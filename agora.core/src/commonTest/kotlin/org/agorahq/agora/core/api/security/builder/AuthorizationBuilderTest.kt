package org.agorahq.agora.core.api.security.builder

import org.agorahq.agora.core.api.Item
import org.agorahq.agora.core.api.ListItems
import org.agorahq.agora.core.api.ShowItem
import org.agorahq.agora.core.api.security.Group
import org.agorahq.agora.core.api.security.RoleDescriptor
import org.agorahq.agora.core.api.security.policy.ResourceFilterPolicy
import org.agorahq.agora.core.api.security.policy.allGroups
import org.agorahq.agora.core.api.security.policy.allUsers
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
            groups {
                +Group.ANONYMOUS
            }
            roles {
                USER {
                    Item::class {
                        ListItems allowFor allGroups
                    }
                }
            }
        }

        val expected = DefaultAuthorization(
                roles = listOf(
                        DefaultRole(
                                descriptor = USER,
                                permissions = listOf(
                                        DefaultPermission(
                                                operationDescriptor = ListItems,
                                                policies = listOf(allGroups))))),
                groups = listOf(Group.ANONYMOUS))

        assertEquals(expected, auth)
    }

    @Test
    fun Given_an_authorization_with_multiple_policies_When_creating_it_Then_the_proper_authorization_is_built() {

        val auth = authorization {
            groups {
                +Group.ANONYMOUS
            }
            roles {
                USER {
                    Item::class {
                        ListItems allowFor allGroups filterFor IN_STOCK_ONLY
                    }
                }
            }
        }

        val expected = DefaultAuthorization(
                roles = listOf(DefaultRole(
                        descriptor = USER,
                        permissions = listOf(DefaultPermission(
                                operationDescriptor = ListItems,
                                policies = listOf(allGroups, IN_STOCK_ONLY))))),
                groups = listOf(Group.ANONYMOUS))

        assertEquals(expected, auth)
    }

    @Test
    fun Given_an_authorization_with_inheritors_When_creating_it_Then_the_proper_authorization_is_built() {

        val auth = authorization {
            groups {
                +Group.ANONYMOUS
            }
            roles {
                val userRole = USER {
                    Item::class {
                        ListItems allowFor allGroups filterFor IN_STOCK_ONLY
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

        val expected = DefaultAuthorization(
                roles = listOf(DefaultRole(
                        descriptor = USER,
                        permissions = listOf(DefaultPermission(
                                operationDescriptor = ListItems,
                                policies = listOf(allGroups, IN_STOCK_ONLY)))),
                        DefaultRole(
                                descriptor = ADMIN,
                                permissions = listOf(DefaultPermission(
                                        operationDescriptor = ListItems,
                                        policies = listOf(allGroups))))),
                groups = listOf(Group.ANONYMOUS))

        assertEquals(expected, auth)
    }

    @Test
    fun Given_an_authorization_with_multiple_inheritors_When_creating_it_Then_the_proper_authorization_is_built() {

        val auth = authorization {
            groups {
                +Group.ANONYMOUS
            }
            roles {
                val poorSchmuckRole = POOR_SCHMUCK {
                    Item::class {
                        ListItems allowFor allGroups filterFor IN_STOCK_ONLY filterFor EXPENSIVE_ITEMS_ONLY
                    }
                }
                val userRole = USER {
                    inherit from poorSchmuckRole
                    Item::class {
                        ListItems allowFor allGroups filterFor IN_STOCK_ONLY
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

        val expected = DefaultAuthorization(
                roles = listOf(DefaultRole(
                        descriptor = POOR_SCHMUCK,
                        permissions = listOf(DefaultPermission(
                                operationDescriptor = ListItems,
                                policies = listOf(allGroups, IN_STOCK_ONLY, EXPENSIVE_ITEMS_ONLY)))),
                        DefaultRole(
                                descriptor = USER,
                                permissions = listOf(DefaultPermission(
                                        operationDescriptor = ListItems,
                                        policies = listOf(allGroups, IN_STOCK_ONLY)))),
                        DefaultRole(
                                descriptor = ADMIN,
                                permissions = listOf(DefaultPermission(
                                        operationDescriptor = ListItems,
                                        policies = listOf(allGroups))))),
                groups = listOf(Group.ANONYMOUS))

        assertEquals(expected, auth)
    }

    @Test
    fun Given_an_authorization_with_inheriting_multiple_operations_but_overriding_only_one_When_creating_it_Then_the_proper_authorization_is_built() {

        val auth = authorization {
            groups {
                +Group.ANONYMOUS
            }
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

        val expected = DefaultAuthorization(
                roles = listOf(
                        DefaultRole(
                                descriptor = USER,
                                permissions = listOf(DefaultPermission(
                                        operationDescriptor = ListItems,
                                        policies = listOf(allGroups, IN_STOCK_ONLY)), DefaultPermission(
                                        operationDescriptor = ShowItem,
                                        policies = listOf(allUsers, IN_STOCK_ONLY)))),
                        DefaultRole(
                                descriptor = ADMIN,
                                permissions = listOf(DefaultPermission(
                                        operationDescriptor = ListItems,
                                        policies = listOf(allGroups)), DefaultPermission(
                                        operationDescriptor = ShowItem,
                                        policies = listOf(allUsers, IN_STOCK_ONLY))))),
                groups = listOf(Group.ANONYMOUS))

        assertEquals(expected, auth)
    }

    companion object {

        val POOR_SCHMUCK = RoleDescriptor.create("poor_schmuck")
        val USER = RoleDescriptor.create("user")
        val ADMIN = RoleDescriptor.create("admin")

        val IN_STOCK_ONLY = ResourceFilterPolicy.create<Item> {
            it.inStock
        }

        val EXPENSIVE_ITEMS_ONLY = ResourceFilterPolicy.create<Item> {
            it.price > 10
        }

    }


}