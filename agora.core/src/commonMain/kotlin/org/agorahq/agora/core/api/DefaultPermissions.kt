package org.agorahq.agora.core.api

import org.agorahq.agora.core.api.security.Permission

enum class DefaultPermissions(
        override val operation: String,
        override val subject: String
) : Permission {

    CREATE_COMMENT("create", "comment"),
    CREATE_POST("create", "post"),
    DELETE_POST("delete", "post"),
    EDIT_POST("edit", "post"),
    PUBLISH_POST("publish", "post"),
    HIDE_POST("hide", "post")

}