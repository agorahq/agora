@file:Suppress("UNCHECKED_CAST")

package org.agorahq.agora.delivery.extensions

import io.ktor.http.Parameters
import org.agorahq.agora.core.domain.Document
import org.agorahq.agora.core.domain.Permalink
import kotlin.reflect.KClass

fun <D : Document> Permalink.Companion.create(
        klass: KClass<Permalink<out D>>,
        parameters: Parameters): Permalink<out D> = parameters.mapTo(klass)