package org.agorahq.markland.core.user

data class Role(
        val name: String,
        val permissions: Set<Permission>)