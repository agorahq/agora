plugins {
    kotlinMultiplatform
}

group = "org.agorahq.markland"

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
            commonMainApi(cobaltDatatypes)

            jvmMainApi(kotlinStdLibJdk8)
            jvmMainApi(kotlinxHtmlJvm)

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