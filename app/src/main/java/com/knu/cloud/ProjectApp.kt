package com.knu.cloud

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import com.knu.cloud.components.FABContent
import com.knu.cloud.components.ProjectAppBar
import com.knu.cloud.components.ProjectBottomBar
import com.knu.cloud.navigation.MainDestination
import com.knu.cloud.navigation.authNavGraph
import com.knu.cloud.navigation.homeNavGraph
import com.knu.cloud.screens.instanceCreate.InstanceCreateScreen
import com.knu.cloud.screens.splash.ProjectSplashScreen
import com.knu.cloud.ui.theme.CloudTheme

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ProjectApp(appState: ProjectAppState = rememberProjectAppState()) {
    CloudTheme {
        Scaffold(
            scaffoldState = appState.scaffoldState,
            topBar = {
                ProjectAppBar(
                    title = "POCKET",
                    path = "프로젝트 / Compute / 대시보드"
                )
            },
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
//                    showInstanceCreateDialog.value = true
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
                authNavGraph(
                    onLoginClicked = appState::navigateToHome,
                    onSignUpClicked = appState::navigateToSignUp,
                    onSignUpSubmitClicked = appState::navigateToLogin
                    /*TODO: 로그인 request 보내기*/
                )

                dialog(
                    route = MainDestination.INSTANCE_CREATE_ROUTE,
                    dialogProperties = DialogProperties(usePlatformDefaultWidth = false)
                ){
                    Surface(modifier = Modifier
                        .width(1200.dp)
                        .height(800.dp)
                        .padding(10.dp)
                        .clip(RoundedCornerShape(30.dp))
                    ) {
                        InstanceCreateScreen()
                    }
                }
            }
        }
    }
}


//        Surface(
//            color = MaterialTheme.colors.background,
//            modifier = Modifier
//                .fillMaxSize()
//        ) {
//            Column(
//                verticalArrangement = Arrangement.Top,
//                horizontalAlignment = Alignment.CenterHorizontally
//            ) {
//                ProjectNavigation()
//            }
//        }
