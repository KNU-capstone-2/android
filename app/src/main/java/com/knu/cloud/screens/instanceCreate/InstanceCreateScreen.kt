package com.knu.cloud.screens.instanceCreate

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.knu.cloud.components.CreateLoadingDialog
import com.knu.cloud.components.LaunchButton
import com.knu.cloud.navigation.InstanceCreateSections
import com.knu.cloud.navigation.findStartDestination
import com.knu.cloud.navigation.instanceCreateNavGraph
import timber.log.Timber


@Composable
fun InstanceCreateScreen(
    instanceCreateViewModel : InstanceCreateViewModel = hiltViewModel()
){
    val context = LocalContext.current

//    val instanceCreateViewModel = remember {
//        InstanceCreateViewModel()
//    }
    val instanceCreateNavController :NavHostController = rememberNavController()
    val navBackStackEntry by instanceCreateNavController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: InstanceCreateSections.Details.route

    val isDialogOpen = instanceCreateViewModel.isDialogOpen.value
//    val openResourceDialog by instanceCreateViewModel.openResourceDialog.collectAsStateWithLifecycle()

//    LaunchedEffect(key1 = openResourceDialog) {
//        instanceCreateViewModel.updateOpenResourceDialog(openResourceDialog)
//    }

//    if (openResourceDialog.showProgressDialog) {
//        Timber.tag("openResource").e("${openResourceDialog.showProgressDialog}")
//        instanceCreateViewModel.startCoroutine(context)
//        CreateLoadingDialog()
//    }
    Timber.tag("dialog").d("isDialogOpen : ${isDialogOpen}")
    if(isDialogOpen){
        CreateLoadingDialog()
        instanceCreateViewModel.startCoroutine(context)
    }

    Row(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier.weight(0.1f)
        ) {
            ProjectNavigationRail(
                sections = InstanceCreateSections.values(),
                currentRoute = currentRoute,
                navigateToRoute = {route ->
                    if (route != currentRoute) {
                        instanceCreateNavController.navigate(route) {
                            launchSingleTop = true
                            restoreState = true
                            // Pop up backstack to the first destination and save state. This makes going back
                            // to the start destination when pressing back in any other bottom tab.
                            popUpTo(findStartDestination(instanceCreateNavController.graph).id) {
                                saveState = true
                            }
                        }
                    }
                }
            )
        }
        Column(
            modifier = Modifier.weight(0.9f)
        ) {
            Column(
                modifier = Modifier.weight(0.9f)
            ) {
                NavHost( /* CreateInstance NavHost */
                    navController = instanceCreateNavController,
                    startDestination = InstanceCreateSections.Details.route
                ){
                    instanceCreateNavGraph(instanceCreateViewModel)
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.1f),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.Bottom
            ) {
                LaunchButton {
                    instanceCreateViewModel.openDialog()
                }
            }
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
