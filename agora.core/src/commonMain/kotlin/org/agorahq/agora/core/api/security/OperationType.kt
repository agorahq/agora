package org.agorahq.agora.core.api.security

import org.agorahq.agora.core.api.data.Page
import org.agorahq.agora.core.api.operation.context.OperationContext
import org.agorahq.agora.core.api.operation.context.PageContext
import org.agorahq.agora.core.api.operation.context.PageURLContext
import org.agorahq.agora.core.api.operation.context.ResourceIdContext
import org.agorahq.agora.core.api.operation.context.ViewModelContext
import org.agorahq.agora.core.api.data.Resource
import org.agorahq.agora.core.api.view.ViewModel
import org.agorahq.agora.core.platform.isSubclassOf
import kotlin.reflect.KClass

@Suppress("UNCHECKED_CAST", "DuplicatedCode")
sealed class OperationType<R : Resource, C : OperationContext, U : Any> {

    abstract val contextClass: KClass<C>

    abstract class ReadOperation<R : Resource, C : OperationContext> : OperationType<R, C, String>()

    class PageRenderer<P : Page>(private val pageClass: KClass<P>) : ReadOperation<P, PageURLContext<P>>() {

        override val contextClass = PageURLContext::class as KClass<PageURLContext<P>>

        override fun toString() = "PageRenderer(pageClass=${pageClass.simpleName}, contextClass=${contextClass.simpleName})"

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other == null || this::class != other::class) return false
            other as PageRenderer<*>
            if (!isSubclassOf(pageClass, other.pageClass)) return false
            if (!isSubclassOf(contextClass, other.contextClass)) return false
            return true
        }

        override fun hashCode(): Int {
            var result = pageClass.hashCode()
            result = 31 * result + contextClass.hashCode()
            return result
        }

    }

    class PageListRenderer<P : Page>(private val pageClass: KClass<P>) : ReadOperation<P, OperationContext>() {

        override val contextClass = OperationContext::class

        override fun toString() = "PageListRenderer(pageClass=${pageClass.simpleName}, contextClass=${contextClass.simpleName})"

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other == null || this::class != other::class) return false
            other as PageListRenderer<*>
            if (!isSubclassOf(pageClass, other.pageClass)) return false
            if (!isSubclassOf(contextClass, other.contextClass)) return false
            return true
        }

        override fun hashCode(): Int {
            var result = pageClass.hashCode()
            result = 31 * result + contextClass.hashCode()
            return result
        }


    }

    class PageFormRenderer<P : Page>(private val pageClass: KClass<P>) : ReadOperation<P, PageContext<P>>() {

        override val contextClass = PageContext::class as KClass<PageContext<P>>

        override fun toString() = "PageFormRenderer(pageClass=${pageClass.simpleName}, contextClass=${contextClass.simpleName})"

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other == null || this::class != other::class) return false
            other as PageFormRenderer<*>
            if (!isSubclassOf(pageClass, other.pageClass)) return false
            if (!isSubclassOf(contextClass, other.contextClass)) return false
            return true
        }

        override fun hashCode(): Int {
            var result = pageClass.hashCode()
            result = 31 * result + contextClass.hashCode()
            return result
        }


    }

    class PageElementFormRenderer<R : Resource, P : Page>(
            private val resourceClass: KClass<R>,
            private val pageClass: KClass<P>
    ) : ReadOperation<R, PageContext<P>>() {

        override val contextClass = PageContext::class as KClass<PageContext<P>>

        override fun toString() = "PageElementFormRenderer(resourceClass=${resourceClass.simpleName}, " +
                "pageClass=${pageClass.simpleName}, " +
                "contextClass=${contextClass.simpleName})"

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other == null || this::class != other::class) return false
            other as PageElementFormRenderer<*, *>
            if (!isSubclassOf(resourceClass, other.resourceClass)) return false
            if (!isSubclassOf(pageClass, other.pageClass)) return false
            if (!isSubclassOf(contextClass, other.contextClass)) return false

            return true
        }

        override fun hashCode(): Int {
            var result = resourceClass.hashCode()
            result = 31 * result + pageClass.hashCode()
            result = 31 * result + contextClass.hashCode()
            return result
        }

    }

    class PageElementListRenderer<R : Resource, P : Page>(
            private val resourceClass: KClass<R>,
            private val pageClass: KClass<P>
    ) : ReadOperation<R, PageContext<P>>() {

        override val contextClass = PageContext::class as KClass<PageContext<P>>

        override fun toString() = "PageElementListRenderer(resourceClass=${resourceClass.simpleName}, " +
                "pageClass=${pageClass.simpleName}, " +
                "contextClass=${contextClass.simpleName})"

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other == null || this::class != other::class) return false

            other as PageElementListRenderer<*, *>

            if (!isSubclassOf(resourceClass, other.resourceClass)) return false
            if (!isSubclassOf(pageClass, other.pageClass)) return false
            if (!isSubclassOf(contextClass, other.contextClass)) return false

            return true
        }

        override fun hashCode(): Int {
            var result = resourceClass.hashCode()
            result = 31 * result + pageClass.hashCode()
            result = 31 * result + contextClass.hashCode()
            return result
        }
    }

    class ResourceSaver<R : Resource, V : ViewModel>(
            private val resourceClass: KClass<R>,
            private val viewModelClass: KClass<V>
    ) : OperationType<R, ViewModelContext<V>, Unit>() {

        override val contextClass = ViewModelContext::class as KClass<ViewModelContext<V>>

        override fun toString() = "ResourceSaver(resourceClass=${resourceClass.simpleName}, " +
                "viewModelClass=${viewModelClass.simpleName}, " +
                "contextClass=${contextClass.simpleName})"

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other == null || this::class != other::class) return false

            other as ResourceSaver<*, *>

            if (!isSubclassOf(resourceClass, other.resourceClass)) return false
            if (!isSubclassOf(viewModelClass, other.viewModelClass)) return false
            if (!isSubclassOf(contextClass, other.contextClass)) return false

            return true
        }

        override fun hashCode(): Int {
            var result = resourceClass.hashCode()
            result = 31 * result + viewModelClass.hashCode()
            result = 31 * result + contextClass.hashCode()
            return result
        }
    }

    class ResourceDeleter<R : Resource>(private val resourceClass: KClass<R>) : OperationType<R, ResourceIdContext, Unit>() {

        override val contextClass = ResourceIdContext::class

        override fun toString() = "ResourceDeleter(resourceClass=${resourceClass.simpleName}, contextClass=${contextClass.simpleName})"

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other == null || this::class != other::class) return false

            other as ResourceDeleter<*>

            if (!isSubclassOf(resourceClass, other.resourceClass)) return false
            if (!isSubclassOf(contextClass, other.contextClass)) return false

            return true
        }

        override fun hashCode(): Int {
            var result = resourceClass.hashCode()
            result = 31 * result + contextClass.hashCode()
            return result
        }


    }

}
