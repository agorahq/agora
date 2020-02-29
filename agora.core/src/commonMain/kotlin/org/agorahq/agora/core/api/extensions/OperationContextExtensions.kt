package org.agorahq.agora.core.api.extensions

import org.agorahq.agora.core.api.document.ContentResource
import org.agorahq.agora.core.api.module.Module
import org.agorahq.agora.core.api.module.context.OperationContext
import org.hexworks.cobalt.datatypes.Maybe
import kotlin.reflect.KClass

fun <C : ContentResource> OperationContext.findModule(klass: KClass<Module<C>>): Maybe<Module<C>> {
    return site.moduleRegistry.findModule(klass)
}