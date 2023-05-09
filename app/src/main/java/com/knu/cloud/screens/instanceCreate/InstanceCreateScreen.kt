package com.knu.cloud.screens.instanceCreate

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.knu.cloud.navigation.InstanceCreateSections
import com.knu.cloud.navigation.MainDestination
import com.knu.cloud.navigation.findStartDestination
import com.knu.cloud.navigation.instanceCreateNavGraph


@Composable
fun InstanceCreateScreen(){
    val intanceCreateNavController :NavHostController = rememberNavController()
    val navBackStackEntry by intanceCreateNavController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: InstanceCreateSections.Details.route
    Row(modifier = Modifier.fillMaxSize()) {
        ProjectNavigationRail(
            sections = InstanceCreateSections.values(),
            currentRoute = currentRoute,
            navigateToRoute = {route ->
                if (route != currentRoute) {
                    intanceCreateNavController.navigate(route) {
                        launchSingleTop = true
                        restoreState = true
                        // Pop up backstack to the first destination and save state. This makes going back
                        // to the start destination when pressing back in any other bottom tab.
                        popUpTo(findStartDestination(intanceCreateNavController.graph).id) {
                            saveState = true
                        }
                    }
                }

            }
        )
        NavHost( /* CreateInstance NavHost */
            navController = intanceCreateNavController,
            startDestination = InstanceCreateSections.Details.route
        ){
            instanceCreateNavGraph()
        }
    }
}

@Composable
fun ProjectNavigationRail(
    sections : Array<InstanceCreateSections>,
    currentRoute: String,
    navigateToRoute: (String) -> Unit
) {
    NavigationRail {
        val routes = remember { sections.map{it.route} }
        val currentSection = sections.first{ it.route == currentRoute}

        sections.forEach { section ->
            val selected = section == currentSection
            NavigationRailItem(
                icon = { Icon(section.icon, contentDescription = null) } ,
                label = { Text(section.title) },
                selected = selected,
                onClick = { navigateToRoute(section.route) }
            )
        }
    }
}