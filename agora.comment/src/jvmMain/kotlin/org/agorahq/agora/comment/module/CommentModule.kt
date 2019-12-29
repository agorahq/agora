package org.agorahq.agora.comment.module

import org.agorahq.agora.comment.domain.Comment
import org.agorahq.agora.core.module.Facet
import org.agorahq.agora.core.module.base.BaseModule

class CommentModule(
        facets: Iterable<Facet> = listOf()
) : BaseModule<Comment>(facets) {

    override val name = "Comments"
}