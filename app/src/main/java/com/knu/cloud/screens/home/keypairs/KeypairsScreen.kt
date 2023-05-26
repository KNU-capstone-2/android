package com.knu.cloud.screens.home.keypairs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.knu.cloud.components.CenterLottieLoadingIndicator
import com.knu.cloud.components.DeleteConfirmDialog
import com.knu.cloud.components.DeleteResultDialog
import com.knu.cloud.components.basicTable.*
import com.knu.cloud.model.instanceCreate.KeypairData
import timber.log.Timber

val KEYPAIR_COLUMN_HEADERS  = listOf("Key Pair Name", "Type","FingerPrint")
val KEYPAIR_COLUMN_TYPES  = listOf(
    TableColumnType.Text,
    TableColumnType.ColorBox,
    TableColumnType.Text
)
val KEYPAIR_COLUMN_WEIGHTS  = listOf(.2f,.1f,.4f)

@Composable
fun KeypairsScreen(
    onKeypairCreateClicked: () -> Unit,
    onKeypairDetailClicked: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel : KeypairsViewModel  = hiltViewModel()
) {
    val context = LocalContext.current // Toast 메세지를 위함
    val uiState by viewModel.uiState.collectAsState()
    var selectedKeypair by rememberSaveable {
        mutableStateOf<KeypairData?>(null)
    }
    var isDeleteConfirmDialogOpen by remember { mutableStateOf(false) }

    if (isDeleteConfirmDialogOpen) {
        DeleteConfirmDialog(
            data = "키페어",
            onDeleteBtnClicked = {
                viewModel.deleteKeypairs()
            },
            onCloseBtnClicked =  {
                isDeleteConfirmDialogOpen = false
            }
        )
    }

    if (uiState.deleteComplete){
        DeleteResultDialog(
            data = "키페어",
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
            KeypairBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                totalCnt = uiState.keypairs.size,
                checkedCnt = uiState.checkedKeypairIds.size,
                onLaunchBtnClicked = onKeypairCreateClicked,
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
                    KeypairTable(
                        modifier = Modifier,
                        dataList = uiState.keypairs,
                        checkedKeypairIds = uiState.checkedKeypairIds,
                        onAllChecked = {
                            viewModel.allKeypairsCheck(it)
                        },
                        onRowChecked = { checked, keypairName ->
                            if (checked) {
                                viewModel.keypairCheck(keypairName)
                            } else {
                                viewModel.keypairUnCheck(keypairName)
                            }
                        },
                        onRowSelected = { keypairName ->
                            val selectedData = uiState.keypairs.find { it.name == keypairName }
                            selectedKeypair =
                                if (selectedKeypair == selectedData) null else selectedData
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun KeypairBar(
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
                Text("Key Pairs",
                    modifier = Modifier.padding(10.dp),
                    style = MaterialTheme.typography.h6
                )
                Text(text = "Total : $totalCnt  Selected : $checkedCnt",
                    modifier = Modifier.padding(10.dp)
                )
            }
        }
        OutlinedButton(
            modifier = Modifier.weight(0.1f),
            onClick = onLaunchBtnClicked
        ) {
            Text(text = "Create Key Pair")
        }
        OutlinedButton(
            modifier = Modifier.weight(0.1f),
            onClick =  onDeleteBtnClicked
        ) {
            Text(text = "Delete Key Pairs")
        }
    }
}

@Composable
fun KeypairTable(
    modifier: Modifier = Modifier,
    dataList :List<KeypairData>,
    checkedKeypairIds :List<String>,
    onAllChecked : (Boolean) -> Unit,
    onRowChecked : (Boolean, String) -> Unit,
    onRowSelected : (String) -> Unit
) {
    var isAllSelected by rememberSaveable { mutableStateOf(false) }
    var isHeaderChecked by rememberSaveable { mutableStateOf(false) }
    var columnHeaders by  remember { mutableStateOf(KEYPAIR_COLUMN_HEADERS)}
    var columnTypes by remember{ mutableStateOf(KEYPAIR_COLUMN_TYPES )}
    var columnWeights by rememberSaveable{ mutableStateOf( KEYPAIR_COLUMN_WEIGHTS)}
    var rowItems by remember { mutableStateOf(emptyList<TableRowItem>()) }

    LaunchedEffect(checkedKeypairIds) {
        Timber.d("checkedKeypairIds : $checkedKeypairIds")
        if (checkedKeypairIds.isEmpty()) {
            /* to initialize table checkBoxes*/
            isAllSelected = false
            isHeaderChecked = true
        }
    }

    rowItems = dataList.map { keypairData ->
        val keypairNameCell by mutableStateOf( TableCell(keypairData.name ))
        val keypairTypeCell by mutableStateOf( TableCell(keypairData.type))
        val keypairFingerprintCell by mutableStateOf( TableCell("${keypairData.fingerprint}"))
        val cellItems = remember { mutableListOf(
            keypairNameCell,keypairTypeCell,keypairFingerprintCell
        ) }
        TableRowItem(
            rowID = keypairData.name,
            columnTypes = columnTypes,
            isChecked = keypairData.name in checkedKeypairIds,
            isSelected = false,
            cells = cellItems.toList()
        )
    }


    BasicTable(
        modifier = modifier,
        tableHeaderItem = TableHeaderItem(
            textList = columnHeaders,
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