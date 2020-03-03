package org.agorahq.agora.core.api.security

sealed class OperationType {
    object ReadOperation : OperationType()
    object SaveResource : OperationType()
    object DeleteResource : OperationType()
}