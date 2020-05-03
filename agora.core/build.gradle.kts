plugins {
    kotlin("multiplatform")
    id("maven-publish")
    id("signing")
    kotlin("plugin.serialization") version "1.3.71"
}

kotlin {

    jvm {
        jvmTarget(JavaVersion.VERSION_1_8)
        withJava()
    }

    js {
    }

    dependencies {

        with(Libs) {
            commonMainApi(kotlinStdLibCommon)
            commonMainApi(kotlinxCoroutines)
            commonMainApi(kotlinReflect)
            commonMainApi(kotlinxHtmlCommon)
            commonMainApi("org.jetbrains.kotlinx:kotlinx-serialization-runtime-common:0.20.0")
            commonMainApi(cobaltCore)

            jvmMainApi(kotlinStdLibJdk8)
            jvmMainApi("org.jetbrains.kotlinx:kotlinx-serialization-runtime:0.20.0")
            jvmMainApi(kotlinxHtmlJvm)
            jvmMainApi(flexmark)

            jsMainApi(kotlinStdLibJs)
            jsMainApi(kotlinxHtmlJs)
            jsMainApi("org.jetbrains.kotlinx:kotlinx-serialization-runtime-js:0.20.0")
        }

        with(TestLibs) {
            commonTestApi(kotlinTestCommon)
            commonTestApi(kotlinTestAnnotationsCommon)

            jvmTestApi(kotlinTestJunit)

            jsTestApi(kotlinTestJs)
        }
    }
}

publishing {
    publishWith(
            project = project,
            module = "agora.core",
            desc = "Core components of Agora."
    )
}

signing {
    isRequired = false
    sign(publishing.publications)
}