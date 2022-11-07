package ru.rtulab.smartdormitory.presentation.ui.common.header

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import ru.rtulab.smartdormitory.R
import ru.rtulab.smartdormitory.ui.theme.Accent
import ru.rtulab.smartdormitory.ui.theme.White

@Composable
fun BasicTopAppBar(
    scope:CoroutineScope,
    text: String,
    drawerState: DrawerState,
    backgroundColor:Color = MaterialTheme.colors.background,
    onBackAction: () -> Unit = emptyBackAction,
    options: List<AppBarOption> = listOf(AppBarOption.Clickable(icon = Icons.Default.Notifications, onClick = {/**/}))
) {
    TopAppBar(
        backgroundColor = backgroundColor
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (onBackAction != emptyBackAction) {
                    IconButton(onClick = onBackAction) {
                        Icon(
                            Icons.Default.Close,
                            contentDescription = stringResource(R.string.close),
                        tint = White
                        )
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                }else{
                    IconButton(onClick = {
                        scope.launch {
                            if(drawerState.isOpen)
                                drawerState.close()
                            else
                                drawerState.open()
                        }
                    }) {
                        Icon(
                            Icons.Default.Menu,
                            contentDescription = stringResource(coil.compose.base.R.string.navigation_menu),
                            tint = White
                        )
                    }
                    Spacer(modifier = Modifier.width(16.dp))

                }

                Text(
                    text = text,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start,
                    color = White,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            OptionsRow(options)
        }
    }
}

@Composable
fun OptionsRow(
    options: List<AppBarOption>,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        options.forEach { option ->
            when (option) {
                is AppBarOption.Clickable -> {
                    IconButton(
                        modifier = Modifier
                            .height(24.dp)
                            .width(24.dp),
                        onClick = option.onClick
                    ) {
                        BadgedBox(
                            badge = {
                                if (option.badgeCount > 0)
                                    Badge(
                                        backgroundColor = Accent,
                                        contentColor = White
                                    ) {
                                        Text(option.badgeCount.toString())
                                    }
                            }
                        ) {
                            Icon(
                                imageVector = option.icon,
                                contentDescription = option.contentDescription,
                                tint = MaterialTheme.colors.onSurface
                            )
                        }

                    }
                }
            }

        }
    }
}

