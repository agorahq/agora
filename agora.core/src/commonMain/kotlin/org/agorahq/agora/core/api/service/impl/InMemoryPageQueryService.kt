package org.agorahq.agora.core.api.service.impl

import org.agorahq.agora.core.api.content.Page
import org.agorahq.agora.core.api.content.ResourceURL
import org.agorahq.agora.core.api.data.Result
import org.agorahq.agora.core.api.exception.EntityNotFoundException
import org.agorahq.agora.core.api.service.PageQueryService
import org.hexworks.cobalt.core.api.UUID
import kotlin.reflect.KClass

class InMemoryPageQueryService<D : Page>(
        private val pageClass: KClass<D>,
        private val objects: MutableMap<UUID, D>
) : BaseQueryService<D>(objects), PageQueryService<D> {

    override fun findByUrl(resourceURL: ResourceURL<D>): Result<out D, out Exception> {
        return Result.create(objects.values.firstOrNull { resourceURL.matches(it) }) {
            EntityNotFoundException(pageClass, "url: ${resourceURL.generate()}")
        }
    }
}