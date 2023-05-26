package com.knu.cloud.components.basicTable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Checkbox
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


sealed class TableColumnType{
    object Text :TableColumnType()
    object ColorBox :TableColumnType()
    object CheckBox :TableColumnType()
}

data class TableRowData(
    val dataList : List<String>,
    var isRowSelected : Boolean
)

@Composable
fun HeaderCell(
    text : String
) {
    Column(
        modifier = Modifier.background(Color.Transparent),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = text,
            color = Color.Black.copy(alpha= 0.7f),
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier
                .padding(5.dp)
        )
    }
}

@Composable
fun CheckBoxCell(
    modifier: Modifier = Modifier,
    checked : Boolean,
    onCheckedChange : ((Boolean) -> Unit)?
) {
    Checkbox(
        modifier = modifier,
        checked = checked,
        onCheckedChange = onCheckedChange
    )
}
@Composable
fun TextCell(
    cell : TableCell,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .background(Color.Transparent),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = cell.text,
            color = Color.Black.copy(alpha= 0.7f),
            fontWeight = FontWeight.Normal,
            modifier = Modifier
                .padding(5.dp)
        )
    }
}

@Composable
fun ColorBoxCell(
    modifier: Modifier = Modifier,
    cell : TableCell
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.CenterStart
    ){
        val boxColor = if(cell.color == Color.Black) Color.LightGray
            else cell.color
        Box(modifier = modifier
            .clip(shape = RoundedCornerShape(10.dp))
            .background(boxColor)
            .padding(5.dp)
        ){
            Text(
                text = cell.text,
                color = Color.Black,
                fontWeight = FontWeight.Normal,
                modifier = Modifier
            )
        }
    }

}

@Composable
fun RowScope.TableCellLayout(
    modifier: Modifier = Modifier,
    weight: Float? = null,
//    type: TableColumnType,
    content: @Composable () -> Unit
) {
    Surface (modifier = modifier
        .then(if(weight !=null) Modifier.weight(weight) else Modifier)
        .fillMaxHeight()
        , color = Color.Transparent
    ){
        content()
    }
}

@Preview(showBackground = true)
@Composable
fun TestColorBoxCell() {

}