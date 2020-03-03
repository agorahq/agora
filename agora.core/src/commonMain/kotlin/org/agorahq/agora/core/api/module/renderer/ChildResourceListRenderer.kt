package org.agorahq.agora.core.api.module.renderer

import org.agorahq.agora.core.api.content.PageElement
import org.agorahq.agora.core.api.module.context.OperationContext

interface ChildResourceListRenderer<R : PageElement, C : OperationContext> : Renderer<R, C>