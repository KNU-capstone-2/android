package com.knu.cloud.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.knu.cloud.R


/**
 * @param data : 삭제할 데이터의 이름
 * */
@Composable
fun DeleteConfirmDialog(
    data : String,
    onDeleteBtnClicked: () -> Unit,
    onCloseBtnClicked: () -> Unit
) {
    AlertDialog(
        onDismissRequest = {onCloseBtnClicked()},
        title = {
            Text(text = "$data 데이터 삭제")
        },
        text = {
            Text(text = "저장된 $data 데이터를 삭제 하시겠습니까?")
        },
        buttons = {
            Row(
                modifier = Modifier
                    .width(350.dp)
                    .padding(bottom = 12.dp),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(
                    onClick = {
                        onDeleteBtnClicked()
                        onCloseBtnClicked()
                    }
                ) {
                    Text("확인")
                }
                TextButton(
                    onClick = { onCloseBtnClicked() }
                ) {
                    Text("취소")
                }
            }
        },
        shape = RoundedCornerShape(24.dp)
    )
}

/**
 * @param data : 삭제된 데이터의 이름
 * */
@Composable
fun DeleteResultDialog(
    data : String,
    deleteResult : List<Pair<String,Boolean>>,
    onCloseBtnClicked: () -> Unit
) {
    AlertDialog(
        onDismissRequest = {onCloseBtnClicked()},
        title = {
            Text(text = "$data 삭제 결과")
        },
        text = {
            deleteResult.forEach {
                if(it.second){
                    Text(text = "$data ${it.first} 삭제 결과 : 성공)")
                }else{
                    Text(text = "$data ${it.first} 삭제 결과 : 실패")
                }
            }
        },
        buttons = {
            Row(
                modifier = Modifier
                    .width(350.dp)
                    .verticalScroll(rememberScrollState())
                    .padding(bottom = 12.dp),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(
                    onClick = {
                        onCloseBtnClicked()
                    }
                ) {
                    Text("확인")
                }
            }
        },
        shape = RoundedCornerShape(24.dp)
    )
}
