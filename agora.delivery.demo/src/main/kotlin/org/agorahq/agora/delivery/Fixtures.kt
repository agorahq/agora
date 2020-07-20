package org.agorahq.agora.delivery

import com.soywiz.klock.DateTime
import org.agorahq.agora.comment.domain.Comment
import org.agorahq.agora.comment.operations.*
import org.agorahq.agora.core.api.security.Role.Companion.ANONYMOUS
import org.agorahq.agora.core.api.security.User
import org.agorahq.agora.core.api.security.builder.authorization
import org.agorahq.agora.core.api.security.policy.forAll
import org.agorahq.agora.core.api.shared.security.BuiltInRoles.ADMIN
import org.agorahq.agora.core.api.shared.security.BuiltInRoles.ATTENDEE
import org.agorahq.agora.core.internal.data.DefaultSiteMetadata
import org.agorahq.agora.core.internal.service.DefaultOperationRegistry
import org.agorahq.agora.delivery.extensions.commentIsNotHidden
import org.agorahq.agora.delivery.extensions.commentIsOwnOrNotHidden
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
                ShowPostListing withPolicy postIsPublished
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
                ListComments withPolicy commentIsOwnOrNotHidden
                ShowEditCommentLink withPolicy ownCommentOnly
                ShowCommentEditor withPolicy ownCommentOnly
                DeleteComment withPolicy ownCommentOnly
            }
        }
        ADMIN {

            inherit from attendeeRole

            Post::class {
                ShowPostListing allow forAll
                ShowPost allow forAll
                CreatePost allow forAll
                ShowPostEditor allow forAll
                CreateAndEditNewPost allow forAll

                ShowCreatePostLink allow forAll
                ShowTogglePostPublished allow forAll
                ShowEditPostLink allow forAll
                ShowDeletePostLink allow forAll

                TogglePostPublished allow forAll
                DeletePost allow forAll
            }

            Comment::class {
                ListComments allow forAll
                DeleteComment allow forAll

                ShowEditCommentLink allow forAll
                ShowCommentEditor allow forAll
            }
        }
    }
}

val JACK = User.create(
        id = UUID.fromString("315748b2-d569-497c-b031-881e14862103"),
        email = "jack@jack.com",
        username = "jack",
        roles = setOf(ATTENDEE))

val JENNA = User.create(
        id = UUID.fromString("b7d96ee5-012f-4965-b0cc-01e11047506c"),
        email = "jenna@jenna.com",
        username = "jenna",
        roles = setOf(ATTENDEE))

val FRANK = User.create(
        id = UUID.fromString("9c402dd4-45cb-474e-a54a-8f109124082a"),
        email = "frank@frank.com",
        username = "frank",
        roles = setOf(ATTENDEE))

val OGABI = User.create(
        id = UUID.fromString("82a14afe-3372-4320-aebb-9b015137e728"),
        email = "nightgoody@gmail.com",
        username = "ogabi",
        roles = setOf(ADMIN))

val ADDAMSSON = User.create(
        id = UUID.fromString("d7473d2e-72c3-4c31-a558-3f3780475ff9"),
        email = "arold.adam@gmail.com",
        username = "addamsson",
        roles = setOf(ADMIN))

val EDEM = User.create(
        id = UUID.fromString("8b3673be-41cc-48a0-aab1-66b8f54c01db"),
        email = "adam.arold@gmail.com",
        username = "edem",
        roles = setOf(ATTENDEE))

val POST_A_ID = UUID.fromString("ba14eb7f-7704-4dea-8dc1-60248174eb9b")
val POST_B_ID = UUID.fromString("128ca860-01f7-4ba4-af7e-8374072ba406")
val POST_C_ID = UUID.fromString("5562732e-d759-441c-ae76-8d664ba28358")
val POST_D_ID = UUID.fromString("58a2132f-86be-48a5-bbcc-a41b6b384718")
val POST_E_ID = UUID.fromString("0940cdb1-cccc-4175-9d3f-1074aded89a1"
)
val PUBLISHED_POST_A = Post(
        id = POST_A_ID,
        title = "Agora is launching soon",
        owner = OGABI,
        tags = setOf("agora", "post", "development"),
        createdAt = DateTime.createClamped(2020, 3, 9),
        publishedAt = DateTime.createClamped(2020, 3, 12),
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
        tags = setOf("agora", "ktor", "development"),
        owner = OGABI,
        createdAt = DateTime.createAdjusted(2020, 5, 17),
        publishedAt = DateTime.createAdjusted(2020, 5, 22),
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
        tags = setOf("agora", "authorization", "development"),
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
        tags = setOf("agora", "react", "development"),
        owner = ADDAMSSON,
        createdAt = DateTime.createAdjusted(2020, 7, 12),
        publishedAt = DateTime.createAdjusted(2020, 7, 20),
        abstract = "We started looking for frontend libraries for a while now and it seems that React will be the one we use.",
        excerpt = "The first installment of the permission system for Agora was a bit convoluted. We've fixed this with the new approach.",
        content = """
            Most web applications need some kind of frontend library or framework for their development.
            
            We've been doing some research for the one which fits Agora and right now it seems that we'll use
            React.
            
            It is a mature frontend library, with a vibrant community, and it is also rather simple compared to the alternatives.
        """.trimIndent())

