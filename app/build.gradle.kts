import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.dagger.hilt.android.plugin)
    id("kotlin-parcelize")
    id("kotlin-kapt")
    id("androidx.navigation.safeargs.kotlin")

}

android {
    namespace = "com.example.searchmovie"
    compileSdk = 34

    testOptions {
        unitTests{
            isReturnDefaultValues = true
        }
    }

    defaultConfig {
        applicationId = "com.example.searchmovie"
        minSdk = 29
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        //load the values from secrets.properties file
        val keystoreFile = project.rootProject.file("config.properties")
        val properties = Properties()
        properties.load(keystoreFile.inputStream())

        //return empty key in case something goes wrong
        val apiKey = properties.getProperty("TMDB_API_KEY") ?: ""
        val baseURL = properties.getProperty("BASE_URL") ?: ""

        buildConfigField(
            type = "String",
            name = "TMDB_API_KEY",
            value = apiKey
        )

        buildConfigField(
            type = "String",
            name = "BASE_URL",
            value = baseURL
        )
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
    buildFeatures {
        buildConfig = true
        viewBinding = true
        dataBinding = true
    }
}



dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.retrofit)
    implementation(libs.retrofit.gson)
    implementation(libs.hilt.android)
    implementation(libs.androidx.databinding.runtime)
    implementation(libs.androidx.databinding.common)
    implementation(libs.androidx.recyclerview)
    implementation(libs.androidx.activity.ktx)
    implementation(libs.androidx.fragment.ktx)
    implementation(libs.androidx.paging.runtime)
    implementation(libs.androidx.legacy.support.v4)
    implementation(libs.androidx.coordinatorlayout)
    kapt(libs.hilt.compiler)
    kapt(libs.hilt.androidx.compiler)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.androidx.transition.ktx)
    implementation(libs.io.coil.ktx)
    implementation(libs.io.coil.gif.ktx)


    testImplementation(libs.junit)
    testImplementation(libs.org.mokito.kotlin)
    testImplementation(libs.org.jetbrains.kotlinx.cotoutines.test)
    testImplementation(libs.androidx.paging.testing)
    testImplementation(libs.mockito.inline)
    androidTestImplementation(libs.androidx.espresso.core)
}
