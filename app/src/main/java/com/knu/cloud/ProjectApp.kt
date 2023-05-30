package com.knu.cloud

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.*
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
import com.knu.cloud.components.MessageDialog
import com.knu.cloud.components.FABContent
import com.knu.cloud.components.NavDrawer
import com.knu.cloud.components.ProjectAppBar
import com.knu.cloud.di.ConfigModule.provideSessionManager
import com.knu.cloud.navigation.*
import com.knu.cloud.network.SessionManager
import com.knu.cloud.screens.instanceCreate.InstanceCreateScreen
import com.knu.cloud.screens.splash.ProjectSplashScreen
import com.knu.cloud.ui.theme.CloudTheme
import com.knu.cloud.utils.reformatScreenPath
import kotlinx.coroutines.delay
import timber.log.Timber

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ProjectApp (
    appState: ProjectAppState = rememberProjectAppState(),
    sessionManager: SessionManager = provideSessionManager()
) {
    val authState by sessionManager.authState.collectAsState()
    var firstRendering by remember { mutableStateOf(true) }
    if(appState.showLogOutDialog.value){
        MessageDialog(
            title = "로그아웃",
            message = "정말 로그아웃을 하시겠습니까?",
            onCancelClicked = {
                appState.showLogOutDialog.value = false
            },
            onConfirmClicked = {
                appState.showLogOutDialog.value = false
                sessionManager.logout()
            }
        )
    }
    LaunchedEffect(authState.isLoggedIn,appState.showLogOutDialog.value,firstRendering){
        Timber.d("authState.isLoggedId : ${authState.isLoggedIn}")
        delay(1000L)
        if(!firstRendering && !authState.isLoggedIn && !appState.showLogOutDialog.value){
            appState.navActions.navigateToLogin(null)
        }
        firstRendering = false
    }
    /*TODO : userName sessionManager에서 받아왔는데 Project앱에서 연동이 안됨 싱글톤인데 왜이러지. */
//    LaunchedEffect(authState.userName){
//        Timber.d("userName : ${authState.userName}")
//        if(authState.userName.isNotEmpty()){
//            appState.userName.value = authState.userName
//        }
//    }

    CloudTheme {
        Scaffold(
            scaffoldState = appState.scaffoldState,
            topBar = {
                if (appState.enabledTopAppBar.value) {
                    ProjectAppBar(
                        title = "POCKET",
                        path = reformatScreenPath(appState.currentRoute),
                        userName = "JaeWoong",
                        onLogOutClicked = {
                            appState.showLogOutDialog.value = true
                        }
                    )
                }
            },
            floatingActionButton = {
                if (appState.isInstanceScreen) {
                    FABContent {
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
                        onLoginClicked = {
                            appState.navActions.navigateToHome(null)
//                            sessionManager.login("123")
                        },
                        onSignUpClicked = appState.navActions::navigateToSignUp,
                        navigateToLogin = appState.navActions::navigateToLogin
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
                            InstanceCreateScreen(
                                onCloseClicked = {
                                    appState.navActions.navigateToRoute(ComputeSections.Instance.route)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}