package org.agorahq.agora.core.api.operation

import org.agorahq.agora.core.api.data.Page
import org.agorahq.agora.core.api.data.PageElement
import org.agorahq.agora.core.api.data.Resource
import org.agorahq.agora.core.api.operation.context.*
import org.agorahq.agora.core.api.view.ViewModel
import org.agorahq.agora.core.platform.isSuperclassOf
import kotlin.reflect.KClass

/**
 * An [OperationType] contains metadata about how an [Operation] is performed
 * (its parameter types and return type). It can also be used as a matcher
 * using [OperationType.matches].
 */
@Suppress("UNCHECKED_CAST", "DuplicatedCode")
sealed class OperationType<R : Resource, C : OperationContext, T : Any>(
        val contextClass: KClass<out C>
) {

    abstract fun matches(
            other: OperationType<out Resource, out OperationContext, out Any>
    ): Boolean

    protected inline fun <reified K : OperationType<R, C, T>> whenSuperMatches(
            other: OperationType<out Resource, out OperationContext, out Any>,
            fn: (other: K) -> Boolean): Boolean {
        return if (other is K && isSuperclassOf(contextClass, other.contextClass)) {
            fn(other)
        } else false
    }

    data class PageRenderer<P : Page>(
            val pageClass: KClass<P>
    ) : OperationType<P, PageURLContext<P>, String>(
            PageURLContext::class as KClass<out PageURLContext<P>>
    ) {

        override fun matches(
                other: OperationType<out Resource, out OperationContext, out Any>
        ) = whenSuperMatches<PageRenderer<P>>(other) {
            isSuperclassOf(pageClass, it.pageClass)
        }
    }

    data class PageListRenderer<P : Page>(
            val pageClass: KClass<P>
    ) : OperationType<P, OperationContext, String>(OperationContext::class) {

        override fun matches(
                other: OperationType<out Resource, out OperationContext, out Any>
        ) = whenSuperMatches<PageListRenderer<P>>(other) {
            isSuperclassOf(pageClass, it.pageClass)
        }
    }

    data class PageFormRenderer<P : Page>(
            val pageClass: KClass<P>
    ) : OperationType<Page, PageContext<out Page>, String>(PageContext::class) {

        override fun matches(
                other: OperationType<out Resource, out OperationContext, out Any>
        ) = whenSuperMatches<PageFormRenderer<P>>(other) {
            isSuperclassOf(pageClass, it.pageClass)
        }
    }

    data class PageElementFormRenderer<PE : PageElement, P : Page>(
            val pageElementClass: KClass<PE>,
            val parentClass: KClass<P>
    ) : OperationType<PE, PageContext<P>, String>(PageContext::class as KClass<out PageContext<P>>) {

        override fun matches(
                other: OperationType<out Resource, out OperationContext, out Any>
        ) = whenSuperMatches<PageElementFormRenderer<PE, P>>(other) {
            isSuperclassOf(pageElementClass, it.pageElementClass) &&
                    isSuperclassOf(parentClass, it.parentClass)
        }
    }

    data class PageElementListRenderer<PE : PageElement, P : Page>(
            val pageElementClass: KClass<PE>,
            val parentClass: KClass<P>
    ) : OperationType<PE, PageContext<P>, String>(PageContext::class as KClass<out PageContext<P>>) {

        override fun matches(
                other: OperationType<out Resource, out OperationContext, out Any>
        ) = whenSuperMatches<PageElementListRenderer<PE, P>>(other) {
            isSuperclassOf(pageElementClass, it.pageElementClass) &&
                    isSuperclassOf(parentClass, it.parentClass)
        }
    }

    data class ResourceSaver<R : Resource, V : ViewModel>(
            val resourceClass: KClass<R>,
            val viewModelClass: KClass<V>
    ) : OperationType<R, ViewModelContext<V>, Unit>(ViewModelContext::class as KClass<out ViewModelContext<V>>) {

        override fun matches(
                other: OperationType<out Resource, out OperationContext, out Any>
        ) = whenSuperMatches<ResourceSaver<R, V>>(other) {
            isSuperclassOf(resourceClass, it.resourceClass) &&
                    isSuperclassOf(viewModelClass, it.viewModelClass)
        }
    }

    data class ResourceAlterer<R : Resource>(
            val resourceClass: KClass<R>
    ) : OperationType<R, ResourceIdContext, Unit>(ResourceIdContext::class) {

        override fun matches(
                other: OperationType<out Resource, out OperationContext, out Any>
        ) = whenSuperMatches<ResourceAlterer<R>>(other) {
            isSuperclassOf(resourceClass, it.resourceClass)
        }
    }

}
