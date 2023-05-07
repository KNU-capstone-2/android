package com.knu.cloud.components.basicTable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Checkbox
import androidx.compose.material.LocalContentColor
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp


object TableCellType{
    const val HEADER = "header"
    const val TEXT = "text"
    const val COLOR_BOX = "colorbox"
    const val CHECK_BOX = "checkbox"
}



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
    checked : Boolean,
    onCheckedChange : ((Boolean) -> Unit)?
) {
    Checkbox(
        checked = checked,
        onCheckedChange = onCheckedChange
    )
}
@Composable
fun TextCell(text : String) {
    Column(
        modifier = Modifier
            .background(Color.Transparent),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = text,
            color = Color.Black.copy(alpha= 0.7f),
            fontWeight = FontWeight.Normal,
            modifier = Modifier
                .padding(5.dp)
        )
    }
}

@Composable
fun ColorBoxCell(
    text : String,
    color: Color,
) {
    Box(
        modifier = Modifier,
        contentAlignment = Alignment.Center
    ){
        Box(modifier = Modifier
            .background(color.copy(alpha = 0.2f))
            .clip(shape = RoundedCornerShape(15.dp))
        ){
            Text(
                text = text,
                color = color.copy(alpha= 0.7f),
                fontWeight = FontWeight.Normal,
                modifier = Modifier
            )
        }
    }

}

@Composable
fun RowScope.TableCellLayout(
    modifier: Modifier = Modifier,
    weight: Float,
    type: String,
    content : @Composable () -> Unit
) {
    Surface (modifier = modifier
        .weight(weight)
        .fillMaxHeight()
        , color = Color.Transparent
    ){
        content()
    }
}