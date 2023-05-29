package com.knu.cloud.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.knu.cloud.R

@Composable
fun CustomOutlinedButton(
    modifier: Modifier = Modifier,
    onBtnClicked : () -> Unit,
    title: String,
    icons: Int
) {
    OutlinedButton(
        modifier = modifier,
        onClick = onBtnClicked,
        shape = RoundedCornerShape(15.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.Outlined_button_color))
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = icons),
                contentDescription = "TEST",
            )
            Spacer(modifier = Modifier.width(20.dp))
            Text(
                text = title,
                color = colorResource(id = R.color.Outline_button_text_color),
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(vertical = 5.dp)
            )
        }
    }
}
