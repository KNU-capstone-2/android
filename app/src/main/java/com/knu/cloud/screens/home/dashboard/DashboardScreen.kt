package com.knu.cloud.screens.home.dashboard

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.knu.cloud.R
import com.knu.cloud.components.LottieImage
import com.knu.cloud.components.dashboard.CardFrame
import com.knu.cloud.components.dashboard.CategoryRow
import com.knu.cloud.model.home.dashboard.DashboardDataSet
import com.knu.cloud.model.home.dashboard.DashboardData
import com.knu.cloud.model.home.dashboard.DashboardUsageData
import com.knu.cloud.model.home.dashboard.DashboardUsageResponse

@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel = hiltViewModel()
) {
    val screenState by viewModel.uiState.collectAsState()

    Content(screenState)
}

@Composable
fun Content(
    state: DashboardState
) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        when (state) {
            is DashboardState.Loading -> LoadingScreen()
            is DashboardState.Success -> ReadyScreen(dataSet = state.dataSet, usageDataSet = state.usageDataSet)
            is DashboardState.Error -> LoadingScreen()
        }
    }
}

@Composable
fun LoadingScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        LottieImage(
            modifier = Modifier
                .size(150.dp),
            rawAnimation = R.raw.loading
        )
    }
}

@Composable
fun ReadyScreen(
    dataSet: DashboardDataSet,
    usageDataSet: DashboardUsageData
) {
    Row(
        modifier = Modifier
            .fillMaxSize()
//            .verticalScroll(rememberScrollState())
    ) {
        Column(
            modifier = Modifier.weight(.2f)
        ) {
            Usage(
                usageDataSet= usageDataSet
            )
        }
        Column(
            modifier = Modifier.weight(.3f)
        ) {
            ChartData(
                modifier = Modifier.weight(.1f),
                type = "Compute",
                dataSet = dataSet.computeDataSet
            )
//            DividerDashboard()
            ChartData(
                modifier = Modifier.weight(.1f),
                type = "Volume",
                dataSet = dataSet.volumeDataSet
            )
//            DividerDashboard()
            ChartData(
                modifier = Modifier.weight(.1f),
                type = "Network",
                dataSet = dataSet.networkDataSet
            )
//            DividerDashboard()
        }
    }
}

@Composable
fun ChartData(
    modifier: Modifier = Modifier,
    type: String,
    dataSet: List<DashboardData>
) {
    CategoryRow(
        modifier = modifier,
        type = type,
        dataSet = dataSet
    )
}

@Composable
fun Usage(
    usageDataSet: DashboardUsageData
) {
    Column(
        modifier = Modifier.padding(12.dp)
    ) {
        Text(
            text = "사용량 요약",
            fontSize = 24.sp,
        )
        Spacer(modifier = Modifier.height(15.dp))

        Test(
            title1 = "활성화된 인스턴스",
            count1 = usageDataSet.activeInstance,
            title2 = "사용 중인 RAM",
            count2 = usageDataSet.RAMTime
        )

        Test(
            title1 = "VCPU 사용시간",
            count1 = usageDataSet.VCPUTime,
            title2 = "GB 사용 시간",
            count2 = usageDataSet.GBTime
        )

        Test(
            title1 = "RAM 사용시간",
            count1 = usageDataSet.RAMTime,
            title2 = "GB 사용 시간",
            count2 = usageDataSet.GBTime
        )
    }
}

val cardTheme = listOf(
    Triple(R.color.dashboard_card_color_1, R.color.dashboard_card_text_1_1, R.color.dashboard_card_text_1_2),
    Triple(R.color.dashboard_card_color_2, R.color.dashboard_card_text_2, R.color.dashboard_card_text_2),
    Triple(R.color.dashboard_card_color_3, R.color.dashboard_card_text_3, R.color.dashboard_card_text_3)
)

@Composable
fun Test(
    title1: String,
    count1: String,
    title2: String,
    count2: String
) {
    Row(
        modifier = Modifier.padding(5.dp)
    ) {
        val themeData1 = cardTheme.random()
        CardFrame(
            title = title1,
            count = count1,
            cardColor = themeData1.first,
            textColor1 = themeData1.second,
            textColor2 = themeData1.third
        )
        Spacer(modifier= Modifier.width(10.dp))
        val themeData2 = cardTheme.random()
        CardFrame(
            title = title2,
            count = count2,
            cardColor = themeData2.first,
            textColor1 = themeData2.second,
            textColor2 = themeData2.third
        )
    }
    Spacer(modifier = Modifier.height(10.dp))
}

@Composable
fun DividerDashboard() {
    Divider(
        color = Color.LightGray,
        thickness = 1.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 1.dp, vertical = 5.dp)
    )
}

@Preview (showBackground = true)
@Composable
fun HomeScreenPrev() {
//    ProjectHomeScreen()

}