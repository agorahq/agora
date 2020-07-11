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

        with(Projects) {
            commonMainApi(agoraComment)
        }

        with(Libs) {
            
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
            module = "agora.post",
            desc = "Post module for Agora."
    )
}

signing {
    isRequired = false
    sign(publishing.publications)
}
