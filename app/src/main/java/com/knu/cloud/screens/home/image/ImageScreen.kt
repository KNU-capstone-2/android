package com.knu.cloud.screens.home.image

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
import com.knu.cloud.R
import com.knu.cloud.components.CenterLottieLoadingIndicator
import com.knu.cloud.components.CustomOutlinedButton
import com.knu.cloud.components.DeleteConfirmDialog
import com.knu.cloud.components.DeleteResultDialog
import com.knu.cloud.components.basicTable.*
import com.knu.cloud.model.instanceCreate.ImageData
import com.knu.cloud.utils.convertStatusColor
import timber.log.Timber

val IMAGE_COLUMN_HEADERS =  listOf("Image Name", "Status","Size", "Create Date", "Update Date")
val IMAGE_COLUMN_TYPES  = listOf(
    TableColumnType.Text,
    TableColumnType.ColorBox,
    TableColumnType.ColorBox,
    TableColumnType.ColorBox,
    TableColumnType.ColorBox
)
val IMAGE_COLUMN_WEIGHTS  =  listOf(.2f,.1f,.1f,.1f,.1f)

@Composable
fun ImageScreen(
    onImageCreateClicked: () -> Unit = {},
    onImageDetailClicked: (String) -> Unit = {},
    modifier: Modifier = Modifier,
    viewModel : ImageViewModel  = hiltViewModel()
) {
    val context = LocalContext.current // Toast 메세지를 위함
    val uiState by viewModel.uiState.collectAsState()
    var selectedImage by rememberSaveable {
        mutableStateOf<ImageData?>(null)
    }
    var isDeleteConfirmDialogOpen by remember { mutableStateOf(false) }

    if (isDeleteConfirmDialogOpen) {
        DeleteConfirmDialog(
            data = "이미지",
            onDeleteBtnClicked = {
                viewModel.deleteImages()
            },
            onCloseBtnClicked =  {
                isDeleteConfirmDialogOpen = false
            }
        )
    }

    if (uiState.deleteComplete){
        DeleteResultDialog(
            data = "이미지",
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
            ImageBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                totalCnt = uiState.images.size,
                checkedCnt = uiState.checkedImageIds.size,
                onLaunchBtnClicked = onImageCreateClicked,
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
                    ImageTable(
                        modifier = Modifier,
                        dataList = uiState.images,
                        checkedImageIds = uiState.checkedImageIds,
                        onAllChecked = {
                            viewModel.allImagesCheck(it)
                        },
                        onRowChecked = { checked, imageId ->
                            if (checked) {
                                viewModel.imageCheck(imageId)
                            } else {
                                viewModel.imageUncheck(imageId)
                            }
                        },
                        onRowSelected = { imageId ->
                            val selectedData = uiState.images.find { it.id == imageId }
                            selectedImage =
                                if (selectedImage == selectedData) null else selectedData
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun ImageBar(
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
                Text("Images",
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
            title = "Create Image",
            icons = R.drawable.launch_24
        )
        Spacer(modifier = Modifier.width(5.dp))
        CustomOutlinedButton(
            modifier = Modifier.weight(0.06f),
            onBtnClicked = { onDeleteBtnClicked() },
            title = "Delete Images",
            icons = R.drawable.ic_baseline_delete_forever_24
        )
        Spacer(modifier = Modifier.width(5.dp))
    }
}

@Composable
fun ImageTable(
    modifier: Modifier = Modifier,
    dataList :List<ImageData>,
    checkedImageIds :List<String>,
    onAllChecked : (Boolean) -> Unit,
    onRowChecked : (Boolean, String) -> Unit,
    onRowSelected : (String) -> Unit
) {
    var isAllSelected by rememberSaveable { mutableStateOf(false) }
    var isHeaderChecked by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(checkedImageIds) {
        Timber.d("checkedImageIds $checkedImageIds")
        if (checkedImageIds.isEmpty()) {
            /* to initialize table checkBoxes*/
            isAllSelected = false
            isHeaderChecked = true
        }
    }

    val columnHeaders = IMAGE_COLUMN_HEADERS

    var columnTypes by remember{ mutableStateOf(IMAGE_COLUMN_TYPES)}
    var columnWeights by rememberSaveable{ mutableStateOf(IMAGE_COLUMN_WEIGHTS)}
    /**
    *  || Name || Status || Size || CreateDate || UpdateDate
    * */
    var rowItems by remember { mutableStateOf(emptyList<TableRowItem>())}
    rowItems = dataList.map { imageData ->
        val imageNameCell by mutableStateOf( TableCell(imageData.name ))
        val imageStatusCell by mutableStateOf(
            TableCell( text = imageData.status,
                color = convertStatusColor(imageData.status)
            )
        )
        val imageSizeCell by mutableStateOf( TableCell("${imageData.size} MB") )
        val createDateCell by mutableStateOf( TableCell( imageData.createdDate))
        val updateDateCell by mutableStateOf( TableCell( imageData.updateDate))
        val cellItems by remember { mutableStateOf(
            listOf(imageNameCell,imageStatusCell,imageSizeCell,createDateCell,updateDateCell
        )) }
        TableRowItem(
            rowID = imageData.id,
            columnTypes = columnTypes,
            isChecked = imageData.id in checkedImageIds,
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
