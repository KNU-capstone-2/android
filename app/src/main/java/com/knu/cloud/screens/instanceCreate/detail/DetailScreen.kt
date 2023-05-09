package com.knu.cloud.screens.instanceCreate.detail

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.toSize
import androidx.hilt.navigation.compose.hiltViewModel
import com.knu.cloud.R
import com.knu.cloud.components.DonutChart
import com.knu.cloud.components.DonutChartComponent
import com.knu.cloud.components.text_input.ProjectTextInput
import com.knu.cloud.components.text_input.TextInputType
import com.knu.cloud.components.text_input.addFocusCleaner
import com.knu.cloud.screens.instanceCreate.InstanceCreateViewModel

@ExperimentalComposeUiApi
@Composable
fun DetailScreen(
    viewModel: InstanceCreateViewModel = hiltViewModel()
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = stringResource(R.string.IC_Detail_description),
                style = MaterialTheme.typography.subtitle2,
                modifier = Modifier.padding(15.dp)
            )
            Detail(viewModel = viewModel)
        }
    }
}

@ExperimentalComposeUiApi
@Preview(showBackground = true)
@Composable
fun Detail(
    viewModel: InstanceCreateViewModel = InstanceCreateViewModel(),
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(600.dp), // Prevent vertical infinity maximum height constraints
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .weight(.2f)
                .verticalScroll(rememberScrollState())
                .padding(20.dp)
                .addFocusCleaner(keyboardController!!),
        ) {
            Text(
                text = stringResource(id = R.string.IC_Detail_Header_ProjectName),
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 15.dp, start = 8.dp, end = 15.dp, bottom = 5.dp)
            )
            ProjectTextInput(
                type = TextInputType.FIELD,
                keyboardController = keyboardController,
            )
            Text(
                text = stringResource(id = R.string.IC_Detail_Header_InstanceName),
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 25.dp, start = 8.dp, end = 15.dp, bottom = 5.dp)
            )
            ProjectTextInput(
                type = TextInputType.FIELD,
                keyboardController = keyboardController,
            )

            Text(
                text = stringResource(id = R.string.IC_Detail_Header_Info),
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 25.dp, start = 8.dp, end = 15.dp, bottom = 5.dp)
            )
            ProjectTextInput(
                type = TextInputType.FIELD,
                keyboardController = keyboardController,
            )

            Text(
                text = stringResource(id = R.string.IC_Detail_Header_Area),
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 25.dp, start = 8.dp, end = 15.dp, bottom = 10.dp)
            )
            DropdownCompute() // 가용구역 선택

            Text(
                text = stringResource(id = R.string.IC_Detail_Header_Count),
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 25.dp, start = 8.dp, end = 15.dp, bottom = 5.dp)
            )
            ProjectTextInput(
                type = TextInputType.FIELD,
                keyboardController = keyboardController,
            )
        } // Column End

        Column(
            modifier = Modifier
                .weight(.1f)
                .verticalScroll(rememberScrollState())
                .padding(20.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            DonutChartComponent(
                // 인스턴스 현황
                // Current Usage, Added, Remaining 순
                chartValues = listOf(0, 1, 9)
            )
        } // Column End
    }
}

@Composable
fun DropdownCompute() {
    var expanded by remember { mutableStateOf(false) }
    val items = listOf("Nova") // Nova 하나로 고정
    var selectedIndex by remember { mutableStateOf(0) }
    var fieldSize by remember { mutableStateOf(Size.Zero)}

    Box (
        modifier = Modifier
            .fillMaxSize()
            .onGloballyPositioned { coordinates ->
                // This value is used to assign to
                // the DropDown the same width
                fieldSize = coordinates.size.toSize()
            }
            .clip(shape = RoundedCornerShape(15.dp))
            .border(BorderStroke(1.dp, Color.Gray), shape = RoundedCornerShape(15.dp))
            .graphicsLayer(shape = RoundedCornerShape(20.dp))
            .clickable(onClick = { expanded = true }),
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text (
                text = items[selectedIndex],
            )
            Icon(
                imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                tint = MaterialTheme.colors.secondary,
                contentDescription = "button",
            )
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .width(with(LocalDensity.current) { fieldSize.width.toDp() })
                .height(60.dp),
        ) {
            items.forEachIndexed { index, s ->
                DropdownMenuItem(onClick = {
                    selectedIndex = index
                    expanded = false
                }) {
                    Text(
                        text = s,
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun CustomDonutChart() {

    val chartColors = listOf(
        MaterialTheme.colors.primary,
        MaterialTheme.colors.primaryVariant,
        MaterialTheme.colors.secondary
    )

    val chartValues = listOf(60f, 110f, 20f)
    DonutChart(
        modifier = Modifier.padding(20.dp),
        colors = chartColors,
        inputValues = chartValues,
        textColor = MaterialTheme.colors.secondaryVariant
    )
}

@Preview
@Composable
fun DetailScreenPreview() {

}
