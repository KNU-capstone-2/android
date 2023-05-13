package com.knu.cloud.screens.home.instance

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.knu.cloud.R
import com.knu.cloud.components.basicTable.*
import com.knu.cloud.components.summary.InstanceSummary
import com.knu.cloud.model.instance.InstanceData
import timber.log.Timber


@Composable
fun InstanceScreen (
    onInstanceCreateClicked: () -> Unit,
    onInstanceDetailClicked: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel : InstanceViewModel  = hiltViewModel()
) {
    val context = LocalContext.current // Toast 메세지를 위함
    val instances = viewModel.instances.collectAsState()
    val checkedInstanceIdList = viewModel.checkedInstanceData.collectAsState()
    var selectedInstance by rememberSaveable {
        mutableStateOf<InstanceData?>(null)
    }
    val isAllSelected = rememberSaveable { mutableStateOf(false) }
    val isHeaderClick = rememberSaveable { mutableStateOf(false) }
    val selectedItemIndex = rememberSaveable { mutableStateOf(-1) }

    Timber.tag("vm_test").d("InstanceScreen : checkedInstanceIdList ${checkedInstanceIdList.value}")
    if(checkedInstanceIdList.value.isEmpty()){
        /* to initialize table checkBoxes*/
        isAllSelected.value = false
        isHeaderClick.value = true
    }

    Column(
    ){
        InstancesBar(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            totalInstanceCnt = instances.value.size,
            checkedInstanceCnt = checkedInstanceIdList.value.size,
            onLaunchBtnClicked = onInstanceCreateClicked,
            onDeleteBtnClicked = {
                viewModel.deleteCheckedInstances()
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
                    dataList = instances.value,
                    isAllSelected = isAllSelected,
                    isHeaderClick = isHeaderClick,
                    selectedItemIndex = selectedItemIndex,
                    onAllChecked = {
                       viewModel.allInstanceCheck(it)
                    },
                    onRowChecked = { checked,  instanceId ->
                        Timber.tag("vm_test").d("onRowChecked")
                        if(checked) {
                            Timber.tag("vm_test").d("instanceCheck call")
                            viewModel.instanceCheck(instanceId)
                        }else {
                            viewModel.instanceUncheck(instanceId)
                        }
                    },
                    onRowSelected = { instanceId ->
                        val selectedData = instances.value.find { it.instancesId == instanceId }
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
                            onInstanceDetailClicked(it)
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
    isAllSelected : MutableState<Boolean>,
    isHeaderClick : MutableState<Boolean>,
    selectedItemIndex : MutableState<Int>,
    onAllChecked : (Boolean) -> Unit,
    onRowChecked : (Boolean, String) -> Unit,
    onRowSelected : (String) -> Unit
) {
    val weightList = listOf(.2f,.25f,.125f,.125f,.2f)
    BasicTable(
        tableHeaderItem = TableHeaderItem(
            textList = listOf("Instance Name", "Instance ID","Instance State", "Instance Type", "Status Check"),
            weightList = weightList
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
                weightList = weightList,
                rowID = instanceData.instancesId,
                isSelected = rememberSaveable {
                    mutableStateOf(false)
                }
            )
        },
        onAllChecked = onAllChecked,
        onRowChecked = onRowChecked,
        onRowSelected = onRowSelected,
        isAllSelected = isAllSelected,
        isHeaderClick = isHeaderClick,
        selectedItemIndex = selectedItemIndex
    )
}



@Composable
fun InstancesBar(
    modifier: Modifier = Modifier,
    totalInstanceCnt : Int,
    checkedInstanceCnt : Int,
    onLaunchBtnClicked : () -> Unit,
    onDeleteBtnClicked : () -> Unit
) {
    var isDeleteDialogOpen by remember {
        mutableStateOf(false)
    }

    if (isDeleteDialogOpen) {
        DeleteDialog(
            onDeleteBtnClicked
        ) {
            isDeleteDialogOpen = false
        }
    }

    Row(modifier = modifier
        .fillMaxSize()
    ) {
        Box(modifier = Modifier
            .weight(0.2f)
            .fillMaxSize(),
            contentAlignment = Alignment.CenterStart
        ){
            Row(verticalAlignment = Alignment.Bottom) {
                Text("Instances",
                    modifier = Modifier.padding(10.dp),
                   style = MaterialTheme.typography.h6
                )
                Text(text = "Total : $totalInstanceCnt  Selected : $checkedInstanceCnt",
                    modifier = Modifier.padding(10.dp)
                )
            }
        }
        OutlinedButton(
            modifier = Modifier.weight(0.1f),
            onClick = onLaunchBtnClicked
        ) {
            Text(text = "Launch Instance")
        }
        OutlinedButton(
            modifier = Modifier.weight(0.1f),
            onClick = { isDeleteDialogOpen = true }
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



@Composable
fun DeleteDialog(
    onDeleteBtnClicked: () -> Unit,
    isDeleteDialogOpen: () -> Unit
) {
    AlertDialog(
        onDismissRequest = {isDeleteDialogOpen()},
        title = {
            Text(text = stringResource(id = R.string.Instance_Delete_Btn_Title))
        },
        text = {
            Text(text = stringResource(id = R.string.Instance_Delete_Btn_Text))
        },
        buttons = {
            Row(
                modifier = Modifier
                    .width(350.dp)
                    .padding(bottom = 12.dp),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(
                    onClick = {
                        onDeleteBtnClicked()
                        isDeleteDialogOpen()
                    }
                ) {
                    Text("확인")
                }
                TextButton(
                    onClick = {isDeleteDialogOpen()}
                ) {
                    Text("취소")
                }
            }
        },
        shape = RoundedCornerShape(24.dp)
    )
}
