package com.knu.cloud.components.summary

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.knu.cloud.R
import com.knu.cloud.components.CustomOutlinedButton
import com.knu.cloud.components.InstanceActionButtons
import com.knu.cloud.model.home.instance.InstanceData
import com.knu.cloud.utils.convertStatusColor


@Composable
fun InstanceSummary(
    context: Context,
    instance: InstanceData?,
    StartClicked: () -> Unit,
    ReStartClicked: () -> Unit,
    StopClicked: () -> Unit,
    onInstanceDetailClicked : (String) -> Unit
) {
    Column(
        modifier = Modifier
            .background(color = colorResource(id = R.color.instance_Summary_background))
            .border(width = 1.dp, color = Color.Black)
            .padding(20.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.Start,
    ) {
        if(instance == null){
            Box(modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ){
                Text(text = "Instance 생성 또는 선택해 주세요")
            }
        }else{
            Text(
                text = stringResource(id = R.string.IS_name),
                fontWeight = FontWeight.Bold,
                color = Color.LightGray,
                fontSize = 18.sp,
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = instance.instanceName,
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp,
            )
            InstanceActionButtons(
                StartClicked = {
                    StartClicked()
                    Toast.makeText(context, "Start!", Toast.LENGTH_SHORT).show()
                },
                ReStartClicked = {
                    ReStartClicked()

                    Toast.makeText(context, "ReStart!", Toast.LENGTH_SHORT).show()
                },
                StopClicked = {
                    StopClicked()
                    Toast.makeText(context, "Stop!", Toast.LENGTH_SHORT).show()
                }
            )
            Divider()
            Spacer(modifier = Modifier.height(10.dp))

            CopyIncludedText(
                context = context,
                title = stringResource(id = R.string.IS_id),
                content = instance.instanceId
            )
            CopyIncludedText(
                context = context,
                title = "네트워크 이름",
                content = instance.networkName
            )
            CopyIncludedText(
                context = context,
                title = "네트워크 주소",
                content = instance.networkAddresses
            )
            StateWithText(
                title = stringResource(id = R.string.IS_state),
                stateIcon = R.drawable.instance_running,
                contentColor = convertStatusColor(status = instance.instanceStatus),
                content = instance.instanceStatus
            )
            CopyIncludedText(
                context = context,
                title = "호스트 이름 유형",
                content = instance.hostNameType
            )
            CustomOutlinedButton(
                onBtnClicked = { onInstanceDetailClicked(instance.id) },
                title = "Instance Detail",
                icons = R.drawable.ic_baseline_manage_search_24
            )
        }
    }
}
