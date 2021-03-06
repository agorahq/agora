package org.agorahq.agora.core.api.fixture

import org.agorahq.agora.core.api.data.ResourceURL
import org.hexworks.cobalt.core.api.UUID

class ItemURL(
        private val itemId: UUID,
        override val redirectTo: String? = null,
        override val pageElementToEdit: String? = null
) : ResourceURL<Item> {

    override fun generate() = "$root/${itemId}"

    override fun matches(resource: Item) = itemId == resource.id

    override fun toString() = ResourceURL.toString(this)

    companion object {
        const val root = "/items"
        val route = "/items/${Item::id.name}"
    }
}
