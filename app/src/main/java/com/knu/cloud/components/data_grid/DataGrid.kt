package com.knu.cloud.components.data_grid

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.knu.cloud.model.instanceCreate.FlavorResponse
import com.knu.cloud.model.instanceCreate.KeypairResponse
import com.knu.cloud.model.instanceCreate.NetworkResponse
import com.knu.cloud.model.instanceCreate.Source
import com.knu.cloud.utils.*

@Composable
fun DataGridBar(
    type: String,
    numbers: Int,
    expanded: Boolean,
    onExpanded: (Boolean) -> Unit
) {
    var expanded2 = expanded
    Row(
        modifier = Modifier
            .padding(5.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        ExpandedItemButton(
            expanded = expanded2,
            onClick = {
                expanded2 = !expanded2
                onExpanded(expanded2)
            }
        )
        Text(
            text = type,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(end = 10.dp)
        )
        Surface(
            modifier = Modifier
                .padding(0.dp),
            shape = CircleShape,
            color = Color.LightGray
        ) {
            Text(
                text = numbers.toString(),
                style = MaterialTheme.typography.caption,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(5.dp)
            )
        }
    }
}

@Composable
fun DataGridHeader(
    screenType: String,
) {
    Column(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {
        when(screenType) {
            "Flavor" -> FlavorHeaderTableCell()
            "Source" -> SourceHeaderTableCell()
            "Network" -> NetworkHeaderTableCell()
            "Keypair" -> KeypairHeaderTableCell()
        }
    }
}

@Composable
fun <T> DataGridElementList(
    modifier: Modifier = Modifier,
    item: T,
    index: Int,
    type: String,
    screenType: String,
    onClickButton: (T, Int) -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(start = 15.dp, end = 15.dp)
    ) {
        when(screenType) {
            "Flavor" ->
                FlavorDataTableCell(
                    item = item as FlavorResponse, // Flavor로 캐스팅
                    index = index,
                    type = type
                ) { it, idx ->
                    onClickButton(it as T, idx) // 제너릭 타입으로 캐스팅
                }
            "Source" ->
                SourceDataTableCell(
                    item = item as Source, // Source로 캐스팅
                    index = index,
                    type = type
                ) { it, idx ->
                    onClickButton(it as T, idx) // 제너릭 타입으로 캐스팅
                }
            "Network" ->
                NetworkDataTableCell(
                    item = item as NetworkResponse, // Network로 캐스팅
                    index = index,
                    type = type
                ) { it, idx ->
                    onClickButton(it as T, idx) // 제너릭 타입으로 캐스팅
                }
            "Keypair" ->
                KeypairDataTableCell(
                    item = item as KeypairResponse, // Network로 캐스팅
                    index = index,
                    type = type
                ) { it, idx ->
                    onClickButton(it as T, idx) // 제너릭 타입으로 캐스팅
                }
        }
    }
}