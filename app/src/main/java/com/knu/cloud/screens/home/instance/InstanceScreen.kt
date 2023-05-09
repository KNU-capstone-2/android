package com.knu.cloud.screens.home.instance

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.knu.cloud.components.basicTable.*
import com.knu.cloud.components.summary.InstanceSummary



@Composable
fun InstanceScreen (
    onInstanceCreateClicked: () -> Unit,
    onInstanceDetailClicked: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel : InstanceViewModel  = hiltViewModel()
) {
    val context = LocalContext.current
    val testData = viewModel.testData.value
    var selectedInstance by remember {
        mutableStateOf<InstanceData?>(null)
    }
    Column(
    ){
        InstancesBar(
            onLaunchBtnClicked = onInstanceCreateClicked,
            onDeleteBtnClicked = {
                /*TODO : 선택한 인스턴스들 삭제*/
            }
        )
        Divider(modifier = Modifier.height(1.dp), color = Color.Black)
        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ){
            Column(
                modifier = Modifier.weight(.7f)
            ) {
                InstanceTable(
                    dataList = testData,
                    onRowSelected = { instanceId ->
                        val selectedData = testData.find { it.instancesId == instanceId }
                        selectedInstance = if (selectedInstance == selectedData) null else selectedData
                    }
                )
            }
            if(selectedInstance != null){
                Column(
                    modifier = Modifier
                        .weight(.3f)
                        .background(Color.White)
                        .fillMaxHeight()
                    , verticalArrangement = Arrangement.Center
                    , horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    InstanceSummary(
                        context = context,
                        instance = selectedInstance,
                        StartClicked = { /*TODO*/ },
                        ReStartClicked = { /*TODO*/ },
                        StopClicked = { /*TODO*/ },
                        onInstanceDetailClicked = {
                            /*TODO : InstanceDetailScreen으로 Navigation */
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun InstanceTable(
    dataList :List<InstanceData>,
    onRowSelected : (String) -> Unit
) {
    BasicTable(
        tableHeaderItem = TableHeaderItem(
            textList = listOf("Instance Name", "Instance ID","Instance State", "Instance Type", "Status Check"),
            weightList = listOf(.1f,.1f,.1f,.1f,.1f)
        ),
        tableRowItems = dataList.map{ instanceData ->
            TableRowItem(
                cellTypeList =listOf(
                    TableCellType.TEXT,
                    TableCellType.TEXT,
                    TableCellType.COLOR_BOX,
                    TableCellType.COLOR_BOX,
                    TableCellType.COLOR_BOX),
                textList = listOf( instanceData.instancesName,instanceData.instancesId, instanceData.instanceState,instanceData.instanceType,instanceData.statusCheck),
                colorList = listOf(Color.Black, Color.Black, Color.Green, Color.LightGray, Color.Green),
                weightList = listOf(.1f,.1f,.1f,.1f,.1f),
                rowID = instanceData.instancesId,
                isSelected = remember {
                    mutableStateOf(instanceData.isSelected)
                }
            )
        },
        onRowSelected =onRowSelected
    )
}



@Composable
fun InstancesBar(
    modifier: Modifier = Modifier,
    onLaunchBtnClicked : () -> Unit,
    onDeleteBtnClicked : () -> Unit
) {
    Row(modifier = modifier
        .fillMaxWidth()
    ) {
        Text("Instances (10/20)",modifier = Modifier.weight(0.2f))
        Spacer(modifier = modifier.weight(0.4f))
        OutlinedButton(
            modifier = Modifier.weight(0.3f),
            onClick = onLaunchBtnClicked
        ) {
            Text(text = "Launch Instance")
        }
        OutlinedButton(
            modifier = Modifier.weight(0.3f),
            onClick = onDeleteBtnClicked
        ) {
            Text(text = "Delete Instances")
        }
    }

}
@Preview(showBackground = true, device = Devices.TABLET)
@Composable
fun TestInstanceScreen() {
    InstanceScreen(
        onInstanceCreateClicked = {},
        onInstanceDetailClicked = {}
    )
}

