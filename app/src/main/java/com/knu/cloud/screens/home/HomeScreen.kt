package com.knu.cloud.screens.home

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.knu.cloud.ProjectAppState
import com.knu.cloud.components.FABContent
import com.knu.cloud.navigation.*
import com.knu.cloud.rememberProjectAppState
import com.knu.cloud.screens.home.dashboard.ProjectBottomBar
import com.knu.cloud.screens.instanceCreate.InstanceCreateScreen
import com.knu.cloud.screens.splash.ProjectSplashScreen

@Composable
fun HomeScreen(appState: ProjectAppState = rememberProjectAppState()) {
    Scaffold(
        bottomBar = {
            if(appState.shouldShowBottomBar){
                ProjectBottomBar(
                    sections = appState.bottomBarTabs,
                    currentRoute = appState.currentRoute!!,
                    navigateToRoute = appState::navigateToBottomBarRoute
                )
            }
        },
        floatingActionButton = {
            if(appState.isInstanceScreen){
                FABContent {
                    appState.navController.navigate(MainDestination.INSTANCE_CREATE_ROUTE)
                }
            }
        }
    ) {innerPaddingModifier ->
        NavHost( /* Root NavHost */
            navController = appState.navController,
            startDestination = MainDestination.SPlASH_ROUTE,
            modifier = Modifier.padding(innerPaddingModifier)
        ){
            composable(route = MainDestination.SPlASH_ROUTE){
                ProjectSplashScreen(navController = appState.navController)
            }
            homeNavGraph(onInstanceCreateBtnClicked = appState::navigateToInstanceCreate)
            authNavGraph(onLoginClicked = appState::navigateToHome)

            composable(route = MainDestination.INSTANCE_CREATE_ROUTE){
                InstanceCreateScreen()
            }
        }
    }

}