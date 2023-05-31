plugins {
    `kotlin-dsl`
}
repositories {
    mavenCentral()
}
dependencies {
    implementation ("org.jetbrains.kotlin:kotlin-stdlib:1.8.21")
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-scalars:2.9.0")
    implementation ("com.squareup.okhttp3:logging-interceptor:3.14.9")
}