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
    const val SETTING_ROUTE = "home/setting"
    const val INSTANCE_DETAIL_ROUTE = "home/compute/instance/detail"
}

val homeSections : Array<Section> = ComputeSections.sectionValues().plus(NetworkSections.sectionValues())
interface Section {
    val title : String
    val icon : ImageVector
    val route : String
}
enum class SectionType {
    COMPUTE,
    NETWORK,
    INSTANCE_CREATE
}
enum class ComputeSections(
    override val title: String,
    override val icon: ImageVector,
    override val route: String
) :Section {
    DashBoard("DashBoard", Icons.Outlined.Home, "home/compute/dashboard"),
    Instance("Instance", Icons.Outlined.Menu, "home/compute/instance"),
    Image("Image", Icons.Outlined.Menu, "home/compute/image"),
    KeyPairs("KeyPairs", Icons.Outlined.Menu, "home/compute/keypairs"),
    Volume("Volume", Icons.Outlined.Menu, "home/compute/volume");

    companion object{
        fun sectionValues() : Array<Section>{
            return  arrayOf(DashBoard,Instance,Image,KeyPairs,Volume)
        }
    }
}

enum class NetworkSections(
    override val title: String,
    override val icon: ImageVector,
    override val route: String
) : Section{
    Networks("Network", Icons.Outlined.Home, "home/network/networks"),
    SecurityGroup("SecurityGroup", Icons.Outlined.Menu, "home/network/securitygroup");
    companion object{
        fun sectionValues() : Array<Section>{
            return  arrayOf(Networks,SecurityGroup)
        }
    }
}

enum class InstanceCreateSections(
    override val title: String,
    override val icon: ImageVector,
    override val route: String
) : Section{
    Details("Details", Icons.Default.Info, "instance_create/details"),
    Source("Source", Icons.Outlined.Star, "instance_create/source"),
    Flavor("Flavor", Icons.Outlined.Share, "instance_create/flavor"),
    Network("Network", Icons.Outlined.LocationOn, "instance_create/network"),
    Keypair("Keypair", Icons.Outlined.AccountBox, "instance_create/keypair");

    companion object{
        fun sectionValues() : Array<Section>{
            return  arrayOf(
                InstanceCreateSections.Details,
                InstanceCreateSections.Source,
                InstanceCreateSections.Flavor,
                InstanceCreateSections.Network,
                InstanceCreateSections.Keypair
            )
        }
    }
}
