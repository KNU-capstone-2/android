package com.knu.cloud.screens.home.dashboard

import android.widget.Space
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.knu.cloud.R
import com.knu.cloud.components.PieChartComponent
import com.knu.cloud.components.ProjectAppBar

data class DashboardData(
    val title: String,
    val assignedData: Int,
    val remainingData: Int
)

@Composable
fun DashBoardScreen(
    // viewModel: ViewModel = hiltViewModel()
) {
    var computeDataSet = mutableListOf(
        DashboardData("인스턴스", 4,6),
        DashboardData("VCPUs",2, 8),
        DashboardData("RAM", 3, 7)
    )

    var volumeDataSet = mutableListOf(
        DashboardData("볼륨", 5, 5),
        DashboardData("볼륨 스냅샷", 1, 9),
        DashboardData("볼륨 스토리지", 2, 8)
    )

    var networkDataSet = mutableListOf(
        DashboardData("Floating IP", 1, 50),
        DashboardData("보안 그룹", 1, 9),
        DashboardData("네트워크", 3, 7),
        DashboardData( "포트",3, 500)
    )

    Scaffold(
        topBar = {
            ProjectAppBar(
                title = "POCKET",
                path = "프로젝트 / Compute / 대시보드"
            )
        }
    ) {
        Surface(
            modifier = Modifier
                .padding(it)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .verticalScroll(rememberScrollState())
            ) {
                /* 모달드로우 할당 영역 */
                Column(
                    modifier = Modifier
                        .weight(.1f)
                        .fillMaxSize()
                        .border(BorderStroke(1.dp, Color.Black))
                ) {
                    Text(
                        text= "모달드로우 할당 영역",
                        modifier = Modifier.padding(start = 15.dp, top = 350.dp, bottom = 350.dp)
                    )
                }
                // 모달드로우 end point

                Spacer(modifier = Modifier.width(30.dp))
                Column(
                    modifier = Modifier
                        .weight(.3f)
                        .fillMaxSize()
                ) {
                    Divider(
                        color = Color.LightGray,
                        thickness = 1.dp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 1.dp, vertical = 5.dp)
                    )

                    Compute(dataSet = computeDataSet)

                    Divider(
                        color = Color.LightGray,
                        thickness = 1.dp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 1.dp, vertical = 5.dp)
                    )

                    Volume(dataSet = volumeDataSet)

                    Divider(
                        color = Color.LightGray,
                        thickness = 1.dp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 1.dp, vertical = 5.dp)
                    )

                    Network(dataSet = networkDataSet)

                    Divider(
                        color = Color.LightGray,
                        thickness = 1.dp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 1.dp, vertical = 5.dp)
                    )
                    Usage()
                }
            }
        }
    }
}

@Composable
fun Compute(
    dataSet: List<DashboardData>
) {
    Text(
        text = "Compute",
        fontSize = 20.sp
    )
    Spacer(modifier = Modifier.height(8.dp))

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        dataSet.forEach { data ->
            Column(
                modifier = Modifier.weight(.1f)
            ) {
                PieChartComponent(
                    title = data.title,
                    assignedData = data.assignedData,
                    remainingData = data.remainingData
                )
            }
        }
        Box(modifier = Modifier.weight(.1f))
    }
}

@Composable
fun Volume(
    dataSet: List<DashboardData>
) {
    Text(
        text = "볼륨",
        fontSize = 20.sp
    )
    Spacer(modifier = Modifier.height(8.dp))

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        dataSet.forEach { data ->
            Column(
                modifier = Modifier.weight(.1f)
            ) {
                PieChartComponent(
                    title = data.title,
                    assignedData = data.assignedData,
                    remainingData = data.remainingData
                )
            }
        }
        Box(modifier = Modifier.weight(.1f))
    }
}

@Composable
fun Network(
    dataSet: List<DashboardData>
) {
    Text(
        text = "네트워크",
        fontSize = 20.sp
    )
    Spacer(modifier = Modifier.height(8.dp))

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        dataSet.forEach { data ->
            Column(
                modifier = Modifier.weight(.1f)
            ) {
                PieChartComponent(
                    title = data.title,
                    assignedData = data.assignedData,
                    remainingData = data.remainingData
                )
            }
        }
    }
}

@Composable
fun Usage(

) {
    Text(
        text = "사용량 요약",
        fontSize = 20.sp
    )
    Spacer(modifier = Modifier.height(15.dp))

    Text(
        text = "활성화된 인스턴스 : ",
        fontWeight = Bold,
        fontSize = 15.sp
    )
    Spacer(modifier = Modifier.height(5.dp))

    Text(
        text = "사용 중인 RAM : ",
        fontWeight = Bold,
        fontSize = 15.sp
    )
    Spacer(modifier = Modifier.height(5.dp))

    Text(
        text = "VCPU 사용 시간 : ",
        fontWeight = Bold,
        fontSize = 15.sp
    )
    Spacer(modifier = Modifier.height(5.dp))

    Text(
        text = "GB 사용 시간 : ",
        fontWeight = Bold,
        fontSize = 15.sp
    )
    Spacer(modifier = Modifier.height(5.dp))

    Text(
        text = "RAM 사용 시간 : ",
        fontWeight = Bold,
        fontSize = 15.sp
    )
}

@Preview (showBackground = true)
@Composable
fun HomeScreenPrev() {
//    ProjectHomeScreen()

}