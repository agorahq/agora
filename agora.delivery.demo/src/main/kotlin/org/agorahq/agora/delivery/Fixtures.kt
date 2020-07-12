package org.agorahq.agora.delivery

import com.soywiz.klock.DateTime
import org.agorahq.agora.comment.domain.Comment
import org.agorahq.agora.comment.operations.CreateComment
import org.agorahq.agora.comment.operations.DeleteComment
import org.agorahq.agora.comment.operations.ListComments
import org.agorahq.agora.comment.operations.ShowCommentForm
import org.agorahq.agora.core.api.security.Role.Companion.ANONYMOUS
import org.agorahq.agora.core.api.security.User
import org.agorahq.agora.core.api.security.builder.authorization
import org.agorahq.agora.core.api.security.policy.forAll
import org.agorahq.agora.core.api.shared.security.BuiltInRoles.ADMIN
import org.agorahq.agora.core.api.shared.security.BuiltInRoles.ATTENDEE
import org.agorahq.agora.core.internal.data.DefaultSiteMetadata
import org.agorahq.agora.core.internal.service.DefaultModuleRegistry
import org.agorahq.agora.delivery.extensions.commentIsNotHidden
import org.agorahq.agora.delivery.extensions.ownCommentOnly
import org.agorahq.agora.delivery.extensions.postIsPublished
import org.agorahq.agora.post.domain.Post
import org.agorahq.agora.post.operations.*
import org.hexworks.cobalt.core.api.UUID
import java.util.concurrent.ConcurrentHashMap

val AUTHORIZATION = authorization {

    roles {
        val anonymousRole = ANONYMOUS {

            Post::class {
                ListPosts withPolicy postIsPublished
                ShowPost withPolicy postIsPublished
            }

            Comment::class {
                ListComments withPolicy commentIsNotHidden
            }

        }
        val attendeeRole = ATTENDEE {

            inherit from anonymousRole

            Comment::class {
                CreateComment allow forAll
                ShowCommentForm allow forAll
                DeleteComment withPolicy ownCommentOnly
            }
        }
        ADMIN {

            inherit from attendeeRole

            Post::class {
                ListPosts allow forAll
                ShowPost allow forAll
                CreatePost allow forAll
                EditPost allow forAll
                DeletePost allow forAll
                TogglePostPublished allow forAll
            }

            Comment::class {
                DeleteComment allow forAll
            }
        }
    }
}

val JACK = User.create(
        email = "jack@jack.com",
        username = "jack",
        roles = setOf(ATTENDEE))

val JENNA = User.create(
        email = "jenna@jenna.com",
        username = "jenna",
        roles = setOf(ATTENDEE))

val FRANK = User.create(
        email = "frank@frank.com",
        username = "frank",
        roles = setOf(ATTENDEE))

val OGABI = User.create(
        email = "nightgoody@gmail.com",
        username = "ogabi",
        roles = setOf(ADMIN))

val ADDAMSSON = User.create(
        email = "arold.adam@gmail.com",
        username = "addamsson",
        roles = setOf(ADMIN))

val POST_A_ID = UUID.randomUUID()
val POST_B_ID = UUID.randomUUID()
val POST_C_ID = UUID.randomUUID()
val POST_D_ID = UUID.randomUUID()

val PUBLISHED_POST_A = Post(
        id = POST_A_ID,
        title = "Agora is launching soon",
        owner = OGABI,
        tags = setOf("agora", "post"),
        createdAt = DateTime.createClamped(2020, 3, 9),
        abstract = "Agora is planned to launch in early Q2.",
        excerpt = "After half a year of active development Agora is planned to launch in closed beta.",
        content = """
            After half a year of active development Agora is planned to launch in closed beta.
            
            A few groups will be chosen to participate in it and the devs will use the feedback to
            make the product even better.
            
            As a 
        """.trimIndent())

