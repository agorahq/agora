package org.agorahq.agora.core.api.operation

import org.agorahq.agora.core.api.ListItems
import org.agorahq.agora.core.api.data.Page
import org.agorahq.agora.core.api.operation.OperationType.PageListRenderer
import kotlin.test.Test
import kotlin.test.assertTrue

@Suppress("TestFunctionName")
class OperationTypeTest {

    @Test
    fun Given_an_operation_type_with_concrete_classes_When_checked_for_matching_Then_it_should_match_superclasses() {
        assertTrue {
            PageListRenderer(Page::class).matches(ListItems(listOf()).type)
        }
    }
}
