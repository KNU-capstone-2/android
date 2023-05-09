package com.knu.cloud.navigation

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph

class NavActions(private val navController: NavController) {

    fun navigateToRoute(route: String) {
        if (route != navController.currentDestination?.route) {
            navController.navigate(route) {
                launchSingleTop = true
                restoreState = true
                // Pop up backstack to the first destination and save state. This makes going back
                // to the start destination when pressing back in any other bottom tab.
                popUpTo(findStartDestination(navController.graph).id) {
                    saveState = true
                }
            }
        }
    }

    fun navigateToInstanceCreate(from: NavBackStackEntry) {
        // In order to discard duplicated navigation events, we check the Lifecycle
        Log.d("DEBUG", "navigateToInstanceCreate: hi in projectAppState")
//        if (from.lifecycleIsResumed()) {
        navController.navigate(MainDestination.INSTANCE_CREATE_ROUTE)
//        }
    }
    fun navigateToHome(from: NavBackStackEntry){
        navController.navigate(MainDestination.HOME_ROUTE)
    }

    fun navigateToSignUp(from: NavBackStackEntry){
        navController.navigate(MainDestination.SIGNUP_ROUTE)
    }

    fun navigateToLogin(from: NavBackStackEntry){
        navController.navigate(MainDestination.LOGIN_ROUTE)
    }
}

/**
 * Copied from similar function in NavigationUI.kt
 *
 * https://cs.android.com/androidx/platform/frameworks/support/+/androidx-main:navigation/navigation-ui/src/main/java/androidx/navigation/ui/NavigationUI.kt
 */
tailrec fun findStartDestination(graph: NavDestination): NavDestination {
    return if (graph is NavGraph) findStartDestination(graph.startDestination!!) else graph
}
private fun NavBackStackEntry.lifecycleIsResumed() =
    this.getLifecycle().currentState == Lifecycle.State.RESUMED

private val NavGraph.startDestination: NavDestination?
    get() = findNode(startDestinationId)
