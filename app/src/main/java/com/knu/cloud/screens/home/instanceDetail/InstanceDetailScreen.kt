package com.knu.cloud.screens.home.instanceDetail

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.knu.cloud.R
import com.knu.cloud.components.CenterCircularProgressIndicator
import com.knu.cloud.components.InstanceActionButtons
import com.knu.cloud.components.LineChartComponent
import com.knu.cloud.components.summary.CopyIncludedText
import com.knu.cloud.components.summary.StateWithText
import com.knu.cloud.utils.convertDateFormat

val data = listOf(
    Pair(1, 111.45),
    Pair(2, 111.0),
    Pair(3, 113.45),
    Pair(4, 112.25),
    Pair(5, 116.45),
    Pair(6, 118.65),
    Pair(7, 110.15),
    Pair(8, 113.05),
    Pair(9, 114.25),
    Pair(10, 111.85),
    Pair(11, 110.85)
)

@Composable
fun InstanceDetailScreen (
    id : String,
    viewModel : InstanceDetailViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()
    LaunchedEffect(Unit){
        viewModel.getInstance(id)
    }
    LaunchedEffect(uiState.message){
        if(uiState.message.isNotEmpty()){
            Toast.makeText(context, uiState.message, Toast.LENGTH_SHORT).show()
        }
    }

    if(uiState.isLoading){
        CenterCircularProgressIndicator()
    } else{
        if(uiState.instance == null){
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ){
                Text(text = "해당 인스턴스에 대한 정보가 없습니다")
            }
        }else{
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp)
                    .background(Color.White)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Column(
                        modifier = Modifier
                            .weight(.2f),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.Start
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Start
                        ) {
                            Text(
                                text = "${uiState.instance!!.instanceId}(${uiState.instance!!.instanceName})에 대한 인스턴스 요약",
                                fontSize = 35.sp,
                                fontWeight = FontWeight.Bold,
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                modifier = Modifier,
                                text = "정보",
                                fontSize = 15.sp,
                                color = colorResource(id = R.color.skyBlue)
                            )
                        }
                        Text(
                            text = "6분 전에 업데이트됨",
                            fontSize = 20.sp
                        )
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.End
                    ) {
                        InstanceActionButtons(
                            StartClicked = {
                                viewModel.startInstance(id)
                                Toast.makeText(context, "Start!", Toast.LENGTH_SHORT).show()
                            },
                            ReStartClicked = {
                                 viewModel.reStartInstance(id)
                                Toast.makeText(context, "ReStart!", Toast.LENGTH_SHORT).show()
                            },
                            StopClicked = {
                                viewModel.stopInstance(id)
                                Toast.makeText(context, "Stop!", Toast.LENGTH_SHORT).show()
                            }
                        )
                    }
                }
                Divider(
                    color = Color.LightGray,
                    thickness = 1.dp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 1.dp, vertical = 5.dp)
                )
                Row(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Column(
                        modifier = Modifier
                            .weight(.1f)
                            .padding(start = 10.dp, end = 10.dp)
                    ) {
                        CopyIncludedText(
                            context = context,
                            title = stringResource(R.string.IS_id),
                            content = uiState.instance!!.instanceId
                        )
                        CopyIncludedText(
                            context = context,
                            title = "네트워크 이름",
                            content = uiState.instance!!.networkName
                        )
                        Box(
                            modifier = Modifier.padding(100.dp)
                        )
                        CopyIncludedText(
                            context = context,
                            title = "호스트 이름 유형",
                            content = uiState.instance!!.hostNameType
                        )
                        CopyIncludedText(
                            context = context,
                            title = "이미지 이름",
                            content = uiState.instance?.imageName ?: "null"
                        )
                        CopyIncludedText(
                            context = context,
                            title = "네트워크 주소",
//                            content = "54.209.252.119 [퍼블릭 IP]"
                            content = uiState.instance!!.networkAddresses
                        )
                    }
                    Divider(
                        color = Color.LightGray,
                        thickness = 1.dp,
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(1.dp)
                    )
                    Column(
                        modifier = Modifier.weight(.3f)
                    ) {
                        Row() {
                            Column(
                                modifier = Modifier
                                    .weight(.1f)
                                    .padding(10.dp)
                            ) {
                                CopyIncludedText(
                                    context = context,
                                    title = "자동 할당된 IP 주소",
                                    content = uiState.instance!!.networkAddresses
                                )
                                StateWithText(
                                    title = "인스턴스 상태",
                                    stateIcon = R.drawable.instance_running,
                                    contentColor = R.color.instance_running_text,
                                    content = uiState.instance!!.instanceStatus
                                )
                                CopyIncludedText(
                                    context = context,
                                    title = "보안 그룹",
                                    content = uiState.instance!!.securityGroups
                                )
                                CopyIncludedText(
                                    context = context,
                                    title = "인스턴스 유형",
                                    content = uiState.instance!!.instanceType
                                )
                            }
                            Column(
                                modifier = Modifier
                                    .weight(.1f)
                                    .padding(10.dp)
                            ) {
                                CopyIncludedText(
                                    context = context,
                                    title = "생성 날짜",
                                    content = convertDateFormat(uiState.instance!!.createdDate)
                                )
                                CopyIncludedText(
                                    context = context,
                                    title = "키페어",
                                    content = uiState.instance!!.keypairName
                                )
                                CopyIncludedText(
                                    context = context,
                                    title = "전력 상태",
                                    content = uiState.instance!!.powerState,
                                )
                                CopyIncludedText(
                                    context = context,
                                    title = "작업 상태",
                                    content = uiState.instance!!.taskState
                                )
                            }
                        }
                        Divider(
                            color = Color.LightGray,
                            thickness = 1.dp,
                            modifier = Modifier
                                .fillMaxWidth()
                        )
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(20.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .weight(.1f)
                            ) {
                                LineChartComponent(
                                    title = "CPU 사용률(%)",
                                    data = data
                                )
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Box(
                                modifier = Modifier
                                    .weight(.1f)
                            ) {
                                LineChartComponent(
                                    title = "네트워크 입력(바이트)",
                                    data = data
                                )
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Box(
                                modifier = Modifier
                                    .weight(.1f)
                            ) {
                                LineChartComponent(
                                    title = "네트워크 패킷",
                                    data = data
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
