package org.agorahq.agora.core.api

import org.agorahq.agora.core.api.user.Permission

enum class CorePermissions(
        override val operation: String,
        override val subject: String
) : Permission {

    CREATE_COMMENT("create", "comment")

}