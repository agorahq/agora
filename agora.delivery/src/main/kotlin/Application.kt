@file:Suppress("UNCHECKED_CAST")

package org.agorahq.agora

import io.ktor.application.call
import io.ktor.application.install
import io.ktor.http.ContentType
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.locations.Locations
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import org.agorahq.agora.core.domain.Permalink
import org.agorahq.agora.core.domain.Site
import org.agorahq.agora.core.extensions.modules
import org.agorahq.agora.core.module.KtorServerContext
import org.agorahq.agora.core.module.ServerContext
import org.agorahq.agora.core.service.ContentQueryService
import org.agorahq.agora.core.service.impl.DefaultModuleRegistry
import org.agorahq.agora.core.shared.include.HOMEPAGE
import org.agorahq.agora.post.domain.Post
import org.agorahq.agora.post.module.PostModule
import org.hexworks.cobalt.Identifier
import org.hexworks.cobalt.datatypes.Maybe

val POST_A = Post(
        title = "Post A",
        date = "2019-12-28",
        excerpt = "This is post A",
        shortDescription = "This is post A which I made",
        markdownContent = "Content of Post A",
        comments = sequenceOf(),
        id = Identifier.randomIdentifier(),
        createdAtMs = System.currentTimeMillis(),
        updatedAtMs = System.currentTimeMillis())

val POST_B = Post(
        title = "Post B",
        date = "2019-12-29",
        excerpt = "This is post B",
        shortDescription = "This is post B which I made",
        markdownContent = "Content of Post B",
        comments = sequenceOf(),
        id = Identifier.randomIdentifier(),
        createdAtMs = System.currentTimeMillis(),
        updatedAtMs = System.currentTimeMillis())

val POSTS = sequenceOf(POST_A, POST_B)

@KtorExperimentalLocationsAPI
fun main(args: Array<String>) {

    val moduleRegistry = DefaultModuleRegistry<KtorServerContext>()
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
            queryService = object : ContentQueryService<Post> {

                override fun findByLocation(permalink: Permalink<Post>) = Maybe.ofNullable(POSTS.firstOrNull {
                    it.permalink == permalink.generatePermalink()
                })

                override fun findAll() = POSTS

                override fun findById(id: Identifier) = Maybe.ofNullable(POSTS.firstOrNull {
                    it.id == id
                })

            })

    moduleRegistry.register(postModule)

    val server = embeddedServer(Netty, port = site.port) {
        install(Locations)
        routing {
            val routing = this
            get(site.baseUrl) {
                call.respondText(HOMEPAGE.render(site), ContentType.Text.Html)
            }
            moduleRegistry.modules.forEach { module ->
                module.registerEndpoints(KtorServerContext(routing))
            }
        }
    }
    server.start(wait = true)
}