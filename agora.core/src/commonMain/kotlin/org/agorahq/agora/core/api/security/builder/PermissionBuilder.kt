package org.agorahq.agora.core.api.security.builder

import org.agorahq.agora.core.api.resource.Resource
import org.agorahq.agora.core.api.security.OperationDescriptor
import org.agorahq.agora.core.api.security.OperationType.DeleteResource
import org.agorahq.agora.core.api.security.OperationType.ReadOperation
import org.agorahq.agora.core.api.security.OperationType.SaveResource

class PermissionBuilder<R : Resource>() {

    infix fun OperationDescriptor<R, ReadOperation>.allowReadingFor(filter: UserFilter) {

    }

    infix fun OperationDescriptor<R, SaveResource>.allow(saving: saving) {

    }

    infix fun OperationDescriptor<R, DeleteResource>.allowDeletingFor(filter: UserFilter) {

    }

}