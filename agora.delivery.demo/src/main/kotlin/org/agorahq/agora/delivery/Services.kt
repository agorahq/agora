@file:Suppress("MemberVisibilityCanBePrivate")

package org.agorahq.agora.delivery

import org.agorahq.agora.comment.converter.CommentConverter
import org.agorahq.agora.core.api.service.impl.InMemoryPageElementQueryService
import org.agorahq.agora.core.api.service.impl.InMemoryPageQueryService
import org.agorahq.agora.core.api.service.impl.InMemoryQueryService
import org.agorahq.agora.core.api.service.impl.InMemoryStorageService
import org.agorahq.agora.core.api.view.ConverterService
import org.agorahq.agora.core.platform.MarkdownRendererFactory
import org.agorahq.agora.post.converter.PostConverter
import org.agorahq.agora.post.domain.Post

object Services {

    val userQueryService = InMemoryQueryService(USERS)
    val commentQueryService = InMemoryPageElementQueryService(COMMENTS)
    val postQueryService = InMemoryPageQueryService(Post::class, POSTS)

    val userStorage = InMemoryStorageService(USERS)
    val commentStorage = InMemoryStorageService(COMMENTS)
    val postStorage = InMemoryStorageService(POSTS)

    val converterService = ConverterService.create(listOf(
            CommentConverter(userQueryService),
            PostConverter(userQueryService)
    ))

    val markdownRenderer = MarkdownRendererFactory.createRenderer()
}
