@file:Suppress("UNCHECKED_CAST")

package org.agorahq.agora.delivery.extensions

import io.ktor.http.Parameters
import org.agorahq.agora.core.domain.document.Page
import org.agorahq.agora.core.domain.document.PageURL
import kotlin.reflect.KClass

fun <D : Page> PageURL.Companion.create(
        klass: KClass<PageURL<out D>>,
        parameters: Parameters): PageURL<out D> = parameters.mapTo(klass)