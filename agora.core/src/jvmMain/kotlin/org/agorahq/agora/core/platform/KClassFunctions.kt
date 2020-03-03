package org.agorahq.agora.core.platform

import kotlin.reflect.KClass
import kotlin.reflect.full.isSubclassOf
import kotlin.reflect.full.isSuperclassOf

actual fun isSuperclassOf(klass: KClass<out Any>, derived: KClass<out Any>): Boolean {
    return klass.isSuperclassOf(derived)
}

actual fun isSubclassOf(klass: KClass<out Any>, base: KClass<out Any>): Boolean {
    return klass.isSubclassOf(base)
}