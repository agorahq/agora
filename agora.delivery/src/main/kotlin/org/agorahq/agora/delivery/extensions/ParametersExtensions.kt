@file:Suppress("UNCHECKED_CAST")

package org.agorahq.agora.delivery.extensions

import io.ktor.http.Parameters
import org.hexworks.cobalt.core.api.UUID
import kotlin.reflect.KClass
import kotlin.reflect.KClassifier
import kotlin.reflect.KFunction
import kotlin.reflect.full.primaryConstructor

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
            error("")
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
        else -> {
            throw UnsupportedOperationException("Can't map value '$value' to type $classifier")
        }
    }
}