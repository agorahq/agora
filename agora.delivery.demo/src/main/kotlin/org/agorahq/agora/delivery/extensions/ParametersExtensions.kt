@file:Suppress("UNCHECKED_CAST")

package org.agorahq.agora.delivery.extensions

import com.soywiz.klock.DateTime
import io.ktor.http.Parameters
import org.agorahq.agora.core.api.data.FormField
import org.agorahq.agora.core.api.data.Resource
import org.agorahq.agora.core.api.data.ResourceURL
import org.agorahq.agora.core.api.data.Result
import org.agorahq.agora.core.api.shared.date.Dates
import org.agorahq.agora.core.api.view.ConverterService
import org.hexworks.cobalt.core.api.UUID
import kotlin.reflect.KClass
import kotlin.reflect.KClassifier
import kotlin.reflect.KFunction
import kotlin.reflect.full.primaryConstructor

fun <R : Resource> Parameters.toResourceURL(
        urlClass: KClass<out ResourceURL<R>>
): ResourceURL<R> = mapTo(urlClass)

fun <T : Any> Parameters.mapTo(type: KClass<T>): T {

    require(type.isData) {
        "Creating an instance from Parameters is only supported for data classes."
    }

    val parameters = this
    val constructor: KFunction<Any> = type.primaryConstructor ?: type.constructors.single()
    val arguments = constructor.parameters.filterNot { param ->
        param.isOptional && param.name == null ||
                param.isOptional && parameters.contains(param.name!!).not()
    }.map { parameter ->
        val parameterName = parameter.name!!
        parameters[parameterName]?.let { value ->
            parameter to mapValue(parameter.type.classifier!!, value)
        } ?: run {
            error("$parameterName is not found in $parameters")
        }
    }.toMap()

    return constructor.callBy(arguments) as T
}

private fun mapValue(classifier: KClassifier, value: String): Any {
    return when (classifier) {
        String::class -> value
        Int::class -> value.toInt()
        Long::class -> value.toLong()
        Boolean::class -> value.toBoolean()
        UUID::class -> UUID.fromString(value)
        FormField::class -> FormField.Dirty(value)
        DateTime::class -> DateTime.fromString(value)
        else -> {
            throw UnsupportedOperationException("Can't map value '$value' to type $classifier")
        }
    }
}
