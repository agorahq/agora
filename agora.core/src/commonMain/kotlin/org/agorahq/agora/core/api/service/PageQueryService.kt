package org.agorahq.agora.core.api.service

import org.agorahq.agora.core.api.data.Page
import org.agorahq.agora.core.api.data.ResourceURL
import org.agorahq.agora.core.api.data.Result

interface PageQueryService<D : Page> : QueryService<D> {

    fun findByUrl(resourceURL: ResourceURL<D>): Result<out D, out Exception>
}
