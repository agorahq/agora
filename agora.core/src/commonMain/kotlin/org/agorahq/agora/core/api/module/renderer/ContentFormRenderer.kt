package org.agorahq.agora.core.api.module.renderer

import org.agorahq.agora.core.api.document.ContentResource
import org.agorahq.agora.core.api.document.Page
import org.agorahq.agora.core.api.module.context.ResourceContext

interface ContentFormRenderer<R : ContentResource> : Renderer<R, ResourceContext<out Page>>