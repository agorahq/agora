package org.agorahq.agora.core.api.security.builder

import org.agorahq.agora.core.api.operation.context.OperationContext
import org.agorahq.agora.core.api.resource.Resource
import org.agorahq.agora.core.api.security.OperationDescriptor
import org.agorahq.agora.core.api.security.OperationType.ReadOperation
import org.agorahq.agora.core.api.security.OperationType.ResourceDeleter
import org.agorahq.agora.core.api.security.OperationType.ResourceSaver
import org.agorahq.agora.core.api.view.ViewModel

class PermissionBuilder<R : Resource>() {

    infix fun OperationDescriptor<R, OperationContext, ReadOperation<R, OperationContext>>.allowReadingFor(filter: UserFilter) {

    }

    infix fun OperationDescriptor<R, OperationContext, ResourceSaver<R, ViewModel>>.allow(saving: saving) {

    }

    infix fun OperationDescriptor<R, OperationContext, ResourceDeleter<R>>.allowDeletingFor(filter: UserFilter) {

    }

}