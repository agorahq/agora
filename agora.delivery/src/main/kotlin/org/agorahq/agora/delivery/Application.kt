@file:Suppress("UNCHECKED_CAST")

package org.agorahq.agora.delivery

import io.ktor.application.call
import io.ktor.http.ContentType
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import org.agorahq.agora.comment.domain.Comment
import org.agorahq.agora.comment.facets.CommentListing
import org.agorahq.agora.comment.module.CommentModule
import org.agorahq.agora.core.domain.Permalink
import org.agorahq.agora.core.domain.Site
import org.agorahq.agora.core.extensions.AnyDocumentDetails
import org.agorahq.agora.core.extensions.whenHasFacet
import org.agorahq.agora.core.module.facet.DocumentListing
import org.agorahq.agora.core.service.DefaultModuleRegistry
import org.agorahq.agora.core.service.InMemoryDocumentQueryService
import org.agorahq.agora.core.service.InMemoryFeatureQueryService
import org.agorahq.agora.core.shared.templates.HOMEPAGE
import org.agorahq.agora.delivery.extensions.create
import org.agorahq.agora.post.domain.Post
import org.agorahq.agora.post.facets.PostDocumentDetails
import org.agorahq.agora.post.facets.PostDocumentListing
import org.agorahq.agora.post.module.PostModule
import org.hexworks.cobalt.Identifier

val POST_A_ID = Identifier.randomIdentifier()
val POST_B_ID = Identifier.randomIdentifier()

val POST_A = Post(
        id = POST_A_ID,
        title = "Agora is launching soon",
        date = "2019-12-28",
        shortDescription = "Agora is planned to launch in early Q2.",
        markdownContent = """
            After half a year of active development Agora is planned to launch in closed beta.
            
            A few groups will be chosen to participate in it and the devs will use the feedback to
            make the product even better.
        """.trimIndent())

val POST_B = Post(
        id = POST_B_ID,
        title = "Agora is using Ktor",
        date = "2019-12-29",
        shortDescription = "Ktor have been chosen to be used as the server technology for Agora",
        markdownContent = """
            After careful consideration *Ktor* have been chosen to be used as the server technology for Agora.
            
            We've checked a lot of other tools like *Spring* and *vert.x* but in the end this looked the most promising.
        """.trimIndent())

val COMMENT_A_0 = Comment(
        markdownContent = "**Wow**, that's great!",
        author = "Jack",
        parentId = POST_A_ID)

val COMMENT_B_0 = Comment(
        markdownContent = "I *can't wait*!",
        author = "Jenna",
        parentId = POST_B_ID)

val COMMENT_B_1 = Comment(
        markdownContent = "I've been using *Ktor* for a while now and I'm happy to report that this was a great choice!",
        author = "Frank",
        parentId = POST_B_ID)

val POSTS = sequenceOf(POST_A, POST_B)
val COMMENTS = sequenceOf(COMMENT_A_0, COMMENT_B_0, COMMENT_B_1)

fun main(args: Array<String>) {

    val moduleRegistry = DefaultModuleRegistry()
    val commentQueryService = InMemoryFeatureQueryService(COMMENTS)
    val postQueryService = InMemoryDocumentQueryService(POSTS)

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
            facets = listOf(
                    PostDocumentListing(
                            site = site,
                            postQueryService = postQueryService),
                    PostDocumentDetails(
                            site = site,
                            postQueryService = postQueryService),
                    CommentListing(commentQueryService)))

    val commentModule = CommentModule()

    moduleRegistry.register(postModule)
    moduleRegistry.register(commentModule)

    val server = embeddedServer(Netty, port = site.port) {
        routing {
            get(site.baseUrl) {
                call.respondText(HOMEPAGE.render(site), ContentType.Text.Html)
            }
            moduleRegistry.modules.forEach { module ->
                module.whenHasFacet<DocumentListing> { documentListing ->
                    get(documentListing.path) {
                        call.respondText(
                                text = documentListing.renderListing(),
                                contentType = ContentType.Text.Html)
                    }
                }
                module.whenHasFacet<AnyDocumentDetails> { documentDetails ->
                    get(documentDetails.route) {
                        val permalink = Permalink.create(
                                klass = documentDetails.permalinkType,
                                parameters = call.parameters)
                        call.respondText(
                                text = documentDetails.renderDocumentBy(permalink),
                                contentType = ContentType.Text.Html)
                    }
                }
            }
        }
    }
    server.start(wait = true)
}