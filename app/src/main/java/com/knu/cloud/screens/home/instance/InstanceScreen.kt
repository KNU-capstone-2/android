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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.knu.cloud.R
import com.knu.cloud.components.CenterCircularProgressIndicator
import com.knu.cloud.components.CenterLottieLoadingIndicator
import com.knu.cloud.components.basicTable.*
import com.knu.cloud.components.summary.InstanceSummary
import com.knu.cloud.model.home.instance.InstanceData
import timber.log.Timber


@Composable
fun InstanceScreen (
    onInstanceCreateClicked: () -> Unit,
    onInstanceDetailClicked: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel : InstanceViewModel  = hiltViewModel()
) {
    val context = LocalContext.current // Toast 메세지를 위함
    val uiState by viewModel.uiState.collectAsState()
    var selectedInstance by rememberSaveable {
        mutableStateOf<InstanceData?>(null)
    }
    var isDeleteConfirmDialogOpen by remember { mutableStateOf(false) }

    if (isDeleteConfirmDialogOpen) {
        DeleteConfirmDialog(
            onDeleteBtnClicked = {
                viewModel.deleteCheckedInstances()
            },
            onCloseBtnClicked =  {
                isDeleteConfirmDialogOpen = false
            }
        )
    }

    if (uiState.deleteComplete){
        DeleteResultDialog(
            deleteResult = uiState.deleteResult,
            onCloseBtnClicked = {
                viewModel.closeDeleteResultDialog()
            }
        )
    }
    if (uiState.isLoading) {
        CenterLottieLoadingIndicator()
    } else {
        Column() {
            InstancesBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                totalInstanceCnt = uiState.instances.size,
                checkedInstanceCnt = uiState.checkedInstanceIds.size,
                onLaunchBtnClicked = onInstanceCreateClicked,
                onDeleteBtnClicked = {
                    isDeleteConfirmDialogOpen = true
                }
            )
            Divider(modifier = Modifier.height(1.dp), color = Color.Black)
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
            ) {
                Column(
                    modifier = Modifier.weight(.7f)
                ) {
                    InstanceTable(
                        modifier = Modifier,
                        dataList = uiState.instances,
                        checkedInstanceIds = uiState.checkedInstanceIds,
                        onAllChecked = {
                            viewModel.allInstanceCheck(it)
                        },
                        onRowChecked = { checked, instanceId ->
                            Timber.tag("vm_test").d("onRowChecked")
                            if (checked) {
                                Timber.tag("vm_test").d("instanceCheck call")
                                viewModel.instanceCheck(instanceId)
                            } else {
                                viewModel.instanceUncheck(instanceId)
                            }
                        },
                        onRowSelected = { instanceId ->
                            val selectedData =
                                uiState.instances.find { it.instancesId == instanceId }
                            selectedInstance =
                                if (selectedInstance == selectedData) null else selectedData
                        }
                    )
                }
                if (selectedInstance != null) {
                    Column(
                        modifier = Modifier
                            .weight(.3f)
                            .background(Color.White)
                            .fillMaxHeight(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
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
}

@Composable
fun InstanceTable(
    modifier: Modifier = Modifier,
    dataList :List<InstanceData>,
    checkedInstanceIds :List<String>,
    onAllChecked : (Boolean) -> Unit,
    onRowChecked : (Boolean, String) -> Unit,
    onRowSelected : (String) -> Unit
) {
    var columnTypes by remember{ mutableStateOf(
        listOf(TableColumnType.Text,TableColumnType.Text,TableColumnType.ColorBox,TableColumnType.ColorBox,TableColumnType.ColorBox)
    )}
    var columnWeights by rememberSaveable{ mutableStateOf(
        listOf(.2f,.25f,.125f,.125f,.2f)
    )}
    var isAllSelected by rememberSaveable { mutableStateOf(false) }
    var isHeaderChecked by rememberSaveable { mutableStateOf(false) }


    LaunchedEffect(checkedInstanceIds) {
        Timber.tag("uiState")
            .d("${this.javaClass.name} : uiState.checkedInstanceIds $checkedInstanceIds")
        if (checkedInstanceIds.isEmpty()) {
            /* to initialize table checkBoxes*/
            isAllSelected = false
            isHeaderChecked = true
        }
    }

    // 지금 상태가 변할 때마다 다르게 저장됨 -> mutableStateList가 아닌 mutableState 안에 list형태로 관리하는게 맞다
//    val rowItems = remember { mutableStateListOf<TableRowItem>() }
    var rowItems by remember { mutableStateOf(emptyList<TableRowItem>())}
    rowItems = dataList.map { instanceData ->
        val instanceNameCell by mutableStateOf(TableCell(instanceData.instancesName))
        val instanceIdCell by mutableStateOf(TableCell(instanceData.instancesId))
        val instanceStateCell by mutableStateOf(
            TableCell(instanceData.instanceState, colorResource(id = R.color.instance_state_running))
        )
        val instanceTypeCell by mutableStateOf(TableCell(instanceData.instanceType))
        val statusCheckCell by mutableStateOf(
            TableCell(instanceData.statusCheck,colorResource(id = R.color.instance_state_running))
        )
        val cellItems = remember { mutableListOf(
            instanceNameCell,instanceIdCell,instanceStateCell,instanceTypeCell,statusCheckCell
        ) }
        TableRowItem(
                rowID = instanceData.instancesId,
                columnTypes = columnTypes,
                isChecked = instanceData.instancesId in checkedInstanceIds,
                isSelected = false,
                cells = cellItems.toList()
        )
    }
    BasicTable(
        modifier = modifier,
        tableHeaderItem = TableHeaderItem(
            textList = listOf("Instance Name", "Instance ID","Instance State", "Instance Type", "Status Check"),
            weightList = columnWeights
        ),
        tableRowItems = rowItems,
        columnTypes = columnTypes,
        columnWeights = columnWeights,
        onAllChecked = {
            isAllSelected = true
            isHeaderChecked = true
            onAllChecked(it)
        },
        onRowChecked = { checked , rowId ->
            val rowIdx = rowItems.indexOfFirst { it.rowID == rowId }
            val updateRowItems = rowItems.toMutableList()
            updateRowItems[rowIdx] = updateRowItems[rowIdx].copy(isChecked = checked)
            rowItems = updateRowItems
            if (isAllSelected){
                isAllSelected = false
            }
            isHeaderChecked = false
            onRowChecked(checked,rowId)
        },
        onRowSelected = onRowSelected,
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
            onClick =  onDeleteBtnClicked
        ) {
            Text(text = "Delete Instances")
        }
    }

}

@Composable
fun DeleteConfirmDialog(
    onDeleteBtnClicked: () -> Unit,
    onCloseBtnClicked: () -> Unit
) {
    AlertDialog(
        onDismissRequest = {onCloseBtnClicked()},
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
                        onCloseBtnClicked()
                    }
                ) {
                    Text("확인")
                }
                TextButton(
                    onClick = { onCloseBtnClicked() }
                ) {
                    Text("취소")
                }
            }
        },
        shape = RoundedCornerShape(24.dp)
    )
}


@Composable
fun DeleteResultDialog(
    deleteResult : List<Pair<String,Boolean>>,
    onCloseBtnClicked: () -> Unit
) {
    AlertDialog(
        onDismissRequest = {onCloseBtnClicked()},
        title = {
            Text(text = stringResource(id = R.string.Instance_Delete_Result_Title))
        },
        text = {
            deleteResult.forEach {
                if(it.second){
                    Text(text = "인스턴스 ${it.first} 삭제 결과 : 성공)")
                }else{
                    Text(text = "인스턴스 ${it.first} 삭제 결과 : 실패")
                }
            }
        },
        buttons = {
            Row(
                modifier = Modifier
                    .width(350.dp)
                    .verticalScroll(rememberScrollState())
                    .padding(bottom = 12.dp),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(
                    onClick = {
                        onCloseBtnClicked()
                    }
                ) {
                    Text("확인")
                }
            }
        },
        shape = RoundedCornerShape(24.dp)
    )
}

@Preview(showBackground = true, device = Devices.TABLET)
@Composable
fun InstanceScreenPrev() {
    InstanceScreen(
        onInstanceCreateClicked = {},
        onInstanceDetailClicked = {}
    )
}

