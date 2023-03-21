package com.knu.cloud.navigation

enum class ProjectScreens {
    SplashScreen,
    LoginScreen,
    HomeScreen,
    InstanceScreen,
    InstanceCreateScreen,
    SignUpScreen;

    companion object {
        fun fromRoute(route:String?) : ProjectScreens
                = when(route?.substringBefore("/")){
            SplashScreen.name -> SplashScreen
            LoginScreen.name -> LoginScreen
            null -> HomeScreen
            else -> throw IllegalArgumentException("Route $route is not recognized")
        }
    }
}