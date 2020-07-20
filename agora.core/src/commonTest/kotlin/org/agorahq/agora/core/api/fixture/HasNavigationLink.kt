package org.agorahq.agora.core.api.fixture

import org.agorahq.agora.core.api.operation.Attribute

data class HasNavigationLink(
        val link: String,
        val label: String
) : Attribute
