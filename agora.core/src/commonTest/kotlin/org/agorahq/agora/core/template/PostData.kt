package org.agorahq.agora.core.template

import org.agorahq.agora.core.domain.Site

data class PostData(
        val post: Post,
        val site: Site)