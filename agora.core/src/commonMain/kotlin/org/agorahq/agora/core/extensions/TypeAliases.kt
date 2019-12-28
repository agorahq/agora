package org.agorahq.agora.core.extensions

import org.agorahq.agora.core.domain.DomainObject
import org.agorahq.agora.core.module.Module
import org.agorahq.agora.core.module.ServerContext

typealias AnyModule = Module<out DomainObject, out ServerContext>