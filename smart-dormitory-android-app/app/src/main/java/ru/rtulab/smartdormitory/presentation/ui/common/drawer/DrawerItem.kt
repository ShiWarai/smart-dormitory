package ru.rtulab.smartdormitory.presentation.ui.common.drawer

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentColor
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.CoroutineScope
import ru.rtulab.smartdormitory.presentation.navigation.AppTab

@Composable
fun DrawerItem(
    tab: AppTab,
    scope: CoroutineScope,
    selected: Boolean,
    onItemClick: (AppTab) -> Unit,
    selectedContentColor: Color = LocalContentColor.current,
    unselectedContentColor: Color = selectedContentColor.copy(alpha = ContentAlpha.medium)
) {
    val background = if (selected) selectedContentColor else unselectedContentColor
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = {

                onItemClick(tab)
            }
            )
            .height(45.dp)
            .background(background)
            .padding(start = 10.dp)
    ) {
        Icon(painter = painterResource(tab.icon), contentDescription = tab.route)

        Spacer(modifier = Modifier.width(7.dp))
        Text(
            text = stringResource(id = tab.resourceId),
            fontSize = 18.sp,
            color = Color.White
        )
    }
}