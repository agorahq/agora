package org.agorahq.agora.core.platform

import kotlin.reflect.KClass

expect fun isSuperclassOf(klass: KClass<out Any>, derived: KClass<out Any>): Boolean

expect fun isSubclassOf(klass: KClass<out Any>, base: KClass<out Any>): Boolean