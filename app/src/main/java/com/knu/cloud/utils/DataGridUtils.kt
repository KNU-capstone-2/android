package com.knu.cloud.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.knu.cloud.R
import com.knu.cloud.model.instanceCreate.FlavorResponse
import com.knu.cloud.model.instanceCreate.KeypairResponse
import com.knu.cloud.model.instanceCreate.NetworkResponse
import com.knu.cloud.model.instanceCreate.ImageData

object FlavorUtils{
    const val columnNameWeight = .2f
    const val columnVCPUSWeight = .2f
    const val columnRAMWeight = .2f
    const val columnDiskTotalWeight = .2f
    const val columnRootDiskWeight = .2f
    const val columnEphemeralDiskWeight = .3f
    const val columnPublicWeight = .1f
    const val columnUpDownWeight = .1f

    const val tableHeader = "tableHeader"
    const val tableList = "tableList"
}

object SourceUtils{
    const val columnNameWeight = .3f
    const val columnUpdateWeight = .2f
    const val columnSizeWeight = .2f
    const val columnFormatWeight = .2f
    const val columnVisibleWeight = .1f
    const val columnUpDownWeight = .1f

    const val tableHeader = "tableHeader"
    const val tableList = "tableList"
}

object NetworkUtils{
    const val columnNetworkWeight = .2f
    const val columnSubNetWeight = .3f
    const val columnShareWeight = .2f
    const val columnAdminStateWeight = .2f
    const val columnStateWeight = .1f
    const val columnUpDownWeight = .1f

    const val tableHeader = "tableHeader"
    const val tableList = "tableList"
}

object KeypairUtils{
    const val columnNameWeight = .3f
    const val columnTypeWeight = .6f
    const val columnUpDownWeight = .1f

    const val tableHeader = "tableHeader"
    const val tableList = "tableList"
}

@Composable
fun FlavorHeaderTableCell() {
    Row(
        modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth(),
    ) {
        TableCell(text = stringResource(id = R.string.IC_Flavor_Header_Name), weight = FlavorUtils.columnNameWeight, type = FlavorUtils.tableHeader)
        TableCell(text = stringResource(id = R.string.IC_Flavor_Header_VCPUS), weight = FlavorUtils.columnVCPUSWeight, type = FlavorUtils.tableHeader)
        TableCell(text = stringResource(id = R.string.IC_Flavor_Header_RAM), weight = FlavorUtils.columnRAMWeight, type = FlavorUtils.tableHeader)
        TableCell(text = stringResource(id = R.string.IC_Flavor_Header_DiskTotal), weight = FlavorUtils.columnDiskTotalWeight, type = FlavorUtils.tableHeader)
        TableCell(text = stringResource(id = R.string.IC_Flavor_Header_RootDisk), weight = FlavorUtils.columnRootDiskWeight, type = FlavorUtils.tableHeader)
        TableCell(text = stringResource(id = R.string.IC_Flavor_Header_EphemeralDisk), weight = FlavorUtils.columnEphemeralDiskWeight, type = FlavorUtils.tableHeader)
        TableCell(text = stringResource(id = R.string.IC_Flavor_Header_Public), weight = FlavorUtils.columnPublicWeight, type = FlavorUtils.tableHeader)
        TableCell(text = "", weight = FlavorUtils.columnUpDownWeight, type = FlavorUtils.tableHeader)
    }
    Divider(
        color = Color.Black.copy(alpha = 0.3f),
        thickness = 1.dp,
        modifier = Modifier.padding(5.dp)
    )
}

