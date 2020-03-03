package org.agorahq.agora.core.api.service

import org.agorahq.agora.core.api.content.Page
import org.agorahq.agora.core.api.content.ResourceURL
import org.agorahq.agora.core.api.data.Result
import org.hexworks.cobalt.datatypes.Maybe

interface PageQueryService<D : Page> : QueryService<D> {

    fun findByUrl(resourceURL: ResourceURL<D>): Result<out D, out Exception>
}