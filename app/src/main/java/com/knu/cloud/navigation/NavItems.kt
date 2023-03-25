package com.knu.cloud.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.outlined.*
import androidx.compose.ui.graphics.vector.ImageVector

object MainDestination{
    const val SPlASH_ROUTE = "splash"
    const val AUTH_ROUTE = "auth"
    const val LOGIN_ROUTE = "auth/login"
    const val SIGNUP_ROUTE = "auth/signup"
    const val HOME_ROUTE = "home"
    const val INSTANCE_CREATE_ROUTE ="instance_create"
}

enum class HomeSections(
    val title: String,
    val icon: ImageVector,
    val route: String
) {
    DashBoard("DashBoard", Icons.Outlined.Home, "home/dashboard"),
    Instance("Instance", Icons.Outlined.Menu, "home/instance"),
    Setting("Setting", Icons.Outlined.Settings, "home/setting")
}

enum class InstanceCreateSections(
    val title: String,
    val icon: ImageVector,
    val route: String
) {
    Details("Details", Icons.Default.Info, "create_instance/details"),
    Source("Source", Icons.Outlined.Star, "create_instance/source"),
    Flavor("Flavor", Icons.Outlined.Share, "create_instance/flavor"),
    Network("Network", Icons.Outlined.LocationOn, "create_instance/network")
}
