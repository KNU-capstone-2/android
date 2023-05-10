package com.knu.cloud

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import com.knu.cloud.components.FABContent
import com.knu.cloud.components.NavDrawer
import com.knu.cloud.components.ProjectAppBar
import com.knu.cloud.navigation.*
import com.knu.cloud.screens.instanceCreate.InstanceCreateScreen
import com.knu.cloud.screens.splash.ProjectSplashScreen
import com.knu.cloud.ui.theme.CloudTheme
import timber.log.Timber

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ProjectApp(appState: ProjectAppState = rememberProjectAppState()) {
    CloudTheme {
        Scaffold(
            scaffoldState = appState.scaffoldState,
            topBar = {
                if (appState.enabledTopAppBar.value) {
                    ProjectAppBar(
                        title = "POCKET",
                        path = "프로젝트 / Compute / 대시보드"
                    )
                }
            },
            floatingActionButton = {
                if (appState.isInstanceScreen) {
                    FABContent {
//                    showInstanceCreateDialog.value = true
                        appState.navController.navigate(MainDestination.INSTANCE_CREATE_ROUTE)
                    }
                }
            }
        ) { innerPaddingModifier ->
            Row(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ){
                LaunchedEffect(appState.navController){
                    appState.addDestinationChangedListener()
                }
                if(appState.enabledNavDrawer.value){
                    Timber.tag("navigation").d("NavDrawer composition 일어남")
                    NavDrawer(
                        modifier = Modifier
                            .weight(0.2f)
                            .background(colorResource(id = R.color.TopAppBar_background)),
                        sections = homeSections,
                        currentRoute = appState.currentRoute ?:ComputeSections.DashBoard.route,
                        navigateToRoute = appState.navActions::navigateToRoute,
                    )
                }
                NavHost( /* Root NavHost */
                    navController = appState.navController,
                    startDestination = MainDestination.SPlASH_ROUTE,
                    modifier = Modifier
                        .padding(innerPaddingModifier)
                        .weight(0.8f)
                ) {
                    composable(route = MainDestination.SPlASH_ROUTE) {
                        ProjectSplashScreen(navController = appState.navController)
                    }
                    authNavGraph(
                        onLoginClicked = appState.navActions::navigateToHome,
                        onSignUpClicked = appState.navActions::navigateToSignUp,
                        onSignUpSubmitClicked = appState.navActions::navigateToLogin
                        /* TODO: 로그인 request 보내기 */
                    )
                    homeNavGraph(
                        navController = appState.navController,
                        navActions = appState.navActions,
                        onInstanceCreateBtnClicked = appState.navActions::navigateToInstanceCreate,
                        onInstanceDetailBtnClicked = appState.navActions::navigateToInstanceDetail
                    )
                    dialog(
                        route = MainDestination.INSTANCE_CREATE_ROUTE,
                        dialogProperties = DialogProperties(usePlatformDefaultWidth = false)
                    ){
                        Surface(
                            modifier = Modifier
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
}