package com.knu.cloud.screens.home.instanceDetail

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.knu.cloud.R
import com.knu.cloud.components.InstanceActionButtons
import com.knu.cloud.components.LineChartComponent
import com.knu.cloud.components.ProjectAppBar
import com.knu.cloud.components.summary.CopyIncludedText
import com.knu.cloud.components.summary.StateWithText
import com.knu.cloud.screens.home.instance.InstanceData

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

val testData = InstanceData(
    instancesId = "i-0f204053ab80b5cc8",
    instancesName = "ec2-test",
    publicIPv4Address = "52.83.423.531",
    privateIPv4Address = "172.31.5.206",
    instanceState = "Running",
    publicIPv4DNS = "ec2-52-78-233-109 ap",
    hostNameType = "ip-173-31-92-94.31.ec2",
    privateIpDNSname = "ip-172-31-92.42.ec2.internal",
    instanceType = "t2.micro",
    statusCheck = "2/2 check passe"
)

@Composable
fun InstanceDetailScreen (
    instanceId : String
) {
    val context = LocalContext.current
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
                        text = "${testData.instancesId}(${testData.instancesName})에 대한 인스턴스 요약",
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
                    text = "less than a minute 전에 업데이트됨",
                    fontSize = 20.sp
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                InstanceActionButtons(
                    StartClicked = {
                        Toast.makeText(context, "Start!", Toast.LENGTH_SHORT).show()
                    },
                    ReStartClicked = {
                        Toast.makeText(context, "ReStart!", Toast.LENGTH_SHORT).show()
                    },
                    StopClicked = {
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
                    content = testData.instancesId
                )
                CopyIncludedText(
                    context = context,
                    title = "IPv6 Address",
                    content = ""
                )
                Box(
                    modifier = Modifier.padding(100.dp)
                )
                CopyIncludedText(
                    context = context,
                    title = "호스트 이름 유형",
                    content = "IP 이름: ip-173-31-92-94.31.ec2"
                )
                CopyIncludedText(
                    context = context,
                    title = "프라이빗 리소스 DNS 이름 응답",
                    content = "IPv4(A)"
                )
                CopyIncludedText(
                    context = context,
                    title = "자동 할당된 IP 주소",
                    content = "54.209.252.119 [퍼블릭 IP]"
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
                        modifier = Modifier.weight(.1f).padding(10.dp)
                    ) {
                        CopyIncludedText(
                            context = context,
                            title = "자동 할당된 IP 주소",
                            content = "54.209.252.119 [퍼블릭 IP]"
                        )
                        StateWithText(
                            title = "인스턴스 상태",
                            stateIcon = R.drawable.instance_running,
                            contentColor = R.color.instance_running_text,
                            content = "Running"
                        )
                        CopyIncludedText(
                            context = context,
                            title = "프라이빗 IP DNS 이름(IPv4만 해당)",
                            content = "ip-172-31-92.42.ec2.internal"
                        )
                        CopyIncludedText(
                            context = context,
                            title = "인스턴스 유형",
                            content = "t2.micro"
                        )
                    }
                    Column(
                        modifier = Modifier.weight(.1f).padding(10.dp)
                    ) {
                        CopyIncludedText(
                            context = context,
                            title = "프라이빗 IPv4 주소",
                            content = "172.31.92.85"
                        )
                        CopyIncludedText(
                            context = context,
                            title = "퍼블릿 IPv4 DNS",
                            content = "ec2-54-209-252-119.compute-1"
                        )
                        CopyIncludedText(
                            context = context,
                            title = "VPC ID",
                            content = "vpc-0e7bf769e09f3210e"
                        )
                        CopyIncludedText(
                            context = context,
                            title = "서브넷 ID",
                            content = "subnet-09ae1985405edbb0b"
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
                    modifier = Modifier.fillMaxWidth().padding(20.dp)
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
