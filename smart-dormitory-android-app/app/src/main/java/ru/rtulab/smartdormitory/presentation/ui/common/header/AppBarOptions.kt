package ru.rtulab.smartdormitory.presentation.ui.common.header

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector


sealed class AppBarOption(
    open val icon: ImageVector,
    open val contentDescription: String? = null
) {
    class Clickable(
        override val icon: ImageVector,
        override val contentDescription: String? = null,
        val badgeCount: Int = 0,
        val onClick: () -> Unit
    ) : AppBarOption(icon, contentDescription)

    class Dropdown(
        override val icon: ImageVector,
        override val contentDescription: String? = null,
        val dropdownMenuContent: @Composable (collapseAction: () -> Unit) -> Unit
    ) : AppBarOption(icon, contentDescription)
}

val emptyBackAction: () -> Unit = {}