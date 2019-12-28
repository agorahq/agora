package org.agorahq.agora.comment.module

import org.agorahq.agora.core.domain.Site
import org.agorahq.agora.core.module.KtorServerContext
import org.agorahq.agora.core.module.base.BaseModule
import org.agorahq.agora.core.service.FeatureQueryService
import org.agorahq.agora.post.domain.Comment

class CommentModule(
        private val site: Site,
        private val queryService: FeatureQueryService<Comment>
) : BaseModule<Comment, KtorServerContext>() {

    override val name = "Comments"
}