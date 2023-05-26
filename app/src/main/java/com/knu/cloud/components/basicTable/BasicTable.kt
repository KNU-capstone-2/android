package com.knu.cloud.components.basicTable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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

        TableCellLayout(
            modifier = Modifier.width(64.dp)
        ) {
            CheckBoxCell(
                modifier = Modifier,
                checked = checked,
                onCheckedChange = onAllRowSelected
            )
        }
//        TableCellLayout(modifier,weight = 0.05f) {
//        }
        repeat(weightList.size){idx->
            TableCellLayout(modifier, weight = weightList[idx]) {
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

data class TableCell(
    val text : String,
    val color : Color = Color.Black
)
data class TableRowItem(
    val rowID : String,
    val columnTypes: List<TableColumnType>,
    val cells : List<TableCell>,
    val isChecked : Boolean,
    val isSelected : Boolean
)
//    val textList: List<String>,
//    val colorList: List<Color>,
//    val columnWeights: List<Float>,
//    var isSelected: MutableState<Boolean>


@Composable
fun BasicTable(
    modifier: Modifier = Modifier,
    tableHeaderItem: TableHeaderItem,
    tableRowItems : List<TableRowItem>,
    columnTypes : List<TableColumnType>,
    columnWeights : List<Float>,
    onAllChecked : (Boolean) -> Unit,
    onRowChecked : (Boolean, String) -> Unit,
    onRowSelected : (String) -> Unit
) {
    var selectedItemIndex by rememberSaveable { mutableStateOf(-1) }
    var isAllSelected by rememberSaveable{ mutableStateOf(false) }
    var isHeaderClick by rememberSaveable{ mutableStateOf(false) }

    Column(
        modifier = modifier
    ) {
        TableHeader(
            textList = tableHeaderItem.textList,
            weightList = tableHeaderItem.weightList,
            checked = isAllSelected,
            onAllRowSelected = {selected ->
                isAllSelected = selected
                isHeaderClick = true
                onAllChecked(selected)
            }
        )
        if (!isHeaderClick && tableRowItems.all { it.isSelected}){
            isAllSelected = true
        }
        // TODO : 인스턴스 하나 체크하고 헤더 체크하면 selected가 2가 됨
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ){
            itemsIndexed(
                tableRowItems
            ){index, item ->
                LaunchedEffect(isHeaderClick ){
                    if(isHeaderClick){
                        onRowChecked(isAllSelected,item.rowID)
                    }
                }
                TableRow(
                    modifier = modifier,
                    tableItem = item,
                    columnTypes = columnTypes,
                    columnWeights = columnWeights,
                    isRowChecked = item.isChecked,
                    onRowChecked = { checked ->
                        if (isAllSelected){
                            isAllSelected = false
                        }
                        isHeaderClick = false
                        Timber.tag("vm_test").d("BasicTable onRowChecked")
                        onRowChecked(checked, item.rowID)
                    },
                    isRowSelected = index == selectedItemIndex,
                    onRowSelected = { tableRowItem ->
//                        selectedItem = tableRowItem
                        selectedItemIndex = if(selectedItemIndex == index) -1 else index
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
    tableItem : TableRowItem,
    columnTypes: List<TableColumnType>,
    columnWeights: List<Float>,
    isRowChecked :Boolean,
    onRowChecked : ((Boolean) -> Unit)?,
    isRowSelected : Boolean,
    onRowSelected : (TableRowItem) -> Unit
) {

    Row(
        modifier = modifier
            .height(50.dp)
            .clickable(
                interactionSource = MutableInteractionSource(),
                indication = null
            ) {
                onRowSelected(tableItem)
            }
            .background(if (isRowSelected) Color.LightGray.copy(alpha = .3f) else Color.Transparent)
    ) {
        TableCellLayout(
            modifier = Modifier.width(64.dp)
        ) {
            CheckBoxCell(
                checked = isRowChecked,
                onCheckedChange = onRowChecked
            )
        }
        repeat(tableItem.columnTypes.size){ idx->
            TableCellLayout(modifier, weight = columnWeights[idx]) {
                when(tableItem.columnTypes[idx]){
                    is TableColumnType.Text -> TextCell(cell = tableItem.cells[idx])
                    is TableColumnType.ColorBox-> ColorBoxCell(cell = tableItem.cells[idx])
                    is TableColumnType.CheckBox ->{}
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


//@Preview(showBackground = true)
//@Composable
//fun TestTableRow() {
//    val tableRowItem = TableRowItem(
//        columnTypes =listOf(TableCellType.TEXT,TableCellType.TEXT,TableCellType.COLOR_BOX,TableCellType.COLOR_BOX,TableCellType.COLOR_BOX),
//        textList = listOf("ec2-test","i-of204053ab80b5cc8","Running","t2.micro","2/2 check passe"),
//        colorList = listOf(Color.Black,Color.Black,Color.Green,Color.LightGray,Color.Green),
//        columnWeights = listOf(.1f,.1f,.1f,.1f,.1f),
//        rowID = "i-of204053ab80b5cc8",
//        isSelected = remember{ mutableStateOf(false) }
//    )
//    Surface(modifier = Modifier.fillMaxWidth()) {
//        TableRow(
//            tableItem = tableRowItem ,
//            isRowChecked = false ,
//            onRowChecked = {},
//            isRowSelected = false,
//            onRowSelected = {}
//        )
//    }
//}
//
//@Preview(showBackground = true, device = Devices.TABLET)
//@Composable
//fun TestInstanceTable() {
//    val testHeaderItem = TableHeaderItem(
//        textList = listOf("Instance Name", "Instance ID","Instance State", "Instance Type", "Status Check"),
//        weightList = listOf(.1f,.1f,.1f,.1f,.1f),
//    )
//    val testRowItems = mutableListOf<TableRowItem>(
//        TableRowItem(
//            columnTypes =listOf(TableCellType.TEXT,TableCellType.TEXT,TableCellType.COLOR_BOX,TableCellType.COLOR_BOX,TableCellType.COLOR_BOX),
//            textList = listOf("ec2-test","i-of204053ab80b5cc8","Running","t2.micro","2/2 check passe"),
//            colorList = listOf(Color.Black,Color.Black,Color.Green,Color.LightGray,Color.Green),
//            columnWeights = listOf(.1f,.1f,.1f,.1f,.1f),
//            rowID = "i-of204053ab80b5cc8",
//            isSelected = remember{ mutableStateOf(false) }
//        ),
//        TableRowItem(
//            columnTypes =listOf(TableCellType.TEXT,TableCellType.TEXT,TableCellType.COLOR_BOX,TableCellType.COLOR_BOX,TableCellType.COLOR_BOX),
//            textList = listOf("ec2-test","i-of204053ab80b5cc8","Running","t2.micro","2/2 check passe"),
//            colorList = listOf(Color.Black,Color.Black,Color.Green,Color.LightGray,Color.Green),
//            columnWeights = listOf(.1f,.1f,.1f,.1f,.1f),
//            rowID = "i-of204053ab80b5cc8",
//            isSelected = remember{ mutableStateOf(false) }
//        )
//    )
//    BasicTable(
//        tableHeaderItem = testHeaderItem,
//        tableRowItems = testRowItems,
//        onAllChecked = {},
//        onRowChecked = {a,b ->},
//        onRowSelected = {},
//    )
//}