import java.util.Properties

plugins {
  alias(libs.plugins.android.library)
  alias(libs.plugins.hilt.android)
  alias(libs.plugins.ksp)
}

val appProps = rootProject.extra["appProps"] as Properties
val secretProps = rootProject.extra["secretProps"] as Properties

android {
  compileSdk = appProps["COMPILE_SDK"].toString().toInt()

  namespace = appProps["PROJECT_ID"].toString() + ".data"

  defaultConfig {
    consumerProguardFiles("consumer-rules.pro")
  }

  buildTypes {
    release {
      isMinifyEnabled = false
      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
    }
  }

  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
  }
}

dependencies {
  implementation(project(":core:local"))
  implementation(project(":core:model"))
  implementation(project(":core:network"))

  implementation(libs.hilt.android)
  ksp(libs.hilt.compiler)

  testImplementation(libs.junit)
  androidTestImplementation(libs.androidx.junit)
  androidTestImplementation(libs.androidx.espresso.core)
}