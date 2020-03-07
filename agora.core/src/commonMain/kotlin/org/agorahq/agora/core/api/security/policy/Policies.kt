package org.agorahq.agora.core.api.security.policy

import org.agorahq.agora.core.api.security.Group

val ownOnly = UserFilterPolicy.create { currentUser, owner -> currentUser == owner }

val allUsers = UserFilterPolicy.create { _, _ -> true }

val allGroups = GroupFilterPolicy.create { true }

fun group(group: Group) = GroupFilterPolicy.create {
    it == group
}