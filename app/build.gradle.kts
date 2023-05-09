plugins {
    id (Plugins.ANDROID_APPLICATION)
    id (Plugins.KOTLIN_ANDROID_PLUGIN)
    id (Plugins.HILT_PLUGIN)
    id (Plugins.SECRETS_GRADLE_PLUGIN)
    id (Plugins.PARCELIZE)
    id (Plugins.KAPT)
}

android {
    namespace = "com.knu.cloud"
    compileSdk = DefaultConfig.COMPILE_SDK_VERSION

    defaultConfig {
        applicationId = "com.knu.cloud"
        minSdk = DefaultConfig.MIN_SDK_VERSION
        targetSdk = DefaultConfig.TARGET_SDK_VERSION
        versionCode = DefaultConfig.VERSION_CODE
        versionName = DefaultConfig.VERSION_NAME

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        named("release") {
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
        jvmTarget = JavaVersion.VERSION_11.toString()
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.3.2"
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    kapt {
        correctErrorTypes = true
    }
}

dependencies {

    implementation (Dependencies.CORE_KTX)
    implementation (Dependencies.LIFECYCLE_RUNTIME_KTX)
    implementation (Dependencies.ACTIVITY_COMPOSE)
    implementation (Dependencies.COMPOSE_UI)
    implementation (Dependencies.COMPOSE_UI_PREVIEW)
    implementation (Dependencies.COMPOSE_MATERIAL)

    // Test
    testImplementation (Testing.JUNIT4)
    testImplementation (Testing.MOCK_WEB_SERVER)
    androidTestImplementation (Testing.ANDROID_JUNIT)
    androidTestImplementation (Testing.ESPRESSO_CORE)
    androidTestImplementation (Testing.COMPOSE_TEST)
    debugImplementation (Debugs.COMPOSE_UI_TOOLING)
    debugImplementation (Debugs.COMPOSE_UI_TEST_MANIFEST)

    // Timber setting
    implementation (Dependencies.TIMBER)

    // coil
    implementation (Dependencies.COIL)
    implementation (Dependencies.COIL_GIF)
    implementation (Dependencies.COIL_COMPOSE)

    // navigation
    implementation (Dependencies.COMPOSE_NAVIGATION)

    /*
     LifeCycle viewModel Scope
     Coroutine LifeCycle Scopes
    */
    implementation (Dependencies.LIFECYCLE_RUNTIME_KTX)
    implementation (Dependencies.LIFECYCLE_SAVEDSTATE)
    implementation (Dependencies.LIFECYCLE_VIEWMODEL_COMPOSE)
    implementation (Dependencies.LIFECYCLE_RUNTIME_COMPOSE)

    // Coroutine
    implementation (Dependencies.COROUTINE_CORE)
    implementation (Dependencies.COROUTINE_ANDROID)
    implementation (Dependencies.COROUTINE_PLAY_SERVICES)

    // Room
    implementation (Dependencies.ROOM_RUNTIME)
    // To use Kotlin annotation processing tool (kapt)
    kapt (Dependencies.ROOM_KAPT)
    implementation (Dependencies.ROOM_KTX)
    implementation (Dependencies.ROOM_PAGING)

    // Retrofit
    implementation (Dependencies.RETROFIT)
    implementation (Dependencies.RETROFIT_CONVERTER_GSON)
    implementation (Dependencies.RETROFIT_CONVERTER_MOSHI)

    //moshi
    implementation (Dependencies.MOSHI_KOTLIN)
    implementation (Dependencies.MOSHI)
    kapt (Dependencies.MOSHI_KAPT)

    // OkHttp
    implementation (Dependencies.OKHTTP)
    implementation (Dependencies.OKHTTP_LOGGING_INTERCEPTOR)

    /*
     Activity와 Fragment에 viewModel 의존성을 주입하는 과정에서
     by 위임자를 통한 delegate 패턴으로 viewModel을 초기화할 수 있음.
     delegate 패턴을 사용하면 Factory를 사욯하지 않고도 ViewModel을 생성할 수 있음.
     */
    implementation (Dependencies.ACTIVITY_KTX)

    // kotlinx serialization
    implementation (Dependencies.KOTLIN_SERIALIZATION)

    // DataStore
    implementation (Dependencies.PREFERENCES_DATASTORE)

    // Paging
    implementation (Dependencies.PAGING)

    // WorkManager
    implementation (Dependencies.WORKMANAGER)

    // Lottie
    implementation (Dependencies.LOTTIE)

    //
    implementation ("androidx.compose.runtime:runtime-livedata:1.3.2")

    // Hilt
    implementation (Dependencies.DAGGER_HILT)
    kapt (Dependencies.DAGGER_HILT_KAPT)
    // Hilt Extension (workmanager에 의존성을 주입하기 위함)
    implementation (Dependencies.HILT_EXTENSION_WORK)
    kapt (Dependencies.HILT_EXTENSION_KAPT)
    // Hilt navigation
    implementation (Dependencies.HILT_COMPOSE_NAVIGATION)

}

tasks.register("prepareKotlinBuildScriptModel"){}