package org.agorahq.agora.core.platform

expect object SystemUtils {

    /**
     * Returns the current UNIX timestamp in millisecond
     */
    fun currentTimeMillis(): Long
}