plugins {
    alias(libs.plugins.android)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.dagger.hilt)
    kotlin("kapt")
}

android {
    namespace = "br.com.dcarv.criticalchallenge"
    compileSdk = 34

    defaultConfig {
        applicationId = "br.com.dcarv.criticalchallenge"
        minSdk = 28
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "br.com.dcarv.criticalchallenge.test.HiltTestRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    flavorDimensions += "source"
    productFlavors {
        create("bbc") {
            dimension = "source"
        }
        create("globo") {
            dimension = "source"
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.ktx)
    implementation(libs.lifecycle.viewModelCompose)
    implementation(libs.compose.activity)
    implementation(platform("androidx.compose:compose-bom:2023.08.00"))
    implementation(libs.compose.ui)
    implementation(libs.compose.graphics)
    implementation(libs.compose.preview)
    implementation(libs.compose.material3)
    implementation(libs.coroutines.android)
    implementation(libs.dagger.hilt.android)
    kapt(libs.dagger.hilt.compiler)
    implementation(libs.retrofit)
    implementation(libs.retrofit.moshi)
    implementation(libs.okhttp)
    implementation(libs.coil)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.dagger.hilt.navigation.compose)
    implementation(libs.androidx.biometric)
    implementation(libs.androidx.windowSizeClass)
    testImplementation(libs.junit)
    testImplementation(libs.mockk.android)
    testImplementation(libs.mockk.agent)
    testImplementation(libs.coroutines.core)
    testImplementation(libs.coroutines.test)
    testImplementation(libs.androidx.core.ktx)
    testImplementation(libs.robolectric)
    androidTestImplementation(libs.junit.extensions)
    androidTestImplementation(libs.espresso)
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.08.00"))
    androidTestImplementation(libs.compose.ui.test)
    androidTestImplementation(libs.okhttp.mockwebserver)
    androidTestImplementation(libs.dagger.hilt.testing)
    androidTestImplementation(libs.coil.test)
    kaptAndroidTest(libs.dagger.hilt.testing.compiler)
    debugImplementation(libs.compose.ui.tooling)
    debugImplementation(libs.compose.ui.test.manifest)
}

// Allow references to generated code
kapt {
    correctErrorTypes = true
}
