package org.agorahq.agora.core.api.user

import org.agorahq.agora.core.internal.user.DefaultPermission

/**
 * A [Permission] represents the ability to perform an operation on a given resource.
 * [Permission]s have a [operation] and a [subject], eg: `read:catalog-item` or `create:invoice`.
 * The [key] represents the combination of the two as seen in the above example.
 */
interface Permission {

    val operation: String
    val subject: String
    val key: String
        get() = "$operation.$subject"

    companion object {

        fun create(namespace: String, name: String): Permission =
                DefaultPermission(namespace, name)

        fun fromKey(key: String): Permission {
            val parts = key.trim().split(":")
            require(parts.size == 2) {
                "The given key $key is not a valid permission key: <namespace>:<name>."
            }
            require(parts.none { it.isBlank() }) {
                "Namespace and name can't be blank in a permission key: $key."
            }
            return DefaultPermission(parts[0], parts[1])
        }
    }
}