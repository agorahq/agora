package org.agorahq.agora.core.api.security

import org.agorahq.agora.core.api.Item
import org.agorahq.agora.core.api.ListItems
import org.agorahq.agora.core.api.ShowItem
import org.agorahq.agora.core.api.security.builder.authorization
import org.agorahq.agora.core.api.security.policy.ResourceFilterPolicy
import org.agorahq.agora.core.api.security.policy.allGroups
import org.agorahq.agora.core.api.security.policy.allUsers
import kotlin.test.Test

class AuthorizationTest {

    @Test
    fun Given_an_operation_When() {

        val auth = authorization {
            roles {
                val userRole = USER {
                    Item::class {
                        ListItems allowFor allGroups filterFor IN_STOCK_ONLY
                        ShowItem allowFor allUsers filterFor IN_STOCK_ONLY
                    }
                }
                ADMIN {
                    inherit from userRole
                    Item::class {
                        ListItems allowFor allGroups
                    }
                }
            }
        }

        val listItems = ListItems(
                items = listOf(Item(
                        inStock = true,
                        owner = USER_JOE)),
                authorization = auth)


    }


    companion object {

        val USER = RoleDescriptor.create("user")
        val ADMIN = RoleDescriptor.create("admin")

        val IN_STOCK_ONLY = ResourceFilterPolicy.create<Item> {
            it.inStock
        }

        val USER_JOE = User.create(
                email = "joe@user.com",
                username = "user_joe").toUser()

        val ADMIN_SAM = User.create(
                email = "sam@admin.com",
                username = "admin_sam").toUser()
    }
}