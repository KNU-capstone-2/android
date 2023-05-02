package com.knu.cloud.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.knu.cloud.R
import com.knu.cloud.navigation.*
import timber.log.Timber

@Composable
fun NavDrawer(
    modifier : Modifier =Modifier,
    sections : Array<Section>,
    currentRoute: String,
    navigateToRoute: (String) -> Unit
){
    Column(modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ){
        NavDrawerComponent(
            modifier = Modifier
                .fillMaxWidth()
            ,
            sections,
            SectionType.COMPUTE,
            currentRoute,
            showDivider = true,
            navigateToRoute
        )
        NavDrawerComponent(
            modifier = Modifier
                .fillMaxWidth()
            ,
            sections,
            SectionType.NETWORK,
            currentRoute,
            showDivider = false,
            navigateToRoute
        )
        Spacer(modifier = Modifier.fillMaxHeight())

    }
}

@Composable
fun NavDrawerComponent(
    modifier: Modifier = Modifier,
    sections : Array<Section>,
    sectionType : SectionType,
    currentRoute: String,
    showDivider : Boolean,
    navigateToRoute: (String) -> Unit
) {
    Column(modifier = modifier
        .fillMaxWidth()
        .padding(horizontal = 10.dp),
//        verticalArrangement = Arrangement.SpaceEvenly,
    ) {
        Timber.tag("navigation").d("currentRoute : $currentRoute")
        val currentSection = try{
            sections.first{ it.route == currentRoute}
        }catch (e : NoSuchElementException){
            Timber.tag("navigation").e("currentSection NoSuchElementException")
        }
        val filteredSections : List<Section> = when(sectionType){
            SectionType.COMPUTE -> {
                Text(text = "Compute",
                    modifier = Modifier.padding(vertical = 10.dp),
                    fontWeight = FontWeight.Bold
                )
                sections.filterIsInstance<ComputeSections>()
            }
            SectionType.NETWORK -> {
                Text(text = "Network",
                    modifier = Modifier.padding(vertical = 10.dp),
                    fontWeight = FontWeight.Bold
                )
                sections.filterIsInstance<NetworkSections>()
            }
            else -> {
                emptyList()
            }
        }
        filteredSections.forEach { section ->
            val selected = section == currentSection
            NavDrawerItem(
                modifier = Modifier
                    .fillMaxWidth()
                    .height((LocalContext.current.resources.displayMetrics.heightPixels / 25).dp)
                ,
                icon = { Icon(section.icon, contentDescription = null,modifier = Modifier.padding(horizontal = 10.dp)) } ,
                label = { Text(section.title) },
                selected = selected,
                onClick = { navigateToRoute(section.route) }
            )
        }
    }
    if(showDivider){
        Divider(modifier = Modifier.height(1.dp))
    }
}


@Composable
fun ColumnScope.NavDrawerItem(                                  // BottimNavigationItem을 custom함
    selected: Boolean,
    onClick: () -> Unit,
    icon: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    label: @Composable () -> Unit,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    selectedContentColor: Color = LocalContentColor.current,
    unselectedContentColor: Color = selectedContentColor.copy(alpha = ContentAlpha.medium)
){
    val ripple = rememberRipple(bounded = true, color = selectedContentColor)
    Box(
        modifier
        , contentAlignment = Alignment.CenterStart
//            .weight(1f)
    ){
        Surface(modifier = modifier.fillMaxSize()
            .padding(vertical = 5.dp)
            , shape = RoundedCornerShape(45.dp)
        ) {
            BottomNavigationTransition(
                selectedContentColor,
                unselectedContentColor,
                selected
            ) { progress ->
                val animationProgress = 1f
                Row(modifier = modifier
                    .selectable(
                        selected = selected,
                        onClick = onClick,
                        enabled = enabled,
                        role = Role.Tab,
                        interactionSource = interactionSource,
                        indication = ripple
                    )
                    .background(if (selected) colorResource(id = R.color.DrawerItem_selected) else colorResource(id = R.color.TopAppBar_background))
                    .padding(horizontal = 10.dp)
                    ,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    icon()
                    label()
                }
            }
        }
    }
}

@Composable
private fun BottomNavigationTransition(
    activeColor: Color,
    inactiveColor: Color,
    selected: Boolean,
    content: @Composable (animationProgress: Float) -> Unit
) {
    val animationProgress by animateFloatAsState(
        targetValue = if (selected) 1f else 0f,
        animationSpec = BottomNavigationAnimationSpec
    )

    val color = lerp(inactiveColor, activeColor, animationProgress)

    CompositionLocalProvider(
        LocalContentColor provides color.copy(alpha = 1f),
        LocalContentAlpha provides color.alpha,
    ) {
        content(animationProgress)
    }
}

private val BottomNavigationAnimationSpec = TweenSpec<Float>(
    durationMillis = 300,
    easing = FastOutSlowInEasing
)