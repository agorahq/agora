package org.agorahq.agora.core.api.extensions

import org.agorahq.agora.core.api.operation.AnyOperationDescriptor
import org.agorahq.agora.core.api.operation.OperationDescriptor
import org.agorahq.agora.core.api.security.User
import org.agorahq.agora.core.api.shared.security.BuiltInRoles

val User.isAnonymous: Boolean
    get() = this === User.ANONYMOUS

val User.isAuthenticated: Boolean
    get() = isAnonymous.not()

val User.isAttendee: Boolean
    get() = roles.map { it.name }.contains(BuiltInRoles.ATTENDEE.name)

val User.isAdmin: Boolean
    get() = roles.map { it.name }.contains(BuiltInRoles.ADMIN.name)