@Composable
fun FlavorDataTableCell(item: FlavorResponse, index: Int, type: String, onClickButton: (FlavorResponse, Int) -> Unit) {
    var backGcolor = Color.White
    if (index % 2 == 0) {
        backGcolor = Color.LightGray
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(backGcolor),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        TableCell(text = item.name, weight = FlavorUtils.columnNameWeight, type = FlavorUtils.tableList)
        TableCell(text = item.vcpus.toString(), weight = FlavorUtils.columnVCPUSWeight, type = FlavorUtils.tableList)
        TableCell(text = item.ram.toString() + " MB", weight = FlavorUtils.columnRAMWeight, type = FlavorUtils.tableList)
        TableCell(text = item.diskTotal.toString() + " GB", weight = FlavorUtils.columnDiskTotalWeight, type = FlavorUtils.tableList)
        TableCell(text = item.rootDisk.toString() + " GB", weight = FlavorUtils.columnRootDiskWeight, type = FlavorUtils.tableList)
        TableCell(text = item.ephemeralDisk.toString() + " GB", weight = FlavorUtils.columnEphemeralDiskWeight, type = FlavorUtils.tableList)
        TableCell(text = item.public, weight = FlavorUtils.columnPublicWeight, type = FlavorUtils.tableList)
        IconButton(
            onClick = {
                onClickButton(item, index)
            },
            modifier = Modifier.weight(FlavorUtils.columnUpDownWeight)
        ) {
            Icon(imageVector = if(type == "할당됨") Icons.Default.KeyboardArrowDown else Icons.Default.KeyboardArrowUp,
                contentDescription = "Updown Icon")
        }
    }
    Divider(
        color = Color.LightGray.copy(alpha = 0.3f),
        thickness = 1.dp,
        modifier = Modifier.padding(5.dp)
    )
}

@Composable
fun SourceHeaderTableCell() {
    Row(
        modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth(),
    ) {
        TableCell(text = stringResource(id = R.string.IC_Source_Header_Name), weight = SourceUtils.columnNameWeight, type = SourceUtils.tableHeader)
        TableCell(text = stringResource(id = R.string.IC_Source_Header_Update), weight = SourceUtils.columnUpdateWeight, type = SourceUtils.tableHeader)
        TableCell(text = stringResource(id = R.string.IC_Source_Header_Size), weight = SourceUtils.columnSizeWeight, type = SourceUtils.tableHeader)
        TableCell(text = stringResource(id = R.string.IC_Source_Header_Format), weight = SourceUtils.columnFormatWeight, type = SourceUtils.tableHeader)
        TableCell(text = stringResource(id = R.string.IC_Source_Header_Visible), weight = SourceUtils.columnVisibleWeight, type = SourceUtils.tableHeader)
        TableCell(text = "", weight = SourceUtils.columnUpDownWeight, type = SourceUtils.tableHeader)
    }
    Divider(
        color = Color.Black.copy(alpha = 0.3f),
        thickness = 1.dp,
        modifier = Modifier.padding(5.dp)
    )
}

@Composable
fun SourceDataTableCell(item: ImageData, index: Int, type: String, onClickButton: (ImageData, Int) -> Unit) {
    var backGcolor = Color.White
    if (index % 2 == 0) {
        backGcolor = Color.LightGray
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(backGcolor),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        TableCell(text = item.name, weight = SourceUtils.columnNameWeight, type = SourceUtils.tableList)
        TableCell(text = item.updateDate, weight = SourceUtils.columnUpdateWeight, type = SourceUtils.tableList)
        TableCell(text = item.size.toString() + " MB", weight = SourceUtils.columnSizeWeight, type = SourceUtils.tableList)
        TableCell(text = item.createdDate, weight = SourceUtils.columnSizeWeight, type = SourceUtils.tableList)
        TableCell(text = item.status, weight = SourceUtils.columnVisibleWeight, type = SourceUtils.tableList)
        IconButton(
            onClick = {
                onClickButton(item, index)
            },
            modifier = Modifier.weight(SourceUtils.columnUpDownWeight)
        ) {
            Icon(imageVector = if(type == "할당됨") Icons.Default.KeyboardArrowDown else Icons.Default.KeyboardArrowUp,
                contentDescription = "Updown Icon")
        }
    }
    Divider(
        color = Color.LightGray.copy(alpha = 0.3f),
        thickness = 1.dp,
        modifier = Modifier.padding(5.dp)
    )
}

@Composable
fun NetworkHeaderTableCell() {
    Row(
        modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth(),
    ) {
        TableCell(text = stringResource(id = R.string.IC_Network_Header_Name), weight = NetworkUtils.columnNetworkWeight, type = NetworkUtils.tableHeader)
        TableCell(text = stringResource(id = R.string.IC_Network_Header_AdminState), weight = NetworkUtils.columnSubNetWeight, type = NetworkUtils.tableHeader)
        TableCell(text = stringResource(id = R.string.IC_Network_Header_Share), weight = NetworkUtils.columnShareWeight, type = NetworkUtils.tableHeader)
        TableCell(text = stringResource(id = R.string.IC_Network_Header_AdminState), weight = NetworkUtils.columnAdminStateWeight, type = NetworkUtils.tableHeader)
        TableCell(text = stringResource(id = R.string.IC_Network_Header_State), weight = NetworkUtils.columnStateWeight, type = NetworkUtils.tableHeader)
        TableCell(text = "", weight = NetworkUtils.columnUpDownWeight, type = NetworkUtils.tableHeader)
    }
    Divider(
        color = Color.Black.copy(alpha = 0.3f),
        thickness = 1.dp,
        modifier = Modifier.padding(5.dp)
    )
}

