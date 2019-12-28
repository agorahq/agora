package org.agorahq.agora.core.domain

class Endpoint<D: Document, R>(
        val permalink: Permalink<D>,
        val operation: (Map<String, Any>) -> R)