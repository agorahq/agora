package org.agorahq.agora.core.extensions

import org.agorahq.agora.core.domain.Site

val Site.modules: Iterable<AnyModule>
    get() = moduleRegistry.modules