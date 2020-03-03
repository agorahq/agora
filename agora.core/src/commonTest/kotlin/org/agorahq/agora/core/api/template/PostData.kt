package org.agorahq.agora.core.api.template

import org.agorahq.agora.core.api.data.SiteMetadata

data class PostData(
        val post: TestPost,
        val site: SiteMetadata)