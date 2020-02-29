package org.agorahq.agora.core.api.module.operation

import org.agorahq.agora.core.api.document.Page
import org.agorahq.agora.core.api.document.PageContent
import org.agorahq.agora.core.api.module.Operation
import kotlin.reflect.KClass

interface PageContentCreator<F : PageContent> : Operation {

    val creates: KClass<F>

    val route: String

    fun renderFormFor(parent: Page): String

    fun save(documentElement: F)

}