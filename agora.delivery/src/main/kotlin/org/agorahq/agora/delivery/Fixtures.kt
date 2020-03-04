package org.agorahq.agora.delivery

import org.agorahq.agora.comment.domain.Comment
import org.agorahq.agora.comment.operations.CreateComment
import org.agorahq.agora.comment.operations.DeleteComment
import org.agorahq.agora.comment.operations.ListComments
import org.agorahq.agora.comment.operations.ShowCommentForm
import org.agorahq.agora.core.api.data.UserMetadata
import org.agorahq.agora.core.api.security.builder.all
import org.agorahq.agora.core.api.security.builder.authorization
import org.agorahq.agora.core.api.security.builder.ownOnly
import org.agorahq.agora.core.api.security.builder.saving
import org.agorahq.agora.core.internal.data.DefaultSiteMetadata
import org.agorahq.agora.core.internal.service.DefaultModuleRegistry
import org.agorahq.agora.delivery.extensions.toTimestamp
import org.agorahq.agora.delivery.security.BuiltInRoles
import org.agorahq.agora.post.domain.Post
import org.agorahq.agora.post.operations.CreatePost
import org.agorahq.agora.post.operations.DeletePost
import org.agorahq.agora.post.operations.ListPosts
import org.agorahq.agora.post.operations.ShowPost
import org.hexworks.cobalt.core.api.UUID
import java.time.LocalDate
import java.util.concurrent.ConcurrentHashMap

val POST_A_ID = UUID.randomUUID()
val POST_B_ID = UUID.randomUUID()

val AUTHORIZATION = authorization {
//    BuiltInRoles.ATTENDEE {
//        Post::class {
//            ListPosts allowReadingFor all
//            ShowPost allowReadingFor all
//        }
//        Comment::class {
//            ListComments allowReadingFor all
//            CreateComment allow saving
//            DeleteComment allowDeletingFor ownOnly
//            ShowCommentForm allowReadingFor all
//        }
//    }
//    BuiltInRoles.ADMIN {
//        inheritFrom(BuiltInRoles.ATTENDEE)
//
//        Post::class {
//            CreatePost allow saving
//            DeletePost allowDeletingFor all
//        }
//        Comment::class {
//            DeleteComment allowDeletingFor all
//        }
//    }
}

val JACK = UserMetadata.create(
        email = "jack@jack.com",
        username = "jack",
        roles = setOf(BuiltInRoles.ATTENDEE)).toUser()

val JENNA = UserMetadata.create(
        email = "jenna@jenna.com",
        username = "jenna",
        roles = setOf(BuiltInRoles.ATTENDEE)).toUser()

val FRANK = UserMetadata.create(
        email = "frank@frank.com",
        username = "frank",
        roles = setOf(BuiltInRoles.ATTENDEE)).toUser()

val EDEM = UserMetadata.create(
        email = "arold.adam@gmail.com",
        username = "edem",
        roles = setOf(BuiltInRoles.ADMIN)).toUser()

val OGABI = UserMetadata.create(
        email = "gabor.orosz@me.com",
        username = "ogabi",
        roles = setOf(BuiltInRoles.ADMIN)).toUser()


val POST_A = Post(
        id = POST_A_ID,
        title = "Agora is launching soon",
        owner = EDEM,
        tags = setOf("agora", "post"),
        createdAt = LocalDate.of(2019, 12, 28).toTimestamp(),
        shortDescription = "Agora is planned to launch in early Q2.",
        content = """
            After half a year of active development Agora is planned to launch in closed beta.
            
            A few groups will be chosen to participate in it and the devs will use the feedback to
            make the product even better.
        """.trimIndent())

val POST_B = Post(
        id = POST_B_ID,
        title = "Agora is using Ktor",
        tags = setOf("agora", "ktor"),
        owner = OGABI,
        createdAt = LocalDate.of(2019, 12, 29).toTimestamp(),
        shortDescription = "Ktor have been chosen to be used as the server technology for Agora",
        content = """
            After careful consideration *Ktor* have been chosen to be used as the server technology for Agora.
            
            We've checked a lot of other tools like *Spring* and *vert.x* but in the end this looked the most promising.
        """.trimIndent())

val COMMENT_A_0 = Comment(
        content = "**Wow**, that's great!",
        owner = JACK,
        parentId = POST_A_ID)

val COMMENT_B_0 = Comment(
        content = "I *can't wait*!",
        owner = JENNA,
        parentId = POST_B_ID)

val COMMENT_B_1 = Comment(
        content = "I've been using *Ktor* for a while now and I'm happy to report that this was a great choice!",
        owner = FRANK,
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

val CLIENT_ID = System.getenv("AGORA_OAUTH_CLIENT_ID") ?: error("AGORA_OAUTH_CLIENT_ID env variable is missing")
val CLIENT_SECRET = System.getenv("AGORA_OAUTH_CLIENT_SECRET")
        ?: error("AGORA_OAUTH_CLIENT_SECRET env variable is missing")

val SITE = DefaultSiteMetadata(
        title = "Agora",
        email = "info@agorahq.org",
        description = "Agora Site",
        baseUrl = "/",
        moduleRegistry = DefaultModuleRegistry())