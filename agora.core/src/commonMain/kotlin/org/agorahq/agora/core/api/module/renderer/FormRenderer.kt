package org.agorahq.agora.core.api.module.renderer

import org.agorahq.agora.core.api.module.context.ViewContext
import org.agorahq.agora.core.api.resource.Resource
import org.agorahq.agora.core.api.view.ViewModel

interface FormRenderer<R : Resource, M : ViewModel> : Renderer<R, ViewContext<M>>