@file:Suppress("UNCHECKED_CAST")

package org.agorahq.agora.delivery.extensions

import io.ktor.http.Parameters
import org.agorahq.agora.core.domain.Document
import org.agorahq.agora.core.domain.Permalink
import kotlin.reflect.KClass
import kotlin.reflect.KFunction
import kotlin.reflect.full.primaryConstructor

fun <D : Document> Permalink.Companion.create(
        klass: KClass<Permalink<out D>>,
        parameters: Parameters): Permalink<D> {

    require(klass.isData) {
        "Creating a permalink from a KClass is only supported for data classes."
    }

    val constructor: KFunction<Any> = klass.primaryConstructor ?: klass.constructors.single()
    val arguments = constructor.parameters.map { parameter ->
        require(parameter.type.classifier == String::class) {
            "Only String parameters are supported for now"
        }
        val parameterName = parameter.name!!
        parameter to parameters[parameterName]
    }.filterNot { (param, name) ->
        param.isOptional && name == null
    }.toMap()

    return constructor.callBy(arguments) as Permalink<D>
}
