package com.knu.cloud.screens.home.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.knu.cloud.navigation.HomeSections

@Composable
fun ProjectDashBoardScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .wrapContentSize(Alignment.Center)
    ) {
        Text(text = "HomeScreen")
    }
}
@Composable
fun ProjectBottomBar(
    sections: Array<HomeSections>,
//    onTabSelected : (HomeNavItems) -> Unit,
    currentRoute: String,
    navigateToRoute: (String) -> Unit
){
    BottomNavigation() {
        val routes = remember { sections.map{it.route} }
        val currentSection = sections.first{ it.route == currentRoute}

        sections.forEach { section ->
            val selected = section == currentSection
            BottomNavigationItem(
                icon = { Icon(section.icon, contentDescription = null) } ,
                label = { Text(section.title) },
                selected = selected,
                onClick = { navigateToRoute(section.route) }
            )
        }
    }

}

@Preview (showBackground = true)
@Composable
fun HomeScreenPrev() {
//    ProjectHomeScreen()

}