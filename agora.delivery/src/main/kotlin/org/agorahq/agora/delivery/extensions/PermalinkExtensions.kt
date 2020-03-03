@file:Suppress("UNCHECKED_CAST")

package org.agorahq.agora.delivery.extensions

import io.ktor.http.Parameters
import org.agorahq.agora.core.api.content.Page
import org.agorahq.agora.core.api.content.ResourceURL
import kotlin.reflect.KClass

fun <D : Page> ResourceURL.Companion.create(
        klass: KClass<ResourceURL<D>>,
        parameters: Parameters
): ResourceURL<D> = parameters.mapTo(klass)