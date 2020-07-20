package org.agorahq.agora.delivery

import org.agorahq.agora.comment.operations.*
import org.agorahq.agora.post.operations.*

object Operations {

    val listPosts = ShowPostListing(Services.postQueryService, Services.converterService)
    val showPost = ShowPost(Services.postQueryService, Services.converterService)
    val createPost = CreatePost(Services.postStorage, Services.converterService)
    val showPostEditor = ShowPostEditor(Services.postQueryService, Services.converterService)
    val createAndEditNewPost = CreateAndEditNewPost(showPostEditor)
    val deletePost = DeletePost(Services.postQueryService, Services.postStorage)

    val showCreatePostLink = ShowCreatePostLink()
    val showTogglePostPublished = ShowTogglePostPublished(Services.postQueryService, Services.converterService)
    val showEditPost = ShowEditPostLink(Services.postQueryService)
    val showDeletePost = ShowDeletePostLink(Services.postQueryService)

    val listComments = ListComments(Services.commentQueryService, Services.converterService)
    val showCommentEditor = ShowCommentEditor(Services.commentQueryService)
    val showEditCommentLink = ShowEditCommentLink(Services.commentQueryService)
    val createComment = CreateComment(Services.commentStorage, Services.converterService)
    val deleteComment = DeleteComment(Services.commentQueryService, Services.commentStorage)
    val togglePostPublished = TogglePostPublished(Services.postQueryService, Services.postStorage)
}
