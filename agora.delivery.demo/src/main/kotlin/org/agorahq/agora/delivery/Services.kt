@file:Suppress("MemberVisibilityCanBePrivate")

package org.agorahq.agora.delivery

import org.agorahq.agora.comment.converter.CommentConverter
import org.agorahq.agora.comment.module.CommentModule
import org.agorahq.agora.comment.operations.*
import org.agorahq.agora.core.api.service.impl.InMemoryPageElementQueryService
import org.agorahq.agora.core.api.service.impl.InMemoryPageQueryService
import org.agorahq.agora.core.api.service.impl.InMemoryQueryService
import org.agorahq.agora.core.api.service.impl.InMemoryStorageService
import org.agorahq.agora.core.api.view.ConverterService
import org.agorahq.agora.post.converter.PostConverter
import org.agorahq.agora.post.domain.Post
import org.agorahq.agora.post.module.PostModule
import org.agorahq.agora.post.operations.*

object Services {

    val userQueryService = InMemoryQueryService(USERS)
    val userStorage = InMemoryStorageService(USERS)

    val converterService = ConverterService.create(listOf(
            CommentConverter(userQueryService), PostConverter(userQueryService)))

    val commentQueryService = InMemoryPageElementQueryService(COMMENTS)
    val postQueryService = InMemoryPageQueryService(Post::class, POSTS)
    val commentStorage = InMemoryStorageService(COMMENTS)
    val postStorage = InMemoryStorageService(POSTS)

    val listPosts = ShowPostListing(postQueryService, converterService)
    val showPost = ShowPost(postQueryService, converterService)
    val createPost = CreatePost(postStorage, converterService)
    val showPostEditor = ShowPostEditor(postQueryService, converterService)
    val createAndEditNewPost = CreateAndEditNewPost(showPostEditor)
    val deletePost = DeletePost(postQueryService, postStorage)

    val showCreatePostLink = ShowCreatePostLink()
    val showTogglePostPublished = ShowTogglePostPublished(postQueryService, converterService)
    val showEditPost = ShowEditPostLink(postQueryService)
    val showDeletePost = ShowDeletePostLink(postQueryService)

    val listComments = ListComments(commentQueryService, converterService)
    val showCommentEditor = ShowCommentEditor(commentQueryService)
    val showEditCommentLink = ShowEditCommentLink(commentQueryService)
    val createComment = CreateComment(commentStorage, converterService)
    val deleteComment = DeleteComment(commentQueryService, commentStorage)
    val togglePostPublished = TogglePostPublished(postQueryService, postStorage)

    val postModule = PostModule(listOf(
            listPosts, showPost, createPost, deletePost, showCreatePostLink, showTogglePostPublished, showEditPost, showDeletePost))
    val commentModule = CommentModule(listOf(listComments, showCommentEditor, createComment, showEditCommentLink))

}
