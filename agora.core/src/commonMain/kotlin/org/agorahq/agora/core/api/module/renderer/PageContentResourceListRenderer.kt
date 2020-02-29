package org.agorahq.agora.core.api.module.renderer

import org.agorahq.agora.core.api.document.PageContentResource
import org.agorahq.agora.core.api.module.context.OperationContext

interface PageContentResourceListRenderer<R : PageContentResource, C : OperationContext> : Renderer<R, C>