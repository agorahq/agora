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