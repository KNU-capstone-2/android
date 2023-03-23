package com.knu.cloud.navigation

enum class ProjectScreens {
    SplashScreen,
    LoginScreen,
    HomeScreen,
    InstanceScreen,
    SignUpScreen,
    // InstanceCreateScreen Set
    InstanceCreateScreen,
    DetailScreen,
    FlavorScreen,
    ;

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