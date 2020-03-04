@file:Suppress("UNCHECKED_CAST")

package org.agorahq.agora.delivery.extensions

import io.ktor.http.Parameters
import org.agorahq.agora.core.api.content.ResourceURL
import org.agorahq.agora.core.api.resource.Resource
import kotlin.reflect.KClass

fun <R : Resource> ResourceURL.Companion.create(
        klass: KClass<out ResourceURL<R>>,
        parameters: Parameters
): ResourceURL<R> = parameters.mapTo(klass)