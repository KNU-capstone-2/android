object Dependencies {
    const val CORE_KTX = "androidx.core:core-ktx:${Versions.CORE_KTX}"
    const val COMPOSE_MATERIAL = "androidx.compose.material:material:${Versions.MATERIAL}"

    /* Timber */
    const val TIMBER = "com.jakewharton.timber:timber:${Versions.TIMBER}"

    /*
    LifeCycle viewModel
    Coroutine LifeCycle Scopes
    */
    const val LIFECYCLE_VIEWMODEL_COMPOSE =
        "androidx.lifecycle:lifecycle-viewmodel-compose:${Versions.LIFECYCLE}"
    const val LIFECYCLE_SAVEDSTATE =
        "androidx.lifecycle:lifecycle-viewmodel-savedstate:${Versions.LIFECYCLE}"
    const val LIFECYCLE_VIEWMODEL_KTX =
        "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.LIFECYCLE}"
    const val LIFECYCLE_RUNTIME_KTX =
        "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.LIFECYCLE}"

    // navigation
    const val COMPOSE_NAVIGATION =
        "androidx.navigation:navigation-compose:${Versions.COMPOSE_NAVIGATION}"

    /* Dagger - Hilt */
    const val DAGGER_HILT = "com.google.dagger:hilt-android:${Versions.HILT}"
    const val DAGGER_HILT_KAPT = "com.google.dagger:hilt-compiler:${Versions.HILT}"
    const val HILT_EXTENSION_WORK = "androidx.hilt:hilt-work:${Versions.HILT_EXTENSION}"
    const val HILT_EXTENSION_KAPT = "androidx.hilt:hilt-compiler:${Versions.HILT_EXTENSION}"
    const val HILT_COMPOSE_NAVIGATION = "androidx.hilt:hilt-navigation-compose:${Versions.HILT_EXTENSION}"

    const val RETROFIT = "com.squareup.retrofit2:retrofit:${Versions.RETROFIT}"
    const val RETROFIT_CONVERTER_MOSHI =
        "com.squareup.retrofit2:converter-moshi:${Versions.RETROFIT}"
    const val RETROFIT_CONVERTER_GSON =
        "com.squareup.retrofit2:converter-gson:${Versions.RETROFIT}"

    const val MOSHI = "com.squareup.moshi:moshi:${Versions.MOSHI}"
    const val MOSHI_KOTLIN = "com.squareup.moshi:moshi-kotlin:${Versions.MOSHI}"
    const val MOSHI_KAPT = "com.squareup.moshi:moshi-kotlin-codegen:${Versions.MOSHI}"

    const val OKHTTP = "com.squareup.okhttp3:okhttp:${Versions.OKHTTP}"
    const val OKHTTP_LOGGING_INTERCEPTOR =
        "com.squareup.okhttp3:logging-interceptor:${Versions.OKHTTP}"

    const val COROUTINE_CORE = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.COROUTINE}"
    const val COROUTINE_ANDROID =
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.COROUTINE}"
    const val COROUTINE_PLAY_SERVICES =
        "org.jetbrains.kotlinx:kotlinx-coroutines-play-services:${Versions.COROUTINE}"

    const val COIL = "io.coil-kt:coil:${Versions.COIL}"
    const val COIL_GIF = "io.coil-kt:coil-gif:${Versions.COIL}"
    const val COIL_COMPOSE = "io.coil-kt:coil-compose:${Versions.COIL}"

    const val ROOM_RUNTIME = "androidx.room:room-runtime:${Versions.ROOM}"
    const val ROOM_KTX = "androidx.room:room-ktx:${Versions.ROOM}"
    const val ROOM_KAPT = "androidx.room:room-compiler:${Versions.ROOM}"
    const val ROOM_PAGING = "androidx.room:room-paging:${Versions.ROOM}"

    const val KOTLIN_SERIALIZATION =
        "org.jetbrains.kotlinx:kotlinx-serialization-json:${Versions.KOTLIN_SERIALIZATION}"

    const val PREFERENCES_DATASTORE =
        "androidx.datastore:datastore-preferences:${Versions.DATASTORE}"

    const val PAGING = "androidx.paging:paging-runtime-ktx:${Versions.PAGING}"

    const val WORKMANAGER = "androidx.work:work-runtime-ktx:${Versions.WORKMANAGER}"

    const val ACTIVITY_KTX =
        "androidx.activity:activity-ktx:${Versions.ACTIVITY_KTX}"

    const val ACTIVITY_COMPOSE =
        "androidx.activity:activity-compose:${Versions.ACTIVITY_KTX}"

    const val COMPOSE_UI =
        "androidx.compose.ui:ui:${Versions.COMPOSE_UI}"

    const val COMPOSE_UI_PREVIEW =
        "androidx.compose.ui:ui-tooling-preview:${Versions.COMPOSE_UI}"

}