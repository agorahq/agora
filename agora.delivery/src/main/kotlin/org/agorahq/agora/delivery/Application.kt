@file:Suppress("UNCHECKED_CAST")

package org.agorahq.agora.delivery

import io.ktor.application.call
import io.ktor.http.ContentType
import io.ktor.request.receiveParameters
import io.ktor.response.respondRedirect
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import org.agorahq.agora.comment.domain.Comment
import org.agorahq.agora.comment.module.CommentModule
import org.agorahq.agora.comment.operations.CommentCreator
import org.agorahq.agora.comment.operations.CommentListing
import org.agorahq.agora.core.domain.Site
import org.agorahq.agora.core.domain.document.PageURL
import org.agorahq.agora.core.extensions.AnyDocumentDetails
import org.agorahq.agora.core.extensions.AnyDocumentElementCreating
import org.agorahq.agora.core.extensions.whenHasOperation
import org.agorahq.agora.core.module.operations.DocumentListing
import org.agorahq.agora.core.services.DefaultModuleRegistry
import org.agorahq.agora.core.services.impl.InMemoryDocumentElementQueryService
import org.agorahq.agora.core.services.impl.InMemoryDocumentQueryService
import org.agorahq.agora.core.services.impl.InMemoryStorageService
import org.agorahq.agora.core.shared.templates.HOMEPAGE
import org.agorahq.agora.delivery.extensions.create
import org.agorahq.agora.delivery.extensions.mapTo
import org.agorahq.agora.post.domain.Post
import org.agorahq.agora.post.module.PostModule
import org.agorahq.agora.post.operations.PostDocumentDetailsRenderer
import org.agorahq.agora.post.operations.PostDocumentListing
import org.hexworks.cobalt.core.api.UUID
import java.util.concurrent.ConcurrentHashMap

val POST_A_ID = UUID.randomUUID()
val POST_B_ID = UUID.randomUUID()

val POST_A = Post(
        id = POST_A_ID,
        title = "Agora is launching soon",
        date = "2019-12-28",
        shortDescription = "Agora is planned to launch in early Q2.",
        rawContent = """
            After half a year of active development Agora is planned to launch in closed beta.
            
            A few groups will be chosen to participate in it and the devs will use the feedback to
            make the product even better.
        """.trimIndent())

val POST_B = Post(
        id = POST_B_ID,
        title = "Agora is using Ktor",
        date = "2019-12-29",
        shortDescription = "Ktor have been chosen to be used as the server technology for Agora",
        rawContent = """
            After careful consideration *Ktor* have been chosen to be used as the server technology for Agora.
            
            We've checked a lot of other tools like *Spring* and *vert.x* but in the end this looked the most promising.
        """.trimIndent())

val COMMENT_A_0 = Comment(
        rawContent = "**Wow**, that's great!",
        author = "Jack",
        parentId = POST_A_ID)

val COMMENT_B_0 = Comment(
        rawContent = "I *can't wait*!",
        author = "Jenna",
        parentId = POST_B_ID)

val COMMENT_B_1 = Comment(
        rawContent = "I've been using *Ktor* for a while now and I'm happy to report that this was a great choice!",
        author = "Frank",
        parentId = POST_B_ID)

val POSTS = ConcurrentHashMap<UUID, Post>().apply {
    put(POST_A_ID, POST_A)
    put(POST_B_ID, POST_B)
}
val COMMENTS = ConcurrentHashMap<UUID, Comment>().apply {
    put(COMMENT_A_0.id, COMMENT_A_0)
    put(COMMENT_B_0.id, COMMENT_B_0)
    put(COMMENT_B_1.id, COMMENT_B_1)
}

fun main(args: Array<String>) {

    val moduleRegistry = DefaultModuleRegistry()
    val commentQueryService = InMemoryDocumentElementQueryService(COMMENTS)
    val postQueryService = InMemoryDocumentQueryService(POSTS)
    val commentStorage = InMemoryStorageService(COMMENTS)

    val site = Site(
            title = "Agora",
            email = "info@agorahq.org",
            description = "Agora Site",
            host = "localhost",
            port = 8080,
            baseUrl = "/",
            moduleRegistry = moduleRegistry)

    val postModule = PostModule(
            site = site,
            postQueryService = postQueryService,
            operations = listOf(
                    PostDocumentListing(
                            site = site,
                            postQueryService = postQueryService),
                    PostDocumentDetailsRenderer(
                            site = site,
                            postQueryService = postQueryService),
                    CommentListing(
                            site = site,
                            commentQueryService = commentQueryService),
                    CommentCreator(
                            commentStorage = commentStorage)))

    val commentModule = CommentModule()

    moduleRegistry.register(postModule)
    moduleRegistry.register(commentModule)

    val server = embeddedServer(Netty, port = site.port, host = "localhost") {
        routing {
            get(site.baseUrl) {
                call.respondText(HOMEPAGE.render(site), ContentType.Text.Html)
            }
            moduleRegistry.modules.forEach { module ->
                module.whenHasOperation<DocumentListing> { documentListing ->
                    get(documentListing.route) {
                        call.respondText(
                                text = documentListing.renderListing(),
                                contentType = ContentType.Text.Html)
                    }
                }
                module.whenHasOperation<AnyDocumentDetails> { operation ->
                    get(operation.route) {
                        val permalink = PageURL.create(
                                klass = operation.permalinkType,
                                parameters = call.parameters)
                        call.respondText(
                                text = operation.renderDocumentBy(permalink),
                                contentType = ContentType.Text.Html)
                    }
                }
                module.whenHasOperation<AnyDocumentElementCreating> { elementCreator ->
                    post(elementCreator.route) {
                        elementCreator.store(call.receiveParameters().mapTo(elementCreator.creates))
                        call.request.headers["Referer"]?.let { referer ->
                            call.respondRedirect(referer)
                        }
                    }
                }
            }
        }
    }
    server.start(wait = true)
}