package com.knu.cloud.navigation

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.*
import androidx.navigation.compose.composable
import com.knu.cloud.screens.home.dashboard.ProjectDashBoardScreen
import com.knu.cloud.screens.home.instance.ProjectInstanceScreen
import com.knu.cloud.screens.home.setting.ProjectSettingScreen
import com.knu.cloud.screens.instanceCreate.*
import com.knu.cloud.screens.login.LoginScreen

fun NavGraphBuilder.homeNavGraph(
    onInstanceCreateBtnClicked: (NavBackStackEntry) -> Unit,
) {
    navigation(
        route = MainDestination.HOME_ROUTE,
        startDestination = HomeSections.DashBoard.route
    ){
        composable(HomeSections.DashBoard.route){ from ->
            ProjectDashBoardScreen()
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
) {
    navigation(
        route = MainDestination.AUTH_ROUTE,
        startDestination = MainDestination.LOGIN_ROUTE
    ){
        composable(route = MainDestination.LOGIN_ROUTE){ from ->
            LoginScreen(onLoginClick = { onLoginClicked(from)})
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