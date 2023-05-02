package com.knu.cloud

import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.navigation.*
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.knu.cloud.navigation.*
import kotlinx.coroutines.CoroutineScope
import timber.log.Timber


// ProjectApp에서 사용되는 Destination들

@Composable
fun rememberProjectAppState(
    navController: NavHostController = rememberNavController(),
    scaffoldState : ScaffoldState = rememberScaffoldState(),
    navActions : NavActions = remember(navController) {
        NavActions(navController)
    }
) = remember(navController,scaffoldState, navActions){
    ProjectAppState(navController,scaffoldState,navActions)
}

class ProjectAppState(
    val navController: NavHostController,
    val scaffoldState: ScaffoldState,
    val navActions: NavActions,
    val isHomeSection : MutableState<Boolean> = mutableStateOf(false)
) {
    fun addDestinationChangedListener(
    ){
        Timber.tag("navigation").d("addDestinationChangedListener에 들어옴")
        val callback = NavController.OnDestinationChangedListener { _, destination, _ ->
            Timber.tag("navigation").d("OnDestinationChangedListener 체크하기")
            if(!isHomeSection.value && destination.route in homeSections.map { it.route }) {
                isHomeSection.value = true
                Timber.tag("navigation").d("HomeSectionState true로 변경")
            }
        }
        navController.addOnDestinationChangedListener(callback)
    }

    val currentRoute: String?
        @Composable get() = navController.currentBackStackEntryAsState().value?.destination?.route

    val isInstanceScreen : Boolean
        @Composable get() = navController.currentBackStackEntryAsState().value?.destination?.route == ComputeSections.Instance.route

}
