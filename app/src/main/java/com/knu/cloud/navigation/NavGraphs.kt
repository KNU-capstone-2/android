package com.knu.cloud.navigation

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.*
import androidx.navigation.compose.composable
import com.knu.cloud.screens.home.instance.ProjectInstanceScreen
import com.knu.cloud.screens.home.setting.ProjectSettingScreen
import com.knu.cloud.screens.instanceCreate.*
import com.knu.cloud.screens.auth.login.LoginScreen
import com.knu.cloud.screens.auth.signup.SignUpScreen
import com.knu.cloud.screens.home.dashboard.DashBoardScreen
import com.knu.cloud.screens.instanceCreate.detail.DetailScreen

fun NavGraphBuilder.homeNavGraph(
    onInstanceCreateBtnClicked: (NavBackStackEntry) -> Unit,
) {
    navigation(
        route = MainDestination.HOME_ROUTE,
        startDestination = HomeSections.DashBoard.route
    ){
        composable(HomeSections.DashBoard.route){ from ->
            DashBoardScreen()
        }
        composable(HomeSections.Instance.route){ from ->
            ProjectInstanceScreen(onInstanceCreateClick = { onInstanceCreateBtnClicked(from) })
        }
        composable(HomeSections.Setting.route){ from ->
            ProjectSettingScreen()
        }
    }
}
@OptIn(ExperimentalComposeUiApi::class)
fun NavGraphBuilder.authNavGraph(
    onLoginClicked: (NavBackStackEntry) -> Unit,
    onSignUpClicked :(NavBackStackEntry) -> Unit,
    onSignUpSubmitClicked : (NavBackStackEntry) -> Unit
) {
    navigation(
        route = MainDestination.AUTH_ROUTE,
        startDestination = MainDestination.LOGIN_ROUTE
    ){
        composable(route = MainDestination.LOGIN_ROUTE){ from ->
            LoginScreen(
                onLoginClick = { onLoginClicked(from)},
                onSignUpClick = {onSignUpClicked(from)}
            )
        }
        composable(route = MainDestination.SIGNUP_ROUTE){from ->
            SignUpScreen(onSignUpSubmitClick = { onSignUpSubmitClicked(from)})
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
fun NavGraphBuilder.instanceCreateNavGraph() {
    val instanceCreateViewModel = InstanceCreateViewModel()
    composable(InstanceCreateSections.Details.route){ from ->
        DetailScreen(viewModel = instanceCreateViewModel)
//        InstanceCreateDetail()
    }
    composable(InstanceCreateSections.Source.route){ from ->
        SourceScreen(viewModel = instanceCreateViewModel)
//        InstanceCreateSource()
    }
    composable(InstanceCreateSections.Flavor.route){ from ->
        FlavorScreen(viewModel = instanceCreateViewModel)
//        InstanceCreateFlavor()
    }
    composable(InstanceCreateSections.Network.route){ from ->
        NetworkScreen(viewModel = instanceCreateViewModel)
//        InstanceCreateNetwork()
    }
}