@Composable
fun NetworkDataTableCell(item: NetworkResponse, index: Int, type: String, onClickButton: (NetworkResponse, Int) -> Unit) {
    var backGcolor = Color.White
    if (index % 2 == 0) {
        backGcolor = Color.LightGray
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(backGcolor),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        TableCell(text = item.network, weight = NetworkUtils.columnNetworkWeight, type = NetworkUtils.tableList)
        TableCell(text = item.subNet, weight = NetworkUtils.columnSubNetWeight, type = NetworkUtils.tableList)
        TableCell(text = item.share, weight = NetworkUtils.columnShareWeight, type = NetworkUtils.tableList)
        TableCell(text = item.adminState, weight = NetworkUtils.columnAdminStateWeight, type = NetworkUtils.tableList)
        TableCell(text = item.state, weight = NetworkUtils.columnStateWeight, type = NetworkUtils.tableList)
        IconButton(
            onClick = {
                onClickButton(item, index)
            },
            modifier = Modifier.weight(NetworkUtils.columnUpDownWeight)
        ) {
            Icon(imageVector = if(type == "할당됨") Icons.Default.KeyboardArrowDown else Icons.Default.KeyboardArrowUp,
                contentDescription = "Updown Icon")
        }
    }
    Divider(
        color = Color.LightGray.copy(alpha = 0.3f),
        thickness = 1.dp,
        modifier = Modifier.padding(5.dp)
    )
}

@Composable
fun KeypairHeaderTableCell() {
    Row(
        modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth(),
    ) {
        TableCell(text = "이름", weight = KeypairUtils.columnNameWeight, type = KeypairUtils.tableHeader)
        TableCell(text = "유형", weight = KeypairUtils.columnTypeWeight, type = KeypairUtils.tableHeader)
        TableCell(text = "", weight = KeypairUtils.columnUpDownWeight, type = KeypairUtils.tableHeader)
    }
    Divider(
        color = Color.Black.copy(alpha = 0.3f),
        thickness = 1.dp,
        modifier = Modifier.padding(5.dp)
    )
}

@Composable
fun KeypairDataTableCell(item: KeypairResponse, index: Int, type: String, onClickButton: (KeypairResponse, Int) -> Unit) {
    var backGcolor = Color.White
    if (index % 2 == 0) {
        backGcolor = Color.LightGray
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(backGcolor),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        TableCell(text = item.name, weight = NetworkUtils.columnNetworkWeight, type = NetworkUtils.tableList)
        TableCell(text = item.type, weight = NetworkUtils.columnSubNetWeight, type = NetworkUtils.tableList)
        IconButton(
            onClick = {
                onClickButton(item, index)
            },
            modifier = Modifier.weight(NetworkUtils.columnUpDownWeight)
        ) {
            Icon(imageVector = if(type == "할당됨") Icons.Default.KeyboardArrowDown else Icons.Default.KeyboardArrowUp,
                contentDescription = "Updown Icon")
        }
    }
    Divider(
        color = Color.LightGray.copy(alpha = 0.3f),
        thickness = 1.dp,
        modifier = Modifier.padding(5.dp)
    )
}


@Composable
fun RowScope.TableCell(
    text: String,
    weight: Float,
    type: String
) {
    Text(
        text = text,
        color = Color.Black.copy(alpha= 0.7f),
        fontWeight = if (type=="tableHeader") FontWeight.SemiBold else FontWeight.Normal,
        modifier = Modifier
            .padding(5.dp)
            .weight(weight),
    )
}

@Composable
fun ExpandedItemButton(
    expanded: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    IconButton(onClick = onClick) {
        Icon(
            imageVector = if (expanded) Icons.Default.KeyboardArrowDown else Icons.Default.KeyboardArrowRight,
            tint = MaterialTheme.colors.secondary,
            contentDescription = "button",
        )
    }
}