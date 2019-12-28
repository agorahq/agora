plugins {
    kotlinMultiplatform
}

group = "org.agorahq.agora"

kotlin {

    jvm {
        jvmTarget(JavaVersion.VERSION_1_8)
        withJava()
    }

    js {
    }

    dependencies {

        with(Projects) {
            commonMainApi(agoraCore)
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