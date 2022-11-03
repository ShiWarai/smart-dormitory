package ru.rtulab.smartdormitory.presentation.ui.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.pagerTabIndicatorOffset
import kotlinx.coroutines.launch
import ru.rtulab.smartdormitory.ui.theme.White50
import java.util.*


@ExperimentalPagerApi
@Composable
fun AppBarTabRow(
    pagerState: PagerState,
    tabs: List<String>,
    modifier: Modifier = Modifier,
    isScrollable: Boolean = false
) {
    val coroutineScope = rememberCoroutineScope()
    @Composable
    fun tabRowContent() {
        tabs.forEachIndexed { index, it ->
            Tab(
                text = {
                    Text(
                        text = it,
                        fontSize = 14.sp,
                        lineHeight = 20.sp
                    )
                },
                selected = pagerState.currentPage == index,
                onClick = {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                },
                unselectedContentColor = White50,
                selectedContentColor = MaterialTheme.colors.primary
            )
        }
    }
    if (isScrollable)
        ScrollableTabRow(
            modifier = modifier.fillMaxWidth(),
            selectedTabIndex = pagerState.currentPage,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    Modifier.pagerTabIndicatorOffset(pagerState, tabPositions)
                )
            },
            //contentColor = MaterialTheme.colors.primary,
            backgroundColor = Color.Transparent
        ) {
            tabRowContent()
        }
    else
        TabRow(
            modifier = modifier,
            selectedTabIndex = pagerState.currentPage,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    Modifier. pagerTabIndicatorOffset(pagerState, tabPositions)
                )
            },
            contentColor = MaterialTheme.colors.primary
        ) {
            tabRowContent()
        }
}