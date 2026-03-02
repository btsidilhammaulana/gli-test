import java.util.Properties

plugins {
  alias(libs.plugins.android.library)
  alias(libs.plugins.compose.compiler)
}

val appProps = rootProject.extra["appProps"] as Properties

android {
  compileSdk = appProps["COMPILE_SDK"].toString().toInt()

  namespace = appProps["PROJECT_ID"].toString() + ".custom"

  defaultConfig {
    consumerProguardFiles("consumer-rules.pro")
  }

  buildTypes {
    release {
      isMinifyEnabled = false
      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
    }
  }

  buildFeatures {
    viewBinding = true
  }

  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
  }
}

dependencies {
  implementation(project( ":core:model"))

  implementation(libs.androidx.core.ktx)
  implementation(libs.androidx.appcompat)
  implementation(libs.material)

  implementation(libs.androidx.runtime)
  implementation(libs.androidx.activity)
  implementation(libs.androidx.material3)

  testImplementation(libs.junit)
  androidTestImplementation(libs.androidx.junit)
  androidTestImplementation(libs.androidx.espresso.core)
}