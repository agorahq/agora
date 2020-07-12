package org.agorahq.agora.delivery.extensions

import com.soywiz.klock.DateTime
import org.agorahq.agora.comment.domain.Comment
import org.agorahq.agora.core.api.security.policy.Policy
import org.agorahq.agora.core.platform.SystemUtils
import org.agorahq.agora.post.domain.Post

val postIsPublished = Policy.create<Post>(
        description = "Post is published"
) { _, post ->
    post.publishedAt < DateTime.now()
}

val commentIsNotHidden = Policy.create<Comment>(
        description = "Comment is not hidden"
) { ctx, comment ->
    comment.owner.email == ctx.user.email ||
            comment.hiddenSince > SystemUtils.currentTimeMillis()
}

val ownCommentOnly = Policy.create<Comment>(
        description = "Own comment only"
) { ctx, comment ->
    comment.owner.email == ctx.user.email
}
