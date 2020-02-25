package org.agorahq.agora.core.module.operations

import org.agorahq.agora.core.domain.document.Page
import org.agorahq.agora.core.domain.document.PageURL
import org.agorahq.agora.core.module.Operation
import kotlin.reflect.KClass

interface PageRenderer<D : Page, P : PageURL<D>> : Operation {

    val route: String

    val permalinkType: KClass<P>

    fun renderDocumentBy(permalink: P): String

}