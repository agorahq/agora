@file:Suppress("UNCHECKED_CAST")

package org.agorahq.agora.delivery.extensions

import io.ktor.http.Parameters
import org.agorahq.agora.core.api.document.Page
import org.agorahq.agora.core.api.document.PageURL
import kotlin.reflect.KClass

fun <D : Page> PageURL.Companion.create(
        klass: KClass<PageURL<D>>,
        parameters: Parameters
): PageURL<D> = parameters.mapTo(klass)