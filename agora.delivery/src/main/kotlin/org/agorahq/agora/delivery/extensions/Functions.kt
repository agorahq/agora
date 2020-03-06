package org.agorahq.agora.delivery.extensions

import org.agorahq.agora.core.api.security.policy.ResourceFilterPolicy
import org.agorahq.agora.core.api.security.policy.UserFilterPolicy
import org.agorahq.agora.core.platform.SystemUtils
import org.agorahq.agora.post.domain.Post

val ownOnly = UserFilterPolicy.create { currentUser, owner -> currentUser == owner }

val all = UserFilterPolicy.create { _, _ -> true }

val postIsPublished = ResourceFilterPolicy.create<Post> { post ->
    post.publishedAt < SystemUtils.currentTimeMillis()
}
