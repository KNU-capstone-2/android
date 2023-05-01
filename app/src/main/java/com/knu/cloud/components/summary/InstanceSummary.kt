package com.knu.cloud.components.summary

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
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
import com.knu.cloud.components.InstanceActionButtons

data class InstanceData(
    val instancesId: String,
    val instancesName: String,
    val publicIPv4Address: String,
    val privateIPv4Address: String,
    val instanceState: String,
    val publicIPv4DNS: String
)

@Composable
fun InstanceSummary(
    context: Context,
    instance: InstanceData,
    StartClicked: () -> Unit,
    ReStartClicked: () -> Unit,
    StopClicked: () -> Unit
) {
    Column(
        modifier = Modifier
            .background(color = colorResource(id = R.color.instance_Summary_background))
            .border(width = 1.dp, color = Color.Black)
            .padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start,
    ) {
        Text(
            text = stringResource(id = R.string.IS_name),
            fontWeight = FontWeight.Bold,
            color = Color.LightGray,
            fontSize = 18.sp,
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = instance.instancesName,
            fontWeight = FontWeight.Bold,
            fontSize = 25.sp,
        )
        InstanceActionButtons(
            StartClicked = {
                /*TODO*/
                /* StartClicked() */

                Toast.makeText(context, "Start!", Toast.LENGTH_SHORT).show()
            },
            ReStartClicked = {
                /*TODO*/
                /* ReStartClicked() */

                Toast.makeText(context, "ReStart!", Toast.LENGTH_SHORT).show()
            },
            StopClicked = {
                /*TODO*/
                /* StopClicked() */

                Toast.makeText(context, "Stop!", Toast.LENGTH_SHORT).show()
            }
        )
        Divider()
        Spacer(modifier = Modifier.height(10.dp))

        CopyIncludedText(
            context = context,
            title = stringResource(id = R.string.IS_id),
            content = instance.instancesId
        )
        CopyIncludedText(
            context = context,
            title = stringResource(id = R.string.IS_public_IPv4_address),
            content = instance.publicIPv4Address
        )
        CopyIncludedText(
            context = context,
            title = stringResource(id = R.string.IS_private_IPv4_address),
            content = instance.privateIPv4Address
        )
        StateWithText(
            title = stringResource(id = R.string.IS_state),
            stateIcon = R.drawable.instance_running,
            contentColor = R.color.instance_running_text,
            content = instance.instanceState
        )
        CopyIncludedText(
            context = context,
            title = stringResource(id = R.string.IS_IPv4_DNS),
            content = instance.publicIPv4DNS
        )
    }
}
