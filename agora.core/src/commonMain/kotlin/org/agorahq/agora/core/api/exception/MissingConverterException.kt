package org.agorahq.agora.core.api.exception

class MissingConverterException(
        obj: Any,
        private val msg: String = "No converter found for ${obj::class.simpleName}."
) : RuntimeException(msg) {
    override fun toString() = msg
}
