plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
    kotlin("kapt")
}
kapt {
    correctErrorTypes = true
}
android {
    namespace = "com.example.whattowatch"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.whattowatch"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildFeatures{
        viewBinding = true

    }
    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.viewpager2)
    implementation(libs.androidx.ui.test.android)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    // recyclerView
    implementation(libs.androidx.recyclerview)
    // For control over item selection of both touch and mouse driven selection
    implementation(libs.androidx.recyclerview.selection)
    //ViewModel
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.livedata.ktx)

    // retrofit
    implementation(libs.retrofit)
    // gson converter
    implementation(libs.converter.gson)

    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)
    //Glide
    implementation (libs.glide)
    annotationProcessor (libs.compiler)
    //picaso
    implementation (libs.picasso)
    implementation (libs.androidx.activity.ktx)
    implementation (libs.androidx.fragment.ktx)
    androidTestImplementation (libs.androidx.espresso.core.v350)

configurations.all {
    resolutionStrategy {
        force ("androidx.test.espresso:espresso-core:3.5.0") // Use the version compatible with your project
    }
}
}