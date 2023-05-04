package com.knu.cloud.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.*
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.knu.cloud.R
import com.knu.cloud.components.NavDrawer
import com.knu.cloud.screens.instanceCreate.*
import com.knu.cloud.screens.auth.login.LoginScreen
import com.knu.cloud.screens.auth.signup.SignUpScreen
import com.knu.cloud.screens.home.dashboard.DashBoardScreen
import com.knu.cloud.screens.home.image.ImageScreen
import com.knu.cloud.screens.home.instance.InstanceScreen
import com.knu.cloud.screens.home.keypairs.KeypairsScreen
import com.knu.cloud.screens.home.networks.NetworksScreen
import com.knu.cloud.screens.home.securitygroup.SecurityGroupScreen
import com.knu.cloud.screens.home.volume.VolumeScreen
import com.knu.cloud.screens.instanceCreate.detail.DetailScreen
import com.knu.cloud.screens.instanceCreate.keypair.KeypairScreen


fun NavGraphBuilder.homeNavGraph(
    navController: NavController,
    navActions: NavActions,
    onInstanceCreateBtnClicked: (NavBackStackEntry) -> Unit,
) {
    navigation(
        route = MainDestination.HOME_ROUTE,
        startDestination = ComputeSections.DashBoard.route
    ){
        composable(ComputeSections.DashBoard.route){ from ->
            DashBoardScreen()
        }
        composable(ComputeSections.Instance.route){ from ->
            InstanceScreen(onInstanceCreateClick = { onInstanceCreateBtnClicked(from) })
        }
        composable(ComputeSections.Image.route){ from ->
            ImageScreen()
        }
        composable(ComputeSections.KeyPairs.route){ from ->
            KeypairsScreen()
        }
        composable(ComputeSections.Volume.route){ from ->
            VolumeScreen()
        }
        composable(NetworkSections.Networks.route){ from ->
            NetworksScreen()
        }
        composable(NetworkSections.SecurityGroup.route){ from ->
            SecurityGroupScreen()
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
fun NavGraphBuilder.instanceCreateNavGraph(
    instanceCreateViewModel: InstanceCreateViewModel
) {
    composable(InstanceCreateSections.Details.route){ from ->
        DetailScreen(viewModel = instanceCreateViewModel)
    }
    composable(InstanceCreateSections.Source.route){ from ->
        SourceScreen(viewModel = instanceCreateViewModel)
    }
    composable(InstanceCreateSections.Flavor.route){ from ->
        FlavorScreen(viewModel = instanceCreateViewModel)
    }
    composable(InstanceCreateSections.Network.route){ from ->
        NetworkScreen(viewModel = instanceCreateViewModel)
    }
    composable(InstanceCreateSections.Keypair.route){ from ->
        KeypairScreen(viewModel = instanceCreateViewModel)
    }
}