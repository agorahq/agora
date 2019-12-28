allprojects {
    repositories {
        mavenLocal() {
//            metadataSources {
//                gradleMetadata()
//                mavenPom()
//            }
        }
        mavenCentral()
        jcenter()
        kotlinx()
        jitpack()
        maven { url = uri("https://kotlin.bintray.com/ktor") }
        maven { url = uri("https://kotlin.bintray.com/kotlin-js-wrappers") }
    }
}

subprojects {
    apply<MavenPublishPlugin>()
}