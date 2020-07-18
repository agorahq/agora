package org.agorahq.agora.core.api.extensions

import org.agorahq.agora.core.api.data.ElementSource
import org.agorahq.agora.core.api.operation.Command

fun ElementSource<String>.toCommand() = Command.of {
    asSequence().fold("", String::plus)
}
