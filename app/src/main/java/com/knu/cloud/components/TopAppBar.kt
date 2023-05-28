package com.knu.cloud.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.knu.cloud.R

@Composable
fun ProjectAppBar(
    title: String,
    path: String,
    onLogOutClicked : () -> Unit,
    onBackArrowClicked:() -> Unit = {}
) {
    var menuExpanded by remember {
        mutableStateOf(false)
    }
    TopAppBar(
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowLeft,
                    contentDescription = "ArrowBack",
                    modifier = Modifier
                        .clickable {
                            onBackArrowClicked.invoke()
                        }
                        .size(35.dp)
                )
                Spacer(modifier = Modifier.width(5.dp))
                val painter = rememberAsyncImagePainter(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(R.drawable.splash)
                        .size(100)
                        .crossfade(true)
                        .build(),
                )
                if (painter.state is AsyncImagePainter.State.Loading ||
                    painter.state is AsyncImagePainter.State.Error
                ) {
                    CircularProgressIndicator()
                }
                Image(
                    painter = painter,
                    contentDescription = "logo Img",
                    modifier = Modifier.size(30.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = title,
                    style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp)
                )
                Spacer(modifier = Modifier.width(20.dp))
                Text(
                    text = path,
                    style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 12.sp, color = colorResource(id = R.color.TopAppBar_path))
                )
            }
        },
        actions = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription = "person",
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text(text = "admin")
                IconButton(
                    onClick = {
                        menuExpanded = !menuExpanded
                    }
                ) {
                    Icon(
                        imageVector = Icons.Filled.KeyboardArrowDown,
                        contentDescription = "DropDownBtn",
                    )
                }
                DropdownMenu(
                    expanded = menuExpanded,
                    onDismissRequest = { menuExpanded = false }
                ) {
                    DropdownMenuItem(
                        onClick = { /*TODO*/ }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Settings,
                            contentDescription = "Settings",
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(text = "Settings")
                    }
                    DropdownMenuItem(
                        onClick = { /*TODO*/ }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_baseline_help_24),
                            contentDescription = "Help",
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(text = "Help")
                    }
                    DropdownMenuItem(
                        onClick = { /*TODO*/ }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_baseline_download_24),
                            contentDescription = "openstack file",
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(text = "OpenStack RC File")
                    }
                    Divider()
                    Column(
                        modifier = Modifier.height(100.dp),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "    Themes:")
                        Text(text = "         Default")
                        Text(text = "     ✓ Material")
                    }
                    Divider()
                    DropdownMenuItem(
                        onClick = onLogOutClicked
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_baseline_exit_to_app_24),
                            contentDescription = "logout",
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(text = "LogOut")
                    }
                }
            }
        },
        backgroundColor = colorResource(id = R.color.TopAppBar_background),
        elevation = 0.dp
    )
}