package com.knu.cloud.components

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
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
    val totalCnt = deleteResult.size
    val successCnt = deleteResult.filter { it.second }.size
    val failCnt = totalCnt - successCnt
    Dialog(
        onDismissRequest = onCloseBtnClicked,
        properties = DialogProperties(
            dismissOnClickOutside = false,
            dismissOnBackPress = false
        )
    ) {
        Surface(
            modifier = Modifier
                .width(500.dp)
                .height(350.dp)
                .clip(RoundedCornerShape(24.dp))
            ,
            shape = MaterialTheme.shapes.medium,
            color =  MaterialTheme.colors.surface,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp)
                ,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "$data 삭제 결과",
                    style = MaterialTheme.typography.h5
                )
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(top = 8.dp)
                    ,
                    contentAlignment = Alignment.CenterStart
                ) {
                    if (deleteResult.isEmpty()){
                        Text(
                            text = "삭제 결과가 없습니다.",
                            style = MaterialTheme.typography.h6
                        )
                    }else{
                        LazyColumn(modifier = Modifier
                            .fillMaxWidth()
                        ) {
                            items(deleteResult){
                                if(it.second){
                                    Text(
                                        text = "$data ${it.first} 삭제 결과 : 성공",
                                        style = MaterialTheme.typography.h6
                                    )
                                }else{
                                    Text(
                                        text = "$data ${it.first} 삭제 결과 : 실패",
                                        style = MaterialTheme.typography.h6
                                    )
                                }
                            }
                        }
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = if (totalCnt > 0) "성공 : $successCnt 실패 : $failCnt " else "")
                    TextButton(
                        onClick = {
                            onCloseBtnClicked()
                        }
                    ) {
                        Text("확인")
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, device = Devices.TABLET)
@Composable
fun DeleteResultDialogPrev() {
    Surface() {
        DeleteResultDialog(
            data = "키페어",
            deleteResult = listOf(
                Pair("aaaaa",true),Pair("aaaaa",true),Pair("aaaaa",true),Pair("aaaaa",true),
                Pair("aaaaa",true),Pair("aaaaa",true),Pair("aaaaa",true),Pair("aaaaa",true),
                Pair("aaaaa",true),Pair("aaaaa",true),Pair("aaaaa",true),Pair("aaaaa",true),
            )
        ) {

        }

    }

}