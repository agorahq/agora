package org.agorahq.agora.core.module.operations

import org.agorahq.agora.core.domain.document.Page
import org.agorahq.agora.core.domain.document.PageContent
import org.agorahq.agora.core.module.Operation
import kotlin.reflect.KClass

interface PageContentCreator<F : PageContent> : Operation {

    val creates: KClass<F>

    val route: String

    fun renderFormFor(parent: Page): String

    fun save(documentElement: F)

}