package org.agorahq.agora.comment.facets

import org.agorahq.agora.comment.domain.Comment
import org.agorahq.agora.comment.templates.COMMENT_LIST
import org.agorahq.agora.core.domain.Document
import org.agorahq.agora.core.module.facet.DocumentFeatureListing
import org.agorahq.agora.core.service.FeatureQueryService

class CommentListing(
        private val commentQueryService: FeatureQueryService<Comment>
) : DocumentFeatureListing {

    override fun renderListingFor(document: Document): String {
        return COMMENT_LIST.render(commentQueryService.findByParent(document))
    }
}