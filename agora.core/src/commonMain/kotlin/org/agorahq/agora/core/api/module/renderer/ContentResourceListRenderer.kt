package org.agorahq.agora.core.api.module.renderer

import org.agorahq.agora.core.api.document.ContentResource
import org.agorahq.agora.core.api.module.context.OperationContext

interface ContentResourceListRenderer<R : ContentResource, C : OperationContext> : Renderer<R, C>