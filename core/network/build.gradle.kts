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

  namespace = appProps["PROJECT_ID"].toString() + ".network"

  defaultConfig {
    consumerProguardFiles("consumer-rules.pro")
  }

  buildTypes {
    all {
      resValue("string", "base_url", secretProps["BASE_URL"].toString())
    }
    release {
      isMinifyEnabled = false
      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
    }
  }

  buildFeatures {
    resValues = true
  }

  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
  }
}

dependencies {
  implementation(project(":core:model"))
  implementation(project(":core:local"))

  implementation(libs.hilt.android)
  ksp(libs.hilt.compiler)

  api(libs.retrofit.core)
  api(libs.retrofit.converter.gson)

  implementation(libs.okhttp3.okhttp)
  implementation(libs.okhttp3.logging.interceptor)

  debugImplementation(libs.chucker.library)
  releaseImplementation(libs.chucker.no.op)


  testImplementation(libs.junit)
  androidTestImplementation(libs.androidx.junit)
  androidTestImplementation(libs.androidx.espresso.core)
}