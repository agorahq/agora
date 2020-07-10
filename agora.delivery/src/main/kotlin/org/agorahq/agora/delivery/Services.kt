package org.agorahq.agora.delivery

import org.agorahq.agora.comment.converter.CommentConverter
import org.agorahq.agora.comment.module.CommentModule
import org.agorahq.agora.comment.operations.CreateComment
import org.agorahq.agora.comment.operations.DeleteComment
import org.agorahq.agora.comment.operations.ListComments
import org.agorahq.agora.comment.operations.ShowCommentForm
import org.agorahq.agora.core.api.service.impl.InMemoryPageElementQueryService
import org.agorahq.agora.core.api.service.impl.InMemoryPageQueryService
import org.agorahq.agora.core.api.service.impl.InMemoryQueryService
import org.agorahq.agora.core.api.service.impl.InMemoryStorageService
import org.agorahq.agora.core.api.view.ConverterService
import org.agorahq.agora.post.converter.PostConverter
import org.agorahq.agora.post.domain.Post
import org.agorahq.agora.post.module.PostModule
import org.agorahq.agora.post.operations.CreatePost
import org.agorahq.agora.post.operations.DeletePost
import org.agorahq.agora.post.operations.ListPosts
import org.agorahq.agora.post.operations.ShowPost

object Services {

    val userQueryService = InMemoryQueryService(USERS)
    val userStorage = InMemoryStorageService(USERS)

    val converterService = ConverterService.create(listOf(
            CommentConverter(userQueryService), PostConverter(userQueryService)))

    val commentQueryService = InMemoryPageElementQueryService(COMMENTS)
    val postQueryService = InMemoryPageQueryService(Post::class, POSTS)
    val commentStorage = InMemoryStorageService(COMMENTS)
    val postStorage = InMemoryStorageService(POSTS)

    val listPosts = ListPosts(postQueryService, converterService)
    val renderPost = ShowPost(postQueryService, converterService)
    val createPost = CreatePost(postStorage, converterService)
    val deletePost = DeletePost(postQueryService, postStorage)

    val listComments = ListComments(commentQueryService, converterService)
    val renderCommentForm = ShowCommentForm()
    val createComment = CreateComment(commentStorage, converterService)
    val deleteComment = DeleteComment(commentQueryService, commentStorage)

    val postModule = PostModule(listOf(listPosts, renderPost, listComments))
    val commentModule = CommentModule(listOf(renderCommentForm, createComment))

}
