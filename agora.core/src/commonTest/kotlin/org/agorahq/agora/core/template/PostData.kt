package org.agorahq.agora.core.template

import org.agorahq.agora.core.api.data.SiteMetadata

data class PostData(
        val post: Post,
        val site: SiteMetadata)