package org.agorahq.agora.core.api.module

import org.agorahq.agora.core.api.data.Resource
import org.agorahq.agora.core.api.extensions.findMatchingOperations
import org.agorahq.agora.core.api.extensions.findOperationsWithAttribute
import org.agorahq.agora.core.api.fixture.*
import org.agorahq.agora.core.api.operation.facets.ShowsPageElements
import org.agorahq.agora.core.internal.service.DefaultOperationRegistry
import org.hexworks.cobalt.core.api.UUID
import kotlin.test.Test
import kotlin.test.assertTrue

@Suppress("TestFunctionName")
class OperationRegistryTest {

    private val listComments = ListComments()
    private val saveComment = SaveComment()
    private val showItemListing = ShowItemListing()

    private val registry = DefaultOperationRegistry()

    init {
        registry.register(listComments)
        registry.register(saveComment)
        registry.register(showItemListing)
    }

    @Test
    fun Given_a_module_When_filtering_its_operations_by_type_Then_it_should_return_the_proper_ones() {

        val result = registry.findMatchingOperations<Comment, UUID, String>()

        assertTrue("$result should contain ListComments only") {
            result == listOf(listComments)
        }
    }

    @Test
    fun Given_a_module_When_filtering_its_operations_by_type_and_attribute_Then_it_should_return_the_proper_ones() {

        val result = registry.findMatchingOperations<Comment, UUID, String>(ShowsPageElements)

        assertTrue("$result should contain ListComments only") {
            result == listOf(listComments)
        }
    }

    @Test
    fun Given_a_module_When_filtering_its_operations_by_attribute_Then_it_should_return_the_proper_ones() {

        val result = registry.findMatchingOperations<Resource, Any, Any>(ShowsPageElements)

        assertTrue("$result should contain ListComments only") {
            result == listOf(listComments)
        }
    }

    @Test
    fun Given_a_module_When_filtering_its_attribute_types_Then_it_should_return_the_proper_ones() {

        val result = registry.findOperationsWithAttribute<HasNavigationLink>()

        assertTrue("$result should contain ListComments only") {
            result == listOf(showItemListing to listOf(HasNavigationLink("/items", "Items")))
        }
    }
}
