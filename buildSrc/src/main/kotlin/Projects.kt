import org.gradle.kotlin.dsl.DependencyHandlerScope
import org.gradle.kotlin.dsl.project

object Projects {

    inline val DependencyHandlerScope.agoraCore get() = project(":agora.core")
    inline val DependencyHandlerScope.agoraComment get() = project(":agora.comment")
    inline val DependencyHandlerScope.agoraPost get() = project(":agora.post")
    inline val DependencyHandlerScope.agoraUser get() = project(":agora.user")

}