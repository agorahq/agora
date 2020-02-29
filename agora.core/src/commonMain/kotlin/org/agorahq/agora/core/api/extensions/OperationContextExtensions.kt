package org.agorahq.agora.core.api.extensions

import org.agorahq.agora.core.api.document.Content
import org.agorahq.agora.core.api.module.Module
import org.agorahq.agora.core.api.module.context.OperationContext
import org.hexworks.cobalt.datatypes.Maybe
import kotlin.reflect.KClass

fun <C : Content> OperationContext.findModule(klass: KClass<Module<C>>): Maybe<Module<C>> {
    return site.moduleRegistry.findModule(klass)
}