package org.agorahq.agora.comment.extensions

import com.soywiz.klock.DateTime
import org.agorahq.agora.comment.domain.Comment

val Comment.isPublished: Boolean
    get() = publishedAt < DateTime.now()
