package org.agorahq.agora.core.api.operation

import org.agorahq.agora.core.api.data.ContentFormat
import org.agorahq.agora.core.api.data.Page
import org.agorahq.agora.core.api.data.ResourceURL
import org.agorahq.agora.core.api.extensions.toCommand
import org.agorahq.agora.core.api.operation.context.OperationContext
import org.agorahq.agora.core.api.security.OperationDescriptor
import org.agorahq.agora.core.api.security.OperationType
import org.agorahq.agora.core.api.security.User
import org.agorahq.agora.core.internal.document.BuiltInContentFormat
import org.agorahq.agora.core.platform.SystemUtils
import org.hexworks.cobalt.core.api.UUID
import kotlin.test.Test
import kotlin.test.assertTrue

@Suppress("TestFunctionName")
class OperationTest {

    @Test
    fun Given_an_operation_descriptor_fitting_an_operation_When_comparing_them_they_should_be_equal() {

        assertTrue {
            ListItems().descriptor == ListItems
        }

    }

    class ListItems : Operation<Item, OperationContext, String>, OperationDescriptor<Item, OperationContext, String> by Companion {

        override val descriptor = ListItems

        override fun OperationContext.createCommand() = {
            "foo"
        }.toCommand()

        companion object : OperationDescriptor<Item, OperationContext, String> {
            override val name = "List Items"
            override val resourceClass = Item::class
            override val type = OperationType.PageListRenderer(Item::class)
            override val urlClass = ItemURL::class
            override val route = ItemURL.root

            override fun toString() = OperationDescriptor.toString(this)
        }

    }

    class Item(
            val inStock: Boolean,
            val price: Long,
            override val owner: User,
            override val url: ResourceURL<out Page>,
            override val format: ContentFormat = BuiltInContentFormat.MARKDOWN,
            override val content: String = "",
            override val createdAt: Long = SystemUtils.currentTimeMillis(),
            override val updatedAt: Long = createdAt,
            override val publishedAt: Long = createdAt,
            override val id: UUID = UUID.randomUUID()
    ) : Page

    class ItemURL(private val item: Item) : ResourceURL<Item> {

        override fun generate() = "$root/${item.id}"

        override fun matches(resource: Item) = item.id == resource.id

        override fun toString() = ResourceURL.toString(this)

        companion object {
            const val root = "/items"
            val route = "/items/${Item::id.name}"
        }
    }
}