val PUBLISHED_POST_E = Post(
        id = POST_E_ID,
        title = "Agora operations received an upgrade",
        tags = setOf("agora", "development"),
        owner = ADDAMSSON,
        createdAt = DateTime.createAdjusted(2020, 7, 18),
        publishedAt = DateTime.createAdjusted(2020, 7, 18),
        abstract = "The way we were writing operations for Agora was a bit complex. Now it got simplified.",
        excerpt = "The way we were writing operations for Agora was a bit complex. Now it got simplified.",
        content = """
            Operations prior to this change had their own *type* which led to convoluted code.
            
            With a small change which introduced *Attribute*s for these Operations now you can filter operations
            easily whenever you want to execute a specific kind (for a specific resource).
            
            This will greatly simplify the design in the long run and will make it easier to compose operations.
        """.trimIndent())

val COMMENT_A_0 = Comment(
        id = UUID.fromString("81553f7d-c2e2-4d62-9289-395ed42d221c"),
        content = "**Wow**, that's great!",
        owner = JACK,
        parentId = POST_A_ID
)

val COMMENT_A_1 = Comment(
        id = UUID.fromString("b59ecc1f-1ba1-4fb2-a51e-1c6ac2cca147"),
        content = "Please visit our site at myawesomesite.com. I guarantee that it will be **worth it**!",
        owner = JACK,
        parentId = POST_A_ID,
        createdAt = DateTime.createClamped(2020, 6, 5),
        hiddenSince = DateTime.createClamped(2020, 6, 6)
)

val COMMENT_A_2 = Comment(
        id = UUID.fromString("9b0de07a-8f02-4210-82ee-aef0c18c62e8"),
        content = "I like it.",
        owner = EDEM,
        parentId = POST_A_ID
)

val COMMENT_B_0 = Comment(
        id = UUID.fromString("9780a9c3-2ba8-414a-a5db-2d266307ff30"),
        content = "I *can't wait*!",
        owner = JENNA,
        parentId = POST_B_ID
)

val COMMENT_B_1 = Comment(
        id = UUID.fromString("98d029ff-8157-468a-989f-c73f24b06ca3"),
        content = "I've been using *Ktor* for a while now and I'm happy to report that this was a great choice!",
        owner = FRANK,
        parentId = POST_B_ID
)

val POSTS = ConcurrentHashMap<UUID, Post>().apply {
    put(POST_A_ID, PUBLISHED_POST_A)
    put(POST_B_ID, PUBLISHED_POST_B)
    put(POST_C_ID, NOT_PUBLISHED_POST_C)
    put(POST_D_ID, PUBLISHED_POST_D)
    put(POST_E_ID, PUBLISHED_POST_E)
}
val COMMENTS = ConcurrentHashMap<UUID, Comment>().apply {
    put(COMMENT_A_0.id, COMMENT_A_0)
    put(COMMENT_A_1.id, COMMENT_A_1)
    put(COMMENT_A_2.id, COMMENT_A_2)
    put(COMMENT_B_0.id, COMMENT_B_0)
    put(COMMENT_B_1.id, COMMENT_B_1)
}

val USERS = ConcurrentHashMap<UUID, User>().apply {
    put(JACK.id, JACK)
    put(JENNA.id, JENNA)
    put(FRANK.id, FRANK)
    put(OGABI.id, OGABI)
    put(ADDAMSSON.id, ADDAMSSON)
    put(EDEM.id, EDEM)
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
        operationRegistry = DefaultOperationRegistry())
