package com.knu.cloud.navigation

import androidx.compose.foundation.layout.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.*
import androidx.navigation.compose.composable
import com.knu.cloud.screens.instanceCreate.*
import com.knu.cloud.screens.auth.login.LoginScreen
import com.knu.cloud.screens.auth.signup.SignUpScreen
import com.knu.cloud.screens.home.dashboard.DashboardScreen
import com.knu.cloud.screens.home.image.ImageScreen
import com.knu.cloud.screens.home.instance.InstanceScreen
import com.knu.cloud.screens.home.instanceDetail.InstanceDetailScreen
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
    onInstanceDetailBtnClicked : (String) -> Unit
) {
    navigation(
        route = MainDestination.HOME_ROUTE,
        startDestination = ComputeSections.DashBoard.route
    ){
        composable(ComputeSections.DashBoard.route){ from ->
            DashboardScreen()
        }
        composable(ComputeSections.Instance.route){ from ->
            InstanceScreen(
                onInstanceCreateClicked = { onInstanceCreateBtnClicked(from) },
                onInstanceDetailClicked = onInstanceDetailBtnClicked
            )
        }

        composable(
            route = "${MainDestination.INSTANCE_DETAIL_ROUTE}/{id}",
            arguments = listOf(
                navArgument("id"){
                    type = NavType.StringType
                }
            )
        ){ navBackStackEntry ->
            val id = navBackStackEntry.arguments?.getString("id")!!
            InstanceDetailScreen(id = id)
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