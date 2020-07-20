package org.agorahq.agora.delivery.extensions

import io.ktor.request.ApplicationRequest

val ApplicationRequest.refererPath: String?
    get() = headers["Referer"]?.let { referer ->
        val idx = referer.lastIndexOf("?")
        if (idx > 0) {
            referer.substring(0, idx)
        } else referer
    }
