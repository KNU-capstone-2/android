package com.knu.cloud.components.summary

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.knu.cloud.R

@Composable
fun CopyIncludedText(
    context: Context,
    title: String,
    content: String
) {
    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager // 클립보드 복사

    Text(
        text = title,
        fontWeight = FontWeight.Bold,
        color = Color.LightGray,
        fontSize = 18.sp,
    )
    Spacer(modifier = Modifier.height(5.dp))
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        IconButton(
            modifier = Modifier
                .size(23.dp)
                .padding(0.dp),
            onClick = {  // copy
                val clipData = ClipData.newPlainText("content", content)
                clipboard.setPrimaryClip(clipData)
                Toast.makeText(context, "복사 완료!", Toast.LENGTH_SHORT).show()
            }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_copy_24),
                contentDescription = "copy icon",
                modifier = Modifier.size(23.dp),
                tint = Color.Black
            )
        }
        Spacer(modifier = Modifier.width(5.dp))
        Text(
            text = content,
            fontWeight = FontWeight.Bold,
            fontSize = 25.sp,
        )
    }
    Spacer(modifier = Modifier.height(10.dp)) // 위 아래 간격
}
