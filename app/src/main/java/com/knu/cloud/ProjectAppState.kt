package com.knu.cloud

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.lifecycle.Lifecycle
import androidx.navigation.*
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.knu.cloud.navigation.HomeSections
import com.knu.cloud.navigation.MainDestination


// ProjectApp에서 사용되는 Destination들

@Composable
fun rememberProjectAppState(
    navController: NavHostController = rememberNavController()
) = remember(navController){
    ProjectAppState(navController)
}

@Stable
class ProjectAppState(
    val navController: NavHostController) {

    //----------------------
    // BottomBar state source
    //----------------------

    val bottomBarTabs = HomeSections.values()
    private val bottomBarRoutes = bottomBarTabs.map{it.route}

    // 이 변수 읽을 때 바텀바가 보여져야하는지 아닌지 recomposition을 수행함
    val shouldShowBottomBar : Boolean
        @Composable get() = navController.currentBackStackEntryAsState().value?.destination?.route in bottomBarRoutes

    val isInstanceScreen : Boolean
        @Composable get() = navController.currentBackStackEntryAsState().value?.destination?.route == HomeSections.Instance.route

    //----------------------
    // Navigation state source
    //----------------------
    val currentRoute: String?
        get() = navController.currentDestination?.route

//    fun upPress() {
//        navController.navigateUp()
//    }

    fun navigateToBottomBarRoute(route: String) {
        if (route != currentRoute) {
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
    fun navigateToHome(from:NavBackStackEntry){
        navController.navigate(MainDestination.HOME_ROUTE)
    }
}

private fun NavBackStackEntry.lifecycleIsResumed() =
    this.getLifecycle().currentState == Lifecycle.State.RESUMED

private val NavGraph.startDestination: NavDestination?
    get() = findNode(startDestinationId)

/**
 * Copied from similar function in NavigationUI.kt
 *
 * https://cs.android.com/androidx/platform/frameworks/support/+/androidx-main:navigation/navigation-ui/src/main/java/androidx/navigation/ui/NavigationUI.kt
 */
 tailrec fun findStartDestination(graph: NavDestination): NavDestination {
    return if (graph is NavGraph) findStartDestination(graph.startDestination!!) else graph
}
