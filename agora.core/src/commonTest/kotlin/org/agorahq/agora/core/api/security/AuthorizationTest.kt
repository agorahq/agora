package org.agorahq.agora.core.api.security

import org.agorahq.agora.core.api.fixture.Item
import org.agorahq.agora.core.api.fixture.ListItems
import org.agorahq.agora.core.api.fixture.ShowItem
import org.agorahq.agora.core.api.data.Result.Failure
import org.agorahq.agora.core.api.data.Result.Success
import org.agorahq.agora.core.api.exception.MissingPermissionException
import org.agorahq.agora.core.api.operation.context.OperationContext
import org.agorahq.agora.core.api.security.builder.authorization
import org.agorahq.agora.core.api.security.policy.Policy
import org.agorahq.agora.core.api.security.policy.forAll
import org.agorahq.agora.core.internal.data.DefaultSiteMetadata
import org.agorahq.agora.core.internal.service.DefaultOperationRegistry
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.fail

@Suppress("TestFunctionName")
class AuthorizationTest {

    @Test
    fun Given_an_operation_When_executing_it_with_an_unauthorized_user_Then_an_exception_is_thrown() {

        val listItems = ListItems(
                items = listOf(Item(
                        inStock = true,
                        owner = USER_JOE)))

        val ctx = OperationContext.create(
                site = SITE,
                user = User.ANONYMOUS,
                authorization = AUTH,
                input = Unit,
                currentPath = "/")

        when (val result = AUTH.authorize(ctx, listItems)) {
            is Success -> fail("This operation was not supposed to be successful.")
            is Failure -> assertEquals(result.exception.message, MissingPermissionException(User.ANONYMOUS, listItems).message)
        }
    }


    companion object {

        val SITE = DefaultSiteMetadata(
                title = "site",
                email = "test@test.com",
                description = "test site",
                baseUrl = "/",
                operationRegistry = DefaultOperationRegistry())

        val USER = RoleDescriptor.create("user")
        val ADMIN = RoleDescriptor.create("admin")

        private val IN_STOCK_ONLY = Policy.create<Item> { _, item ->
            item.inStock
        }

        val USER_JOE = User.create(
                email = "joe@user.com",
                username = "user_joe")

        val AUTH = authorization {
            roles {
                val userRole = USER {
                    Item::class {
                        ListItems withPolicy IN_STOCK_ONLY
                        ShowItem withPolicy IN_STOCK_ONLY
                    }
                }
                ADMIN {
                    inherit from userRole
                    Item::class {
                        ListItems allow forAll
                        ShowItem allow forAll
                    }
                }
            }
        }
    }
}
