package org.agorahq.agora.delivery

import org.agorahq.agora.comment.domain.Comment
import org.agorahq.agora.core.api.security.builder.all
import org.agorahq.agora.core.api.security.builder.authorization
import org.agorahq.agora.core.api.security.builder.ownOnly
import org.agorahq.agora.core.api.security.builder.saving
import org.agorahq.agora.delivery.security.BuiltInOperations.CREATE_COMMENT
import org.agorahq.agora.delivery.security.BuiltInOperations.CREATE_POST
import org.agorahq.agora.delivery.security.BuiltInOperations.DELETE_COMMENT
import org.agorahq.agora.delivery.security.BuiltInOperations.DELETE_POST
import org.agorahq.agora.delivery.security.BuiltInOperations.LIST_COMMENTS
import org.agorahq.agora.delivery.security.BuiltInOperations.LIST_POSTS
import org.agorahq.agora.delivery.security.BuiltInOperations.SHOW_COMMENT_FORM
import org.agorahq.agora.delivery.security.BuiltInOperations.SHOW_POST
import org.agorahq.agora.delivery.security.BuiltInRoles
import org.agorahq.agora.delivery.security.BuiltInRoles.ATTENDEE
import org.agorahq.agora.post.domain.Post
import kotlin.test.Test


class RolesTest {

    @Test
    fun test() {

        val authorization = authorization {
            ATTENDEE {
                Post::class {
                    LIST_POSTS allowReadingFor all
                    SHOW_POST allowReadingFor all
                }
                Comment::class {
                    LIST_COMMENTS allowReadingFor all
                    CREATE_COMMENT allow saving
                    DELETE_COMMENT allowDeletingFor ownOnly
                    SHOW_COMMENT_FORM allowReadingFor all
                }
            }
            BuiltInRoles.ADMIN {
                inheritFrom(ATTENDEE)

                Post::class {
                    CREATE_POST allow saving
                    DELETE_POST allowDeletingFor all
                }
                Comment::class {
                    DELETE_COMMENT allowDeletingFor all
                }
            }
        }

    }
}
