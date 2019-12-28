package org.agorahq.agora.core.platform

actual object SystemUtils {
    /**
     * Returns the current UNIX timestamp in millisecond
     */
    actual fun currentTimeMillis(): Long {
        return System.currentTimeMillis()
    }

}