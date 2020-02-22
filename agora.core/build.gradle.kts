plugins {
    kotlin("multiplatform")
    id("maven-publish")
    id("signing")
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
            commonMainApi(cobaltCore)

            jvmMainApi(kotlinStdLibJdk8)
            jvmMainApi(kotlinxHtmlJvm)
            jvmMainApi(flexmark)

            jsMainApi(kotlinStdLibJs)
            jsMainApi(kotlinxHtmlJs)
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