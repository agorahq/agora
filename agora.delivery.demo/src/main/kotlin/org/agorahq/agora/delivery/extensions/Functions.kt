package org.agorahq.agora.delivery.extensions

import org.agorahq.agora.comment.domain.Comment
import org.agorahq.agora.core.api.security.policy.Policy
import org.agorahq.agora.core.platform.SystemUtils
import org.agorahq.agora.post.domain.Post

val postIsPublished = Policy.create<Post> { _, post ->
    post.publishedAt < SystemUtils.currentTimeMillis()
}

val commentIsNotHidden = Policy.create<Comment> { _, comment ->
    comment.hiddenSince > SystemUtils.currentTimeMillis()
}
