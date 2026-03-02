import java.util.Properties

plugins {
  alias(libs.plugins.android.library)
  alias(libs.plugins.hilt.android)
  alias(libs.plugins.ksp)
  alias(libs.plugins.kotlin.serialization)
  alias(libs.plugins.kotlin.parcelize)
}

val appProps = rootProject.extra["appProps"] as Properties
val secretProps = rootProject.extra["secretProps"] as Properties

android {
  compileSdk = appProps["COMPILE_SDK"].toString().toInt()

  namespace = appProps["PROJECT_ID"].toString() + ".model"

  defaultConfig {
    consumerProguardFiles("consumer-rules.pro")
  }

  buildTypes {
    all {
      buildConfigField("String", "BASE_IMAGE_URL", secretProps["BASE_IMAGE_URL"].toString())
      buildConfigField("String", "API_READ_ACCESS_TOKEN", secretProps["API_READ_ACCESS_TOKEN"].toString())
    }

    release {
      isMinifyEnabled = false
      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
    }
  }

  buildFeatures {
    buildConfig = true
    resValues = true
  }

  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
  }
}

dependencies {
  implementation(libs.hilt.android)
  ksp(libs.hilt.compiler)

  api(libs.gson)
  implementation(libs.okhttp3.okhttp)

  testImplementation(libs.junit)
  androidTestImplementation(libs.androidx.junit)
  androidTestImplementation(libs.androidx.espresso.core)
}