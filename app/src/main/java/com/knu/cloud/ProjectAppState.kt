package com.knu.cloud

import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.navigation.*
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.knu.cloud.navigation.*
import timber.log.Timber


// ProjectApp에서 사용되는 Destination들

@Composable
fun rememberProjectAppState(
    navController: NavHostController = rememberNavController(),
    scaffoldState : ScaffoldState = rememberScaffoldState(),
    navActions : NavActions = remember(navController) {
        NavActions(navController)
    },
    showMessageDialog :  MutableState<Boolean> = rememberSaveable { mutableStateOf(false) },
    enabledTopAppBar: MutableState<Boolean> = rememberSaveable { mutableStateOf(false) },
    enabledNavDrawer: MutableState<Boolean> = rememberSaveable { mutableStateOf(false) }
) = remember(navController,scaffoldState, navActions,showMessageDialog,enabledTopAppBar,enabledNavDrawer){
    ProjectAppState(navController,scaffoldState,navActions,showMessageDialog,enabledTopAppBar,enabledNavDrawer)
}

class ProjectAppState(
    val navController: NavHostController,
    val scaffoldState: ScaffoldState,
    val navActions: NavActions,
    val showLogOutDialog: MutableState<Boolean>,
    val enabledTopAppBar : MutableState<Boolean>,
    val enabledNavDrawer : MutableState<Boolean>
) {
    fun addDestinationChangedListener(
    ){
        Timber.tag("navigation").d("addDestinationChangedListener에 들어옴")
        val callback = NavController.OnDestinationChangedListener { _, destination, _ ->
            Timber.tag("navigation").d("OnDestinationChangedListener 체크하기")
            val homeSectionRoutes = destination.route in homeSections.map { it.route }
            if (homeSectionRoutes){
                if(!enabledTopAppBar.value) {
                    enabledTopAppBar.value = true
                    Timber.tag("navigation").d("HomeSectionState true로 변경")
                }
                if(!enabledNavDrawer.value) {
                    enabledNavDrawer.value = true
                    Timber.tag("navigation").d("HomeSectionState true로 변경")
                }
            }else if (destination.route?.contains("detail") == true){
                enabledNavDrawer.value = false
            }else if (destination.route?.contains("auth") == true){
                enabledTopAppBar.value = false
                enabledNavDrawer.value = false
            }
        }
        navController.addOnDestinationChangedListener(callback)
    }

    val currentRoute: String?
        @Composable get() = navController.currentBackStackEntryAsState().value?.destination?.route

    val isInstanceScreen : Boolean
        @Composable get() = navController.currentBackStackEntryAsState().value?.destination?.route == ComputeSections.Instance.route

}
