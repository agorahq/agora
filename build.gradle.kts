allprojects {
    repositories {
        mavenLocal()
        mavenCentral()
        jcenter()
        maven("https://dl.bintray.com/kotlin/kotlinx")
        maven("https://kotlin.bintray.com/ktor")
        maven("https://kotlin.bintray.com/kotlin-js-wrappers")
    }
}

tasks.register("stage") {
    doFirst {
        tasks.findByName("clean")
    }
    subprojects.forEach { project ->
        val build = project.tasks.first { it.name.contains("build") }
        dependsOn(build)
    }
}
