plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral() {
        metadataSources {
            gradleMetadata()
            mavenPom()
        }
    }
    jcenter() {
        metadataSources {
            gradleMetadata()
            mavenPom()
        }
    }
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.3.71")
}