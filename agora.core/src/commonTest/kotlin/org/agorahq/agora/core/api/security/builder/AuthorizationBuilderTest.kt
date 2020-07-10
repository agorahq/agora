package org.agorahq.agora.core.api.security.builder

import org.agorahq.agora.core.api.Item
import org.agorahq.agora.core.api.ListItems
import org.agorahq.agora.core.api.ShowItem
import org.agorahq.agora.core.api.security.RoleDescriptor
import org.agorahq.agora.core.api.security.policy.Policy
import org.agorahq.agora.core.api.security.policy.forAll
import org.agorahq.agora.core.internal.security.DefaultAuthorization
import org.agorahq.agora.core.internal.user.DefaultPermission
import org.agorahq.agora.core.internal.user.DefaultRole
import kotlin.test.Test
import kotlin.test.assertEquals

@Suppress("TestFunctionName")
class AuthorizationBuilderTest {

    @Test
    fun Given_an_authorization_with_a_user_policy_When_creating_it_Then_the_proper_authorization_is_built() {

        val auth = authorization {
            roles {
                USER {
                    Item::class {
                        ListItems allow forAll
                    }
                }
            }
        }

        val expected = DefaultAuthorization(
                roles = listOf(
                        DefaultRole(
                                name = USER.name,
                                permissions = listOf(
                                        DefaultPermission(
                                                ListItems.name,
                                                operationDescriptor = ListItems,
                                                policies = listOf())))))

        assertEquals(expected, auth)
    }

    @Test
    fun Given_an_authorization_with_multiple_policies_When_creating_it_Then_the_proper_authorization_is_built() {

        val auth = authorization {
            roles {
                USER {
                    Item::class {
                        ListItems withPolicy IN_STOCK_ONLY
                    }
                }
            }
        }

        val expected = DefaultAuthorization(
                roles = listOf(DefaultRole(
                        name = USER.name,
                        permissions = listOf(DefaultPermission(
                                name = ListItems.name,
                                operationDescriptor = ListItems,
                                policies = listOf(IN_STOCK_ONLY))))))

        assertEquals(expected, auth)
    }

    @Test
    fun Given_an_authorization_with_inheritors_When_creating_it_Then_the_proper_authorization_is_built() {

        val auth = authorization {
            roles {
                val userRole = USER {
                    Item::class {
                        ListItems withPolicy IN_STOCK_ONLY
                    }
                }
                ADMIN {
                    inherit from userRole
                    Item::class {
                        ListItems allow forAll
                    }
                }
            }
        }

        val expected = DefaultAuthorization(
                roles = listOf(DefaultRole(
                        name = USER.name,
                        permissions = listOf(DefaultPermission(
                                name = ListItems.name,
                                operationDescriptor = ListItems,
                                policies = listOf(IN_STOCK_ONLY)))),
                        DefaultRole(
                                name = ADMIN.name,
                                permissions = listOf(DefaultPermission(
                                        name = ListItems.name,
                                        operationDescriptor = ListItems,
                                        policies = listOf())))))

        assertEquals(expected, auth)
    }

    @Test
    fun Given_an_authorization_with_multiple_inheritors_When_creating_it_Then_the_proper_authorization_is_built() {

        val auth = authorization {
            roles {
                val poorSchmuckRole = POOR_SCHMUCK {
                    Item::class {
                        ListItems withPolicy IN_STOCK_ONLY withPolicy EXPENSIVE_ITEMS_ONLY
                    }
                }
                val userRole = USER {
                    inherit from poorSchmuckRole
                    Item::class {
                        ListItems withPolicy IN_STOCK_ONLY
                    }
                }
                ADMIN {
                    inherit from userRole
                    Item::class {
                        ListItems allow forAll
                    }
                }
            }
        }

        val expected = DefaultAuthorization(
                roles = listOf(DefaultRole(
                        name = POOR_SCHMUCK.name,
                        permissions = listOf(DefaultPermission(
                                name = ListItems.name,
                                operationDescriptor = ListItems,
                                policies = listOf(IN_STOCK_ONLY, EXPENSIVE_ITEMS_ONLY)))),
                        DefaultRole(
                                name = USER.name,
                                permissions = listOf(DefaultPermission(
                                        name = ListItems.name,
                                        operationDescriptor = ListItems,
                                        policies = listOf(IN_STOCK_ONLY)))),
                        DefaultRole(
                                name = ADMIN.name,
                                permissions = listOf(DefaultPermission(
                                        name = ListItems.name,
                                        operationDescriptor = ListItems,
                                        policies = listOf())))))

        assertEquals(expected, auth)
    }

    @Test
    fun Given_an_authorization_with_inheriting_multiple_operations_but_overriding_only_one_When_creating_it_Then_the_proper_authorization_is_built() {

        val auth = authorization {
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
                    }
                }
            }
        }

        val expected = DefaultAuthorization(
                roles = listOf(
                        DefaultRole(
                                name = USER.name,
                                permissions = listOf(DefaultPermission(
                                        name = ListItems.name,
                                        operationDescriptor = ListItems,
                                        policies = listOf(IN_STOCK_ONLY)), DefaultPermission(
                                        name = ShowItem.name,
                                        operationDescriptor = ShowItem,
                                        policies = listOf(IN_STOCK_ONLY)))),
                        DefaultRole(
                                name = ADMIN.name,
                                permissions = listOf(DefaultPermission(
                                        name = ListItems.name,
                                        operationDescriptor = ListItems,
                                        policies = listOf()), DefaultPermission(
                                        name = ShowItem.name,
                                        operationDescriptor = ShowItem,
                                        policies = listOf(IN_STOCK_ONLY))))))

        assertEquals(expected, auth)
    }

    @Test
    fun testForOwnership() {
        // TODO:
    }

    companion object {

        val POOR_SCHMUCK = RoleDescriptor.create("poor_schmuck")
        val USER = RoleDescriptor.create("user")
        val ADMIN = RoleDescriptor.create("admin")

        val IN_STOCK_ONLY = Policy.create<Item> { _, item ->
            item.inStock
        }

        val EXPENSIVE_ITEMS_ONLY = Policy.create<Item> { _, item ->
            item.price > 10
        }

    }


}
