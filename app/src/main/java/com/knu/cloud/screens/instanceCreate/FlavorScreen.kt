package com.knu.cloud.screens.instanceCreate

import androidx.compose.runtime.Composable
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.knu.cloud.R

@Composable
fun FlavorScreen (
    viewModel: InstanceCreateViewModel
) {
    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = Color.White
    ) {
        InstanceState(viewModel = viewModel)
    }
}

@Composable
fun InstanceState(
    viewModel: InstanceCreateViewModel,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        Column(
            modifier = Modifier
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioNoBouncy,
                        stiffness = Spring.StiffnessLow
                    )
                )
        ) {
            Text(
                text = stringResource(R.string.IC_Flavor_description),
                style = MaterialTheme.typography.subtitle2,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(20.dp)
            )
            // 할당됨
            DataGrid(
                type = "할당됨",
                dataSet = viewModel.uploadFlavor.value,
                numbers = viewModel.uploadFlavor.value.size
            ) { it, idx ->
                viewModel.deleteFlavor(it, idx)
            }
        }
        Divider(
            color = Color.LightGray.copy(alpha = 0.3f),
            thickness = 1.dp,
            modifier = Modifier.padding(5.dp)
        )
        Column(
            modifier = Modifier
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioNoBouncy,
                        stiffness = Spring.StiffnessLow
                    )
                )
        ) {
            // 사용 가능
            DataGrid(
                type = "사용 가능",
                dataSet = viewModel.possibleFlavor.value,
                numbers = viewModel.possibleFlavor.value.size
            ) { it, idx ->
                viewModel.uploadFlavor(it, idx)
            }
        }
    }
}

@Composable
fun DataGrid(
    type: String,
    dataSet: List<Flavor>,
    numbers: Int,
    onClickButton: (Flavor, Int) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    Row(
        modifier = Modifier
            .padding(5.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        ExpandedItemButton(
            expanded = expanded,
            onClick = { expanded = !expanded },
        )
        Text(
            text = type,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(end = 10.dp)
        )
        Surface(
            modifier = Modifier
                .padding(0.dp),
            shape = CircleShape,
            color = Color.LightGray
        ) {
            Text(
                text = numbers.toString(),
                style = MaterialTheme.typography.caption,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(5.dp)
            )
        }
    }
    if (expanded) {
        DataGridList(flavorDatas = dataSet, type = type) { it, idx ->
            onClickButton(it, idx)
        }
    }
}

@Composable
private fun ExpandedItemButton(
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

@Composable
fun DataGridList(
    flavorDatas: List<Flavor>,
    modifier: Modifier = Modifier,
    type: String,
    onClickButton: (Flavor, Int) -> Unit,
) {

    // Each cell of a column must have the same weight.
    val columnNameWeight = .2f
    val columnVCPUSWeight = .2f
    val columnRAMWeight = .2f
    val columnDiskTotalWeight = .2f
    val columnRootDiskWeight = .2f
    val columnEphemeralDiskWeight = .3f
    val columnPublicWeight = .1f
    val columnUpDownWeight = .1f

    val tableHeader = "tableHeader"
    val tableList = "tableList"

    LazyColumn(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {
        item { // Header
            Row(
                modifier = Modifier
                    .padding(5.dp)
                    .fillMaxWidth(),
            ) {
                TableCell(text = stringResource(id = R.string.IC_Flavor_Header_Name), weight = columnNameWeight, type = tableHeader)
                TableCell(text = stringResource(id = R.string.IC_Flavor_Header_VCPUS), weight = columnVCPUSWeight, type = tableHeader)
                TableCell(text = stringResource(id = R.string.IC_Flavor_Header_RAM), weight = columnRAMWeight, type = tableHeader)
                TableCell(text = stringResource(id = R.string.IC_Flavor_Header_DiskTotal), weight = columnDiskTotalWeight, type = tableHeader)
                TableCell(text = stringResource(id = R.string.IC_Flavor_Header_RootDisk), weight = columnRootDiskWeight, type = tableHeader)
                TableCell(text = stringResource(id = R.string.IC_Flavor_Header_EphemeralDisk), weight = columnEphemeralDiskWeight, type = tableHeader)
                TableCell(text = stringResource(id = R.string.IC_Flavor_Header_Public), weight = columnPublicWeight, type = tableHeader)
                TableCell(text = "", weight = columnUpDownWeight, type = tableHeader)
            }
            Divider(
                color = Color.Black.copy(alpha = 0.3f),
                thickness = 1.dp,
                modifier = Modifier.padding(5.dp)
            )
        }

        // lines Data
        itemsIndexed(flavorDatas) { index, item ->
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
                TableCell(text = item.name, weight = columnNameWeight, type = tableList)
                TableCell(text = item.vcpus.toString(), weight = columnVCPUSWeight, type = tableList)
                TableCell(text = item.ram.toString() + " MB", weight = columnRAMWeight, type = tableList)
                TableCell(text = item.diskTotal.toString() + " GB", weight = columnDiskTotalWeight, type = tableList)
                TableCell(text = item.rootDisk.toString() + " GB", weight = columnRootDiskWeight, type = tableList)
                TableCell(text = item.ephemeralDisk.toString() + " GB", weight = columnEphemeralDiskWeight, type = tableList)
                TableCell(text = item.public, weight = columnPublicWeight, type = tableList)
                IconButton(
                    onClick = {
                        onClickButton(item, index)
                    },
                    modifier = Modifier.weight(columnUpDownWeight)
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
    }
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
