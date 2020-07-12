package org.agorahq.agora.post.extensions

import com.soywiz.klock.DateTime
import org.agorahq.agora.post.domain.Post

val Post.isPublished: Boolean
    get() = publishedAt < DateTime.now()
