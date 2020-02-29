package org.agorahq.agora.core.api.module.operation

import org.agorahq.agora.core.api.document.Page
import org.agorahq.agora.core.api.document.PageURL
import org.agorahq.agora.core.api.module.Operation
import org.agorahq.agora.core.api.module.context.OperationContext
import kotlin.reflect.KClass

interface PageRenderer<P : Page, U : PageURL<P>> : Operation {

    val route: String

    val permalinkType: KClass<U>

    fun render(context: OperationContext, url: PageURL<P>): String

}