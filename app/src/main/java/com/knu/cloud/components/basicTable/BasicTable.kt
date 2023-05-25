package com.knu.cloud.components.basicTable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Divider
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import timber.log.Timber

@Composable
fun TableHeader(
    modifier: Modifier = Modifier,
    textList : List<String>,
    weightList : List<Float>,
    checked : Boolean,
    onAllRowSelected : ((Boolean) -> Unit)?
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(75.dp)
            .background(Color.LightGray)
        ,
    ) {
        TableCellLayout(modifier,weight = 0.05f, type = TableCellType.CHECK_BOX ) {
            CheckBoxCell(
                checked = checked,
                onCheckedChange = onAllRowSelected
            )
        }
        repeat(weightList.size){idx->
            TableCellLayout(modifier,weight = weightList[idx], type = TableCellType.HEADER) {
                HeaderCell(text =textList[idx])
            }
        }
    }
    Divider(
        color = Color.Black.copy(alpha = 0.3f),
        thickness = 1.dp,
    )
}


data class TableHeaderItem(
    val textList: List<String>,
    val weightList: List<Float>
)
data class TableRowItem(
    val cellTypeList: List<String>,
    val textList: List<String>,
    val colorList: List<Color>,
    val weightList: List<Float>,
    val rowID : String,
    var isSelected: MutableState<Boolean>
)


@Composable
fun BasicTable(
    modifier: Modifier = Modifier,
    tableHeaderItem: TableHeaderItem,
    tableRowItems : List<TableRowItem>,
    isAllSelected : MutableState<Boolean>,
    isHeaderClick : MutableState<Boolean>,
    onAllChecked : (Boolean) -> Unit,
    onRowChecked : (Boolean, String) -> Unit,
    onRowSelected : (String) -> Unit
) {
    val selectedItemIndex = rememberSaveable { mutableStateOf(-1) }

    Column(
        modifier = modifier
    ) {
        TableHeader(
            textList = tableHeaderItem.textList,
            weightList = tableHeaderItem.weightList,
            checked = isAllSelected.value,
            onAllRowSelected = {selected ->
                isAllSelected.value = selected
                isHeaderClick.value = true
                onAllChecked(selected)
            }
        )
        if (!isHeaderClick.value && tableRowItems.all { it.isSelected.value}){
            isAllSelected.value = true
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ){
            itemsIndexed(
                tableRowItems
            ){index, item ->
                if(isHeaderClick.value){
                    item.isSelected.value = isAllSelected.value
                }
                TableRow(
                    modifier = modifier,
                    tableItem = item,
                    isRowChecked = item.isSelected.value,
                    onRowChecked = { checked ->
                        if (isAllSelected.value){
                            isAllSelected.value = false
                        }
                        isHeaderClick.value = false
                        item.isSelected.value = checked
                        Timber.tag("vm_test").d("BasicTable onRowChecked")
                        onRowChecked(checked, item.rowID)
                    },
                    isRowSelected = index == selectedItemIndex.value,
                    onRowSelected = { tableRowItem ->
//                        selectedItem = tableRowItem
                        selectedItemIndex.value = if(selectedItemIndex.value == index) -1 else index
                        onRowSelected(tableRowItem.rowID)
                    }
                )
            }
        }
    }

}

@Composable
fun TableRow(
    modifier: Modifier = Modifier,
    tableItem : TableRowItem ,
    isRowChecked :Boolean,
    onRowChecked : ((Boolean) -> Unit)?,
    isRowSelected : Boolean,
    onRowSelected : (TableRowItem) -> Unit
) {

    Row(
        modifier = modifier
            .height(50.dp)
            .clickable {
                onRowSelected(tableItem)
            }
            .background(if (isRowSelected) Color.LightGray.copy(alpha = .3f) else Color.Transparent)
    ) {
        TableCellLayout(modifier,weight = 0.05f, type = TableCellType.CHECK_BOX) {
            CheckBoxCell(
                checked = isRowChecked,
                onCheckedChange = onRowChecked
            )
        }

        repeat(tableItem.cellTypeList.size){idx->
            TableCellLayout(modifier,weight = tableItem.weightList[idx], type = tableItem.cellTypeList[idx]) {
                when(tableItem.cellTypeList[idx]){
                    TableCellType.TEXT -> TextCell(text = tableItem.textList[idx])
                    TableCellType.COLOR_BOX -> ColorBoxCell(text = tableItem.textList[idx], color = tableItem.colorList[idx])
                }
            }
        }
    }
}


