package org.agorahq.agora.core.api.extensions

import org.agorahq.agora.core.api.data.UserMetadata

val UserMetadata.isAnonymous: Boolean
    get() = this === UserMetadata.ANONYMOUS