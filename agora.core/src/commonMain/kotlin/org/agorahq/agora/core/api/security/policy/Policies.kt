package org.agorahq.agora.core.api.security.policy

val ownOnly = UserFilterPolicy.create { currentUser, owner -> currentUser == owner }

val allUsers = UserFilterPolicy.create { _, _ -> true }

val allGroups = GroupFilterPolicy.create { true }