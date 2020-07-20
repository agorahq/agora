package org.agorahq.agora.core.api.module

import org.agorahq.agora.core.api.data.Resource
import org.agorahq.agora.core.api.extensions.findMatchingOperations
import org.agorahq.agora.core.api.fixture.Comment
import org.agorahq.agora.core.api.fixture.CommentModule
import org.agorahq.agora.core.api.fixture.ListComments
import org.agorahq.agora.core.api.fixture.SaveComment
import org.agorahq.agora.core.api.operation.facets.ShowsPageElements
import org.hexworks.cobalt.core.api.UUID
import kotlin.test.Test
import kotlin.test.assertTrue

@Suppress("TestFunctionName")
class ModuleTest {

    private val listComments = ListComments()
    private val saveComment = SaveComment()

    private val commentModule = CommentModule(
            initialOperations = listOf(listComments, saveComment)
    )

    @Test
    fun Given_a_module_When_filtering_its_operations_by_type_Then_it_should_return_the_proper_ones() {

        val result = commentModule.findMatchingOperations<Comment, UUID, String>()

        assertTrue("$result should contain ListComments only") {
            result == listOf(listComments)
        }
    }

    @Test
    fun Given_a_module_When_filtering_its_operations_by_type_and_attribute_Then_it_should_return_the_proper_ones() {

        val result = commentModule.findMatchingOperations<Comment, UUID, String>(ShowsPageElements)

        assertTrue("$result should contain ListComments only") {
            result == listOf(listComments)
        }
    }

    @Test
    fun Given_a_module_When_filtering_its_operations_by_attribute_Then_it_should_return_the_proper_ones() {

        val result = commentModule.findMatchingOperations<Resource, Any, Any>(ShowsPageElements)

        assertTrue("$result should contain ListComments only") {
            result == listOf(listComments)
        }
    }
}
