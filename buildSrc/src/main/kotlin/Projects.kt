import org.gradle.kotlin.dsl.DependencyHandlerScope
import org.gradle.kotlin.dsl.project

object Projects {

    inline val DependencyHandlerScope.marklandCore get() = project(":markland.core")

}