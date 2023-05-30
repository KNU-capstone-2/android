package com.knu.cloud.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.knu.cloud.R
import com.knu.cloud.ui.theme.TableCheckBox

@Composable
fun LoginLogo() {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.BasicComponents_title),
            modifier = Modifier.padding(bottom = 16.dp),
            fontFamily = FontFamily(Font(R.font.nanumgothicextrabold)),
            style = MaterialTheme.typography.h3,
            color = colorResource(id = R.color.Black_Main)
        )
        Text(
            text = stringResource(R.string.BasicComponents_subTitle),
            modifier = Modifier.padding(bottom = 16.dp),
            fontFamily = FontFamily(Font(R.font.nanumgothicbold)),
            style = MaterialTheme.typography.caption,
            color = Color.Gray
        )
    }
}

@Composable
fun FABContent(onTap: () -> Unit) {
    FloatingActionButton(
        onClick = { onTap() },
        shape = RoundedCornerShape(50.dp),
        backgroundColor  = TableCheckBox
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "Add a Book",
            tint = Color.White
        )
    }
}

@Composable
fun MessageDialog(
    title : String,
    message : String,
    onConfirmClicked : () -> Unit,
    onCancelClicked : () -> Unit
){
    AlertDialog(
        onDismissRequest = onCancelClicked,
        title = {
            Text(text = title)
        },
        text = {
            Text(text = message)
        },
        buttons = {
            Row(
                modifier = Modifier
                    .width(350.dp)
                    .padding(bottom = 12.dp),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(onClick = onCancelClicked) {
                    Text("취소")
                }
                TextButton(onClick = onConfirmClicked) {
                    Text("확인")
                }
            }
        },
        shape = RoundedCornerShape(24.dp)
    )
}

@Composable
fun CenterCircularProgressIndicator() {
    Column(
        modifier = Modifier.fillMaxSize().padding(4.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun CenterLottieLoadingIndicator(){
    Column(
        modifier = Modifier.fillMaxSize().padding(4.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LottieImage(
            modifier = Modifier
                .size(150.dp),
            rawAnimation = R.raw.loading
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BottomNavigationBarPreview() {
//    BottomNavigationBar()
}


//@Composable
//fun BottomNavigationBar(navController: NavController) {
//    val items = listOf(
//        ComputeSections.DashBoard,
//        ComputeSections.Instance,
////        ComputeSections.Setting
//    )
//    BottomNavigation(
//        backgroundColor = Color.LightGray,
//        contentColor = Color.Black
//    ) {
//        val navBackStackEntry by navController.currentBackStackEntryAsState()
//        val currentRoute = navBackStackEntry?.destination?.route
//        items.forEach { item ->
//            BottomNavigationItem(
//                icon = { Icon(item.icon, contentDescription = item.title) },
//                label = { Text(text = item.title) },
//                selectedContentColor = Color.Black,
//                unselectedContentColor = Color.Black.copy(0.4f),
//                alwaysShowLabel = true,
//                selected = currentRoute == item.route,
//                onClick = {
//                    navController.navigate(item.route){
//                        /*
//                        Pop up to the start destination of the graph to
//                         avoid building up a large stack of destinations
//                         on the back stack as users select items
//                         */
//                        navController.graph.startDestinationRoute?.let{route ->
//                            popUpTo(route){
//                                saveState = true
//                            }
//                        }
//                        /*
//                        Avoid multiple copies of the same destination when reselecting the same item
//                        */
//                        launchSingleTop = true
//                        /*
//                        Restore state when reselecting a previously selected item
//                        */
//                        restoreState = true
//                    }
//                }
//            )
//        }
//
//    }
//}

//@Composable
//fun ProjectBottomBar(
//    sections: Array<ComputeSections>,
////    onTabSelected : (HomeNavItems) -> Unit,
//    currentRoute: String,
//    navigateToRoute: (String) -> Unit
//){
//    BottomNavigation() {
////        val routes = remember { sections.map{it.route} }
//        val currentSection = sections.first{ it.route == currentRoute}
//
//        sections.forEach { section ->
//            val selected = section == currentSection
//            BottomNavigationItem(
//                icon = { Icon(section.icon, contentDescription = null) } ,
//                label = { Text(section.title) },
//                selected = selected,
//                onClick = { navigateToRoute(section.route) }
//            )
//        }
//    }
//}
