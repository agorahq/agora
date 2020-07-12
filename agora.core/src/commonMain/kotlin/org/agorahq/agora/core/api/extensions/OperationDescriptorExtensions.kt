package org.agorahq.agora.core.api.extensions

import org.agorahq.agora.core.api.operation.AnyOperationDescriptor

fun operations(vararg operations: AnyOperationDescriptor) = operations.toList()
