package org.agorahq.agora.delivery.security

import org.agorahq.agora.comment.operations.CreateComment
import org.agorahq.agora.comment.operations.DeleteComment
import org.agorahq.agora.comment.operations.ListComments
import org.agorahq.agora.comment.operations.ShowCommentForm
import org.agorahq.agora.core.api.security.OperationDescriptor
import org.agorahq.agora.core.api.security.OperationType.DeleteResource
import org.agorahq.agora.core.api.security.OperationType.ReadOperation
import org.agorahq.agora.core.api.security.OperationType.SaveResource
import org.agorahq.agora.post.operations.CreatePost
import org.agorahq.agora.post.operations.DeletePost
import org.agorahq.agora.post.operations.ListPosts
import org.agorahq.agora.post.operations.ShowPost

object BuiltInOperations {

    val LIST_POSTS = OperationDescriptor.create("List Posts", ListPosts::class, ReadOperation)
    val CREATE_POST = OperationDescriptor.create("Create Post", CreatePost::class, SaveResource)
    val DELETE_POST = OperationDescriptor.create("Delete Post", DeletePost::class, DeleteResource)
    val SHOW_POST = OperationDescriptor.create("Show Post", ShowPost::class, ReadOperation)

    val LIST_COMMENTS = OperationDescriptor.create("List Comments", ListComments::class, ReadOperation)
    val CREATE_COMMENT = OperationDescriptor.create("Create Comment", CreateComment::class, SaveResource)
    val DELETE_COMMENT = OperationDescriptor.create("Delete Post", DeleteComment::class, DeleteResource)
    val SHOW_COMMENT_FORM = OperationDescriptor.create("Show Comment Form", ShowCommentForm::class, ReadOperation)
}