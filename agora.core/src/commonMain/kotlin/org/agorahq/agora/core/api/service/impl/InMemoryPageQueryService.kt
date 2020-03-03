package org.agorahq.agora.core.api.service.impl

import org.agorahq.agora.core.api.content.Page
import org.agorahq.agora.core.api.content.ResourceURL
import org.agorahq.agora.core.api.data.Result
import org.agorahq.agora.core.api.exception.EntityNotFoundException
import org.agorahq.agora.core.api.service.PageQueryService
import org.hexworks.cobalt.core.api.UUID
import org.hexworks.cobalt.datatypes.Maybe
import kotlin.reflect.KClass

class InMemoryPageQueryService<D : Page>(
        private val pageClass: KClass<D>,
        private val objects: MutableMap<UUID, D>
) : PageQueryService<D> {

    override fun findAll(): Sequence<D> = objects.values.asSequence()

    override fun findById(id: UUID) = Maybe.ofNullable(objects[id])

    override fun findByUrl(resourceURL: ResourceURL<D>): Result<out D, out Exception> {
        return Result.create(objects.values.firstOrNull { resourceURL.matches(it) }) {
            EntityNotFoundException(pageClass, "url: ${resourceURL.generate()}")
        }
    }
}