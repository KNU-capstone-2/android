package com.knu.cloud.components.basicTable

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun TableHeader(
    modifier: Modifier = Modifier,
    textList : List<String>,
    weightList : List<Float>,
    checked : Boolean,
    onCheckedChange : ((Boolean) -> Unit)?
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
                onCheckedChange = onCheckedChange
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


@Preview(showBackground = true, device = Devices.TABLET)
@Composable
fun TestTableHeader() {
    Surface(modifier = Modifier.fillMaxWidth()) {
        TableHeader(
            textList = listOf("Instance Name", "Instance ID"),
            weightList = listOf(.1f,.1f),
            checked = false,
            onCheckedChange = {}
        )
    }
}