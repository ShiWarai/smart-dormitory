package ru.rtulab.smartdormitory.presentation.ui.report

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import ru.rtulab.smartdormitory.R
import ru.rtulab.smartdormitory.presentation.navigation.LocalNavController
import ru.rtulab.smartdormitory.presentation.ui.common.AppBarTabRow
import ru.rtulab.smartdormitory.presentation.ui.common.LoadingIndicator
import ru.rtulab.smartdormitory.presentation.viewmodel.singletonViewModel
import ru.rtulab.smartdormitory.ui.theme.Accent
import ru.rtulab.smartdormitory.ui.theme.Green
import ru.rtulab.smartdormitory.ui.theme.Red
import ru.rtulab.smartdormitory.ui.theme.White

@ExperimentalPagerApi
@Composable
fun Reports(
    reportViewModel: ReportViewModel = singletonViewModel(),

    ) {
    val navController = LocalNavController.current

    val pagerState = rememberPagerState()
    var isRefreshing by remember { mutableStateOf(false) }


    val tabs = listOf(
        stringResource(R.string.Active),
        stringResource(R.string.Ended),


        )
    val reportsDto = reportViewModel.reportsDtoFlow.collectAsState().value

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier
                    .padding(16.dp),
                onClick = {
                    //  navController.navigate("${AppScreen.BookingCreate.navLink}")

                },
                shape = RoundedCornerShape(8.dp),
                backgroundColor = Accent,
                contentColor = White,
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.Add)
                )

            }
        }
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {

            Surface(
                color = MaterialTheme.colors.primarySurface,
                contentColor = contentColorFor(MaterialTheme.colors.primarySurface),
                elevation = AppBarDefaults.TopAppBarElevation
            ) {
                AppBarTabRow(

                    pagerState = pagerState,
                    tabs = tabs,
                    modifier = Modifier
                        .fillMaxWidth(),
                    isScrollable = true
                )
            }




            HorizontalPager(
                modifier = Modifier.fillMaxSize(),
                count = tabs.size,
                state = pagerState

            ) { currentPage ->
                SwipeRefresh(
                    modifier = Modifier
                        .fillMaxSize(),
                    state = rememberSwipeRefreshState(isRefreshing),
                    onRefresh = {
                        reportViewModel.onRefresh()
                    }
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp)
                    ) {
                        reportsDto.handle(
                            onLoading = {
                                LoadingIndicator()
                            },
                            onError = { msg ->
                                Text(text = msg)
                            },
                            onSuccess = { reports ->

                                when (tabs[currentPage]) {
                                    stringResource(R.string.Active) -> {
                                        LazyColumn(
                                            modifier = Modifier
                                                .fillMaxSize()
                                        ) {
                                            items(
                                                reports.sortedBy { it!!.id }.reversed()
                                                    .filter { !it!!.isDone }) { report ->
                                                ReportCard(
                                                    modifier = Modifier
                                                        .clickable {
                                                            //  navController.navigate("${AppScreen.BookingDetails.navLink}/${b.id}")

                                                        },
                                                    report = report!!,
                                                    status = stringResource(R.string.New),
                                                    statusColor = Green
                                                )
                                            }
                                        }
                                    }
                                    stringResource(R.string.Ended) -> {
                                        LazyColumn(
                                            modifier = Modifier
                                                .fillMaxSize()
                                        ) {
                                            items(
                                                reports.sortedBy { it!!.id }.reversed()
                                                    .filter { it!!.isDone }) { report ->
                                                ReportCard(
                                                    modifier = Modifier
                                                        .clickable {
                                                            //   navController.navigate("${AppScreen.BookingDetails.navLink}/${b.id}")

                                                        },
                                                    report = report!!,
                                                    status = stringResource(R.string.Done),
                                                    statusColor = Red
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        )
                    }
                }
            }
        }
    }

}