@Preview(showBackground = true, device = Devices.TABLET)
@Composable
fun TestTableHeader() {
    Surface(modifier = Modifier.fillMaxWidth()) {
        TableHeader(
            textList = listOf("Instance Name", "Instance ID"),
            weightList = listOf(.1f,.1f),
            checked = false,
            onAllRowSelected = {}
        )
    }
}


@Preview(showBackground = true)
@Composable
fun TestTableRow() {
    val tableRowItem = TableRowItem(
        cellTypeList =listOf(TableCellType.TEXT,TableCellType.TEXT,TableCellType.COLOR_BOX,TableCellType.COLOR_BOX,TableCellType.COLOR_BOX),
        textList = listOf("ec2-test","i-of204053ab80b5cc8","Running","t2.micro","2/2 check passe"),
        colorList = listOf(Color.Black,Color.Black,Color.Green,Color.LightGray,Color.Green),
        weightList = listOf(.1f,.1f,.1f,.1f,.1f),
        rowID = "i-of204053ab80b5cc8",
        isSelected = remember{ mutableStateOf(false) }
    )
    Surface(modifier = Modifier.fillMaxWidth()) {
        TableRow(
            tableItem = tableRowItem ,
            isRowChecked = false ,
            onRowChecked = {},
            isRowSelected = false,
            onRowSelected = {}
        )
    }
}

@Preview(showBackground = true, device = Devices.TABLET)
@Composable
fun TestInstanceTable() {
    val testHeaderItem = TableHeaderItem(
        textList = listOf("Instance Name", "Instance ID","Instance State", "Instance Type", "Status Check"),
        weightList = listOf(.1f,.1f,.1f,.1f,.1f),
    )
    val testRowItems = mutableListOf<TableRowItem>(
        TableRowItem(
            cellTypeList =listOf(TableCellType.TEXT,TableCellType.TEXT,TableCellType.COLOR_BOX,TableCellType.COLOR_BOX,TableCellType.COLOR_BOX),
            textList = listOf("ec2-test","i-of204053ab80b5cc8","Running","t2.micro","2/2 check passe"),
            colorList = listOf(Color.Black,Color.Black,Color.Green,Color.LightGray,Color.Green),
            weightList = listOf(.1f,.1f,.1f,.1f,.1f),
            rowID = "i-of204053ab80b5cc8",
            isSelected = remember{ mutableStateOf(false) }
        ),
        TableRowItem(
            cellTypeList =listOf(TableCellType.TEXT,TableCellType.TEXT,TableCellType.COLOR_BOX,TableCellType.COLOR_BOX,TableCellType.COLOR_BOX),
            textList = listOf("ec2-test","i-of204053ab80b5cc8","Running","t2.micro","2/2 check passe"),
            colorList = listOf(Color.Black,Color.Black,Color.Green,Color.LightGray,Color.Green),
            weightList = listOf(.1f,.1f,.1f,.1f,.1f),
            rowID = "i-of204053ab80b5cc8",
            isSelected = remember{ mutableStateOf(false) }
        )
    )
    BasicTable(
        tableHeaderItem = testHeaderItem,
        tableRowItems = testRowItems,
        onAllChecked = {},
        onRowChecked = {a,b ->},
        onRowSelected = {},
        isAllSelected = remember {
            mutableStateOf(false)
        },
        isHeaderClick = remember {
            mutableStateOf(false)
        },
    )
}