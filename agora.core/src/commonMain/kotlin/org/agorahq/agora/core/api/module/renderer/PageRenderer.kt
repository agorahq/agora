package org.agorahq.agora.core.api.module.renderer

import org.agorahq.agora.core.api.document.Page
import org.agorahq.agora.core.api.document.ResourceURL
import org.agorahq.agora.core.api.module.context.PageContext
import kotlin.reflect.KClass

interface PageRenderer<P : Page, U : ResourceURL<P>> : Renderer<P, PageContext<P>> {

    val urlType: KClass<U>

}