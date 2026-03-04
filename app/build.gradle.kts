plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    kotlin("plugin.serialization") version "2.2.0"
    id("com.google.devtools.ksp")
}

android {
    namespace = "uk.ac.tees.mad.resellit"
    compileSdk = 36

    defaultConfig {
        applicationId = "uk.ac.tees.mad.resellit"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    /**
     * supabase -- dependency
     */
    val supabaseBom = "3.3.0"
    val ktorVersion = "3.3.3"
    implementation(platform("io.github.jan-tennert.supabase:bom:$supabaseBom"))
    implementation("io.github.jan-tennert.supabase:postgrest-kt")
    implementation("io.github.jan-tennert.supabase:auth-kt")
    implementation("io.github.jan-tennert.supabase:realtime-kt")
    implementation("io.github.jan-tennert.supabase:storage-kt")
    implementation("io.ktor:ktor-client-okhttp:$ktorVersion")
    implementation("io.ktor:ktor-client-android:$ktorVersion")

    /**
     * icons  extended dependency
     */

    implementation("androidx.compose.material:material-icons-extended")

    /**
     * room dependency
     */
    val room_version = "2.8.4"

    implementation("androidx.room:room-runtime:${room_version}")
    ksp("androidx.room:room-compiler:$room_version")
    implementation("androidx.room:room-ktx:2.8.4")


    /**
     * work manager dependency
     */
    implementation("androidx.work:work-runtime-ktx:2.9.0")

    /**
     * compose navigation dependency
     */

    implementation("androidx.navigation:navigation-compose:2.7.7")

    /**
     * viewmodel dependency
     */
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.7")

    /**
     * splash screen dependency
     */
    implementation("androidx.core:core-splashscreen:1.0.1")

    /**
     * image loader library
     */
    implementation("io.coil-kt:coil:2.7.0")
    implementation("io.coil-kt:coil-compose:2.7.0")

}