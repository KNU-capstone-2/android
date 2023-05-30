package com.knu.cloud.screens.home.instance

import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.knu.cloud.R
import com.knu.cloud.components.CenterLottieLoadingIndicator
import com.knu.cloud.components.CustomOutlinedButton
import com.knu.cloud.components.DeleteConfirmDialog
import com.knu.cloud.components.DeleteResultDialog
import com.knu.cloud.components.basicTable.*
import com.knu.cloud.components.summary.InstanceSummary
import com.knu.cloud.model.home.instance.InstanceData
import com.knu.cloud.utils.convertDateFormat
import com.knu.cloud.utils.convertStatusColor
import timber.log.Timber

val INSTANCE_COLUMN_HEADERS = listOf(
    "Creator","Instance Name", "Instance ID","Instance State", "Instance Type", "Created Date"
)
val INSTANCE_COLUMN_TYPES  = listOf(
    TableColumnType.Text,
    TableColumnType.Text,
    TableColumnType.Text,
    TableColumnType.ColorBox,
    TableColumnType.ColorBox,
    TableColumnType.ColorBox
)
val INSTANCE_COLUMN_WEIGHTS  = listOf(.3f,.4f,.65f,.3f,.3f,.4f)

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

    LaunchedEffect(uiState.message){
        if(uiState.message.isNotEmpty()){
            Toast.makeText(context, uiState.message, Toast.LENGTH_SHORT).show()
        }
    }

    if (isDeleteConfirmDialogOpen) {
        DeleteConfirmDialog(
            data = "인스턴스",
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
            data = "인스턴스",
            deleteResult = uiState.deleteResult,
            onCloseBtnClicked = {
                viewModel.closeDeleteResultDialog()
                viewModel.getAllInstances()
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
                totalCnt = uiState.instances.size,
                checkedCnt = uiState.checkedIds.size,
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
                        checkedids = uiState.checkedIds,
                        onAllChecked = {
                            viewModel.allInstanceCheck(it)
                        },
                        onRowChecked = { checked, id ->
                            Timber.tag("vm_test").d("onRowChecked")
                            if (checked) {
                                Timber.tag("vm_test").d("instanceCheck call")
                                viewModel.instanceCheck(id)
                            } else {
                                viewModel.instanceUncheck(id)
                            }
                        },
                        onRowSelected = { id ->
                            val selectedData =
                                uiState.instances.find { it.id == id }
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
                            StartClicked = {
                                viewModel.startInstance(selectedInstance!!.instanceName)
                            },
                            ReStartClicked = {
                                viewModel.reStartInstance(selectedInstance!!.instanceName)
                            },
                            StopClicked = {
                                viewModel.stopInstance(selectedInstance!!.instanceName)
                            },
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
    checkedids :List<String>,
    onAllChecked : (Boolean) -> Unit,
    onRowChecked : (Boolean, String) -> Unit,
    onRowSelected : (String) -> Unit
) {
    var columnTypes by remember{ mutableStateOf(INSTANCE_COLUMN_TYPES)}
    var columnWeights by rememberSaveable{ mutableStateOf(INSTANCE_COLUMN_WEIGHTS)}
    var isAllSelected by rememberSaveable { mutableStateOf(false) }
    var isHeaderChecked by rememberSaveable { mutableStateOf(false) }


    LaunchedEffect(checkedids) {
        Timber.tag("uiState")
            .d("${this.javaClass.name} : uiState.checkedids $checkedids")
        if (checkedids.isEmpty()) {
            /* to initialize table checkBoxes*/
            isAllSelected = false
            isHeaderChecked = true
        }
    }

    // 지금 상태가 변할 때마다 다르게 저장됨 -> mutableStateList가 아닌 mutableState 안에 list형태로 관리하는게 맞다
//    val rowItems = remember { mutableStateListOf<TableRowItem>() }
    var rowItems by remember { mutableStateOf(emptyList<TableRowItem>())}
    rowItems = dataList.map { instanceData ->
        val instanceCreatorCell by mutableStateOf(TableCell(instanceData.creatorName))
        val instanceNameCell by mutableStateOf(TableCell(instanceData.instanceName))
        val instanceIdCell by mutableStateOf(TableCell(instanceData.instanceId))
        val instanceStateCell by mutableStateOf(
            TableCell( text = instanceData.instanceStatus,
                color = convertStatusColor(instanceData.instanceStatus)))
        val instanceTypeCell by mutableStateOf(TableCell(instanceData.instanceType))
        val createDateCell by mutableStateOf(TableCell(convertDateFormat(instanceData.createdDate)))
        val cellItems by remember { mutableStateOf(
            listOf(instanceCreatorCell,instanceNameCell,instanceIdCell,instanceStateCell,instanceTypeCell,createDateCell)
        ) }

        TableRowItem(
                rowID = instanceData.id,
                columnTypes = columnTypes,
                isChecked = instanceData.id in checkedids,
                isSelected = false,
                cells = cellItems.toList()
        )
    }
    BasicTable(
        modifier = modifier,
        tableHeaderItem = TableHeaderItem(
            textList = INSTANCE_COLUMN_HEADERS,
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
    totalCnt : Int,
    checkedCnt : Int,
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
                Text(text = "Total : $totalCnt  Selected : $checkedCnt",
                    modifier = Modifier.padding(10.dp)
                )
            }
        }
        CustomOutlinedButton(
            modifier = Modifier.weight(0.06f),
            onBtnClicked = { onLaunchBtnClicked() },
            title = "Launch Instances",
            icons = R.drawable.launch_24
        )
        Spacer(modifier = Modifier.width(5.dp))
        CustomOutlinedButton(
            modifier = Modifier.weight(0.06f),
            onBtnClicked = { onDeleteBtnClicked() },
            title = "Delete Instances",
            icons = R.drawable.ic_baseline_delete_forever_24
        )
        Spacer(modifier = Modifier.width(5.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun InstanceScreenPrev() {
    InstanceScreen(
        onInstanceCreateClicked = {},
        onInstanceDetailClicked = {}
    )
}
