package org.agorahq.agora.core.api.module.renderer

import org.agorahq.agora.core.api.module.context.OperationContext
import org.agorahq.agora.core.api.resource.Resource

interface ResourceListRenderer<R : Resource, C : OperationContext> : Renderer<R, C>