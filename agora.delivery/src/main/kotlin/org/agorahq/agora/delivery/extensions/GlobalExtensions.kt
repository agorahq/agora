package org.agorahq.agora.delivery.extensions

fun <T> T.isPresent(): Boolean {
    return this != null
}