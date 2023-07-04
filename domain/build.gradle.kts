plugins {
    id("org.jetbrains.kotlin.jvm")
    id("java-library")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_7
    targetCompatibility = JavaVersion.VERSION_1_7
}


dependencies {
    implementation ("androidx.paging:paging-common:3.1.1")
    implementation(libs.kotlinx.coroutine)
    //implementation(libs.kotlinx.serialization)
}