val PUBLISHED_POST_B = Post(
        id = POST_B_ID,
        title = "Agora is using Ktor",
        tags = setOf("agora", "ktor"),
        owner = OGABI,
        createdAt = DateTime.createAdjusted(2020, 5, 17),
        abstract = "Ktor have been chosen to be used as the server technology for Agora",
        excerpt = "After careful consideration *Ktor* have been chosen to be used as the server technology for Agora.",
        content = """
            After careful consideration *Ktor* have been chosen to be used as the server technology for Agora.
            
            We've checked a lot of other tools like *Spring* and *vert.x* but in the end this looked the most promising.
            
            So going forward we'll use *Ktor*.
        """.trimIndent())

val NOT_PUBLISHED_POST_C = Post(
        id = POST_C_ID,
        title = "New Permission System is Online",
        tags = setOf("agora", "authorization"),
        owner = ADDAMSSON,
        createdAt = DateTime.createAdjusted(2020, 6, 15),
        publishedAt = DateTime.createAdjusted(2061, 12, 21),
        abstract = "The permission system for Agora received a long-awaited overhaul.",
        excerpt = "The first installment of the permission system for Agora was a bit convoluted. We've fixed this with the new approach.",
        content = """
            The first installment of the permission system for Agora was a bit convoluted. We've fixed this with the new approach.
            
            The groups feature was removed, because it was made redundant by the new ABAC system.
            
            ABAC stands for Attribute Based Access Control. This means that each resource can be filtered for any
            property it has enabling us to create fine-grained access control rules.
            
            This article for example won't be visible for *attendees*, only *admins* because it is not published yet!
        """.trimIndent())

val PUBLISHED_POST_D = Post(
        id = POST_D_ID,
        title = "We're considering React to be used on the frontend",
        tags = setOf("agora", "react"),
        owner = ADDAMSSON,
        createdAt = DateTime.createAdjusted(2020, 7, 12),
        abstract = "We started looking for frontend libraries for a while now and it seems that React will be the one we use.",
        excerpt = "The first installment of the permission system for Agora was a bit convoluted. We've fixed this with the new approach.",
        content = """
            Most web applications need some kind of frontend library or framework for their development.
            
            We've been doing some research for the one which fits Agora and right now it seems that we'll use
            React.
            
            It is a mature frontend library, with a vibrant community, and it is also rather simple compared to the alternatives.
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
    put(POST_A_ID, PUBLISHED_POST_A)
    put(POST_B_ID, PUBLISHED_POST_B)
    put(POST_C_ID, NOT_PUBLISHED_POST_C)
    put(POST_D_ID, PUBLISHED_POST_D)
}
val COMMENTS = ConcurrentHashMap<UUID, Comment>().apply {
    put(COMMENT_A_0.id, COMMENT_A_0)
    put(COMMENT_B_0.id, COMMENT_B_0)
    put(COMMENT_B_1.id, COMMENT_B_1)
}

val USERS = ConcurrentHashMap<UUID, User>().apply {
    put(JACK.id, JACK)
    put(JENNA.id, JENNA)
    put(FRANK.id, FRANK)
    put(OGABI.id, OGABI)
    put(ADDAMSSON.id, ADDAMSSON)
}

val GOOGLE_CLIENT_ID = System.getenv("GOOGLE_OAUTH_CLIENT_ID")
        ?: error("GOOGLE_OAUTH_CLIENT_ID env variable is missing")
val GOOGLE_CLIENT_SECRET = System.getenv("GOOGLE_OAUTH_CLIENT_SECRET")
        ?: error("GOOGLE_OAUTH_CLIENT_SECRET env variable is missing")
val FACEBOOK_CLIENT_ID = System.getenv("FACEBOOK_OAUTH_CLIENT_ID")
        ?: error("FACEBOOK_OAUTH_CLIENT_ID env variable is missing")
val FACEBOOK_CLIENT_SECRET = System.getenv("FACEBOOK_OAUTH_CLIENT_SECRET")
        ?: error("FACEBOOK_OAUTH_CLIENT_SECRET env variable is missing")

val SITE = DefaultSiteMetadata(
        title = "Agora",
        email = "info@agorahq.org",
        description = "Agora Site",
        baseUrl = "/",
        moduleRegistry = DefaultModuleRegistry())
