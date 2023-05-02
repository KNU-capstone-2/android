package com.knu.cloud.screens.home.instance

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.knu.cloud.components.ProjectAppBar
import com.knu.cloud.components.summary.InstanceData
import com.knu.cloud.components.summary.InstanceSummary


val testData = InstanceData(
    instancesId = "i-0f204053ab80b5cc8",
    instancesName = "ec2-test",
    publicIPv4Address = "52.83.423.531",
    privateIPv4Address = "172.31.5.206",
    instanceState = "Running",
    publicIPv4DNS = "ec2-52-78-233-109 ap",
    hostNameType = "ip-173-31-92-94.31.ec2",
    privateIpDNSname = "IPv4(A)",
    instanceType = "t2.micro"
)

@Composable
fun InstanceScreen (
    onInstanceCreateClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    Scaffold(
        topBar = {
            ProjectAppBar(
                title = "POCKET",
                path = "프로젝트 / Compute / 인스턴스"
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
            ) {
                /* 모달드로우 할당 영역 */
                Column(
                    modifier = Modifier
                        .weight(.1f)
                        .fillMaxSize()
                        .border(BorderStroke(1.dp, Color.Black))
                ) {
                    Text(
                        text = "모달드로우 할당 영역",
                        modifier = Modifier.padding(start = 15.dp, top = 500.dp, bottom = 500.dp)
                    )
                }
                // 모달드로우 end

                /* 테이블 할당 영역 */
                Column(
                    modifier = Modifier
                        .weight(.2f)
                        .fillMaxSize()
                        .border(BorderStroke(1.dp, Color.Black))
                ) {
                    Text(
                        text = "테이블 할당 영역",
                        modifier = Modifier.padding(start = 15.dp, top = 500.dp, bottom = 500.dp)
                    )
                }
                // 테이블 end

                Column(
                    modifier = Modifier
                        .weight(.1f)
                        .fillMaxSize()
                ) {
                    InstanceSummary(
                        context = context,
                        instance = testData,
                        StartClicked = { /*TODO*/ },
                        ReStartClicked = { /*TODO*/ },
                        StopClicked = { /*TODO*/ },
                    )
                }
            }
        }
    }
}
