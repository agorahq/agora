package org.agorahq.agora.core.api.exception

class MissingConverterException(obj: Any) : RuntimeException("No converter found for ${obj::class.simpleName}.")