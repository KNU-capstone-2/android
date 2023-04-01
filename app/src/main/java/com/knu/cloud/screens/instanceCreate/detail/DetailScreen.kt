package com.knu.cloud.screens.instanceCreate

import android.app.Activity
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.lifecycle.ViewModelProvider
import com.knu.cloud.MainActivity
import com.knu.cloud.R
import com.knu.cloud.components.DonutChart
import com.knu.cloud.components.text_input.ProjectTextInput
import com.knu.cloud.components.text_input.TextInputType
import com.knu.cloud.components.text_input.addFocusCleaner

@ExperimentalComposeUiApi
@Composable
fun DetailScreen(
    viewModel: InstanceCreateViewModel
) {
    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = Color.White
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ){
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
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ){
        Column(
            modifier = Modifier
//                    .fillMaxWidth()
                .width(500.dp)
                .verticalScroll(rememberScrollState())
                .padding(20.dp)
                .addFocusCleaner(keyboardController!!),
        ) {

            Text(
                text = "Project Name",
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 15.dp, start = 8.dp, end = 15.dp, bottom = 5.dp)
            )
            ProjectTextInput(
                type = TextInputType.FIELD,
                keyboardController = keyboardController,
            )
            Text(
                text = "인스턴스 이름",
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 25.dp, start = 8.dp, end = 15.dp, bottom = 5.dp)
            )
            ProjectTextInput(
                type = TextInputType.FIELD,
                keyboardController = keyboardController,
            )

            Text(
                text = "설명",
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 25.dp, start = 8.dp, end = 15.dp, bottom = 5.dp)
            )
            ProjectTextInput(
                type = TextInputType.FIELD,
                keyboardController = keyboardController,
            )

            Text(
                text = "가용 구역",
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 25.dp, start = 8.dp, end = 15.dp, bottom = 10.dp)
            )

            DropdownCompute()

            Text(
                text = "개수",
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 25.dp, start = 8.dp, end = 15.dp, bottom = 5.dp)
            )
            ProjectTextInput(
                type = TextInputType.FIELD,
                keyboardController = keyboardController,
            )

        }
        Surface(
            modifier = Modifier.width(300.dp)
        ) {
            val chartColors = listOf(
                MaterialTheme.colors.primary,
                MaterialTheme.colors.primaryVariant,
                MaterialTheme.colors.secondary
            )
            val chartValues = listOf(60f, 110f, 20f)
            DonutChart(
                modifier = Modifier
                    .size(width = 300.dp, height = 300.dp)
                    .padding(20.dp),
                colors = chartColors,
                inputValues = chartValues,
                textColor = MaterialTheme.colors.secondaryVariant
            )
        }
    }

}

@Composable
fun DropdownCompute() {
    var expanded by remember { mutableStateOf(false) }
    val items = listOf("Nova")
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
