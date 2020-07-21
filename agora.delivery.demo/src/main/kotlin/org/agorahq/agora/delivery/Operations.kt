package org.agorahq.agora.delivery

import org.agorahq.agora.comment.operations.*
import org.agorahq.agora.post.operations.*

object Operations {

    // post operations
    val showPostListing = ShowPostListing(
            postQueryService = Services.postQueryService,
            converterService = Services.converterService,
            markdownRenderer = Services.markdownRenderer
    )
    val showPost = ShowPost(
            postQueryService = Services.postQueryService,
            converterService = Services.converterService,
            markdownRenderer = Services.markdownRenderer
    )
    val showPostEditor = ShowPostEditor(Services.postQueryService, Services.converterService)
    val showPostCreator = ShowPostCreator(Services.converterService)
    val showCreatePostLink = ShowCreatePostLink()
    val showEditPostLink = ShowEditPostLink(Services.postQueryService)
    val showDeletePostLink = ShowDeletePostLink(Services.postQueryService)
    val showPostPublishedToggle = ShowPostPublishedToggle(Services.postQueryService, Services.converterService)

    val createPost = CreatePost(Services.postStorage, Services.converterService)
    val updatePost = UpdatePost(Services.postQueryService, Services.postStorage)
    val deletePost = DeletePost(Services.postQueryService, Services.postStorage)
    val togglePostPublished = TogglePostPublished(Services.postQueryService, Services.postStorage)

    // comment operations
    val showCommentListing = ShowCommentListing(
            commentQueryService = Services.commentQueryService,
            converterService = Services.converterService,
            markdownRenderer = Services.markdownRenderer
    )
    val showCommentCreator = ShowCommentCreator()
    val showCommentEditor = ShowCommentEditor(Services.commentQueryService)
    val showEditCommentLink = ShowEditCommentLink(Services.commentQueryService)
    val showDeleteCommentLink = ShowDeleteCommentLink(Services.commentQueryService)
    val showHideCommentLink = ShowHideCommentLink(Services.commentQueryService)

    val createComment = CreateComment(Services.commentStorage, Services.converterService)
    val updateComment = UpdateComment(Services.commentQueryService, Services.commentStorage)
    val deleteComment = DeleteComment(Services.commentQueryService, Services.commentStorage)
    val toggleCommentPublished = ToggleCommentPublished(Services.commentQueryService, Services.commentStorage)

}
