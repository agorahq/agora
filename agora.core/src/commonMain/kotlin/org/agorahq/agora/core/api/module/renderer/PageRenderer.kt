package org.agorahq.agora.core.api.module.renderer

import org.agorahq.agora.core.api.content.Page
import org.agorahq.agora.core.api.content.ResourceURL
import org.agorahq.agora.core.api.module.context.PageRequestContext
import kotlin.reflect.KClass

interface PageRenderer<P : Page, U : ResourceURL<P>> : Renderer<P, PageRequestContext<P>> {

    val urlType: KClass<U>

}