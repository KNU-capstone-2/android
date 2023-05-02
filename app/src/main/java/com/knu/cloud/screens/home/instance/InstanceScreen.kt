package com.knu.cloud.screens.home.instance

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.knu.cloud.components.basicTable.*

@Composable
fun InstanceScreen (
    onInstanceCreateClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ){
        Column(
            modifier = Modifier.weight(.7f)
        ) {
            InstancesBar()
            Divider(modifier = Modifier.height(1.dp), color = Color.Black)
            InstancesTable()
        }
        Column(
            modifier = Modifier
                .weight(.3f)
                .background(Color.White)
                .fillMaxHeight()
        , verticalArrangement = Arrangement.Center
        , horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Instance Summary")
        }
    }
}

@Composable
fun InstancesTable(
    modifier: Modifier = Modifier,
) {
    Column() {
        TableHeader(
            textList = listOf("Instance Name", "Instance ID","Instance State", "Instance Type", "Status Check"),
            weightList = listOf(.1f,.1f,.1f,.1f,.1f),
            checked = false,
            onCheckedChange = {}
        )
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ){
            itemsIndexed(listOf("d","d")){index, item ->
                TableRow(
                    cellTypeList = listOf(TableCellType.TEXT,TableCellType.TEXT,TableCellType.COLOR_BOX,TableCellType.COLOR_BOX,TableCellType.COLOR_BOX),
                    textList = listOf("ec2-test","i-of204053ab80b5cc8","Running","t2.micro","2/2 check passe"),
                    colorList = listOf(Color.Black,Color.Black,Color.Green,Color.LightGray,Color.Green),
                    weightList = listOf(.1f,.1f,.1f,.1f,.1f),
                    tableRowChecked = false,
                    onTableRowChecked = {}
                )
            }

        }
    }

}

@Composable
fun TableRow(
    modifier: Modifier =Modifier,
    cellTypeList: List<String>,
    textList : List<String>,
    colorList : List<Color>,
    weightList : List<Float>,
    tableRowChecked :Boolean ,
    onTableRowChecked : ((Boolean) -> Unit)?
) {
    Row(
        modifier = Modifier.height(50.dp)
    ) {
        TableCellLayout(modifier,weight = 0.05f, type = TableCellType.CHECK_BOX) {
            CheckBoxCell(
                checked = tableRowChecked,
                onCheckedChange = onTableRowChecked
            )
        }

        repeat(cellTypeList.size){idx->
            TableCellLayout(modifier,weight = weightList[idx], type = cellTypeList[idx]) {
                when(cellTypeList[idx]){
                    TableCellType.TEXT -> TextCell(text = textList[idx])
                    TableCellType.COLOR_BOX -> ColorBoxCell(text = textList[idx], color = colorList[idx])
                }
            }
        }
    }
}


@Composable
fun InstancesBar(
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier
        .fillMaxWidth()
    ) {
        Text("Instances (10/20)",modifier = Modifier.weight(0.2f))
        Spacer(modifier = modifier.weight(0.4f))
        OutlinedButton(
            modifier = Modifier.weight(0.3f),
            onClick = { /*TODO*/ }

        ) {
            Text(text = "Launch Instance")
        }
        OutlinedButton(
            modifier = Modifier.weight(0.3f),
            onClick = { /*TODO*/ }
        ) {
            Text(text = "Delete Instances")
        }
    }

}
@Preview(showBackground = true, device = Devices.TABLET)
@Composable
fun TestInstanceScreen() {
    InstanceScreen(
        onInstanceCreateClick = {}
    )
}


@Preview(showBackground = true, device = Devices.TABLET)
@Composable
fun TestInstanceTable() {
    InstancesTable()
}


