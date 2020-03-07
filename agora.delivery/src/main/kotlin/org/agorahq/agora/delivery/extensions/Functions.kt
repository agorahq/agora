package org.agorahq.agora.delivery.extensions

import org.agorahq.agora.comment.domain.Comment
import org.agorahq.agora.core.api.security.policy.ResourceFilterPolicy
import org.agorahq.agora.core.platform.SystemUtils
import org.agorahq.agora.post.domain.Post

val postIsPublished = ResourceFilterPolicy.create<Post> { post ->
    post.publishedAt < SystemUtils.currentTimeMillis()
}

val commentIsNotHidden = ResourceFilterPolicy.create<Comment> { comment ->
    comment.hiddenSince > SystemUtils.currentTimeMillis()
}
