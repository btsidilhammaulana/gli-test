import java.util.Properties

plugins {
  alias(libs.plugins.android.application)
  alias(libs.plugins.hilt.android)
  alias(libs.plugins.ksp)
}

val appProps = rootProject.extra["appProps"] as Properties

android {
  compileSdk = appProps["COMPILE_SDK"].toString().toInt()

  namespace = appProps["PROJECT_ID"].toString()

  defaultConfig {
    applicationId = appProps["PROJECT_ID"].toString()
    minSdk = appProps["MINIMUM_SDK"].toString().toInt()
    targetSdk = appProps["TARGET_SDK"].toString().toInt()
    versionCode = appProps["VERSION_CODE"].toString().toInt()
    versionName = appProps["VERSION_NAME"].toString()

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
  }

  buildTypes {
    debug {
      isDebuggable = true
      isMinifyEnabled = false
    }
    release {
      isMinifyEnabled = false
      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
    }
  }

  buildFeatures {
    buildConfig = true
    viewBinding = true
  }

  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
  }
}

dependencies {
  implementation(project(":core:domain"))
  implementation(project(":core:model"))
  implementation(project(":custom"))

  implementation(libs.androidx.core.ktx)
  implementation(libs.androidx.appcompat)
  implementation(libs.androidx.activity)
  implementation(libs.material)

  implementation(libs.hilt.android)
  ksp(libs.hilt.compiler)

  implementation(libs.bannerviewpager)

  implementation(libs.coil)
  implementation(libs.coil.compose)
  implementation(libs.coil.network.okhttp)

  implementation(libs.androidx.paging.runtime)

  implementation(libs.androidx.core.splashscreen)

  implementation(libs.androidx.swiperefreshlayout)

  implementation(libs.youtube.video.player)

  testImplementation(libs.junit)
  androidTestImplementation(libs.androidx.junit)
  androidTestImplementation(libs.androidx.espresso.core)
}