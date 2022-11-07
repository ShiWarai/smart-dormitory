package ru.rtulab.smartdormitory.presentation.ui.booking

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import ru.rtulab.smartdormitory.R
import ru.rtulab.smartdormitory.presentation.navigation.AppScreen
import ru.rtulab.smartdormitory.presentation.navigation.LocalNavController
import ru.rtulab.smartdormitory.presentation.ui.common.AppBarTabRow
import ru.rtulab.smartdormitory.presentation.ui.common.LoadingIndicator
import ru.rtulab.smartdormitory.presentation.ui.common.ObjectCardWithDate
import ru.rtulab.smartdormitory.presentation.ui.objects.ObjectViewModel
import ru.rtulab.smartdormitory.presentation.viewmodel.singletonViewModel
import ru.rtulab.smartdormitory.ui.theme.*

@OptIn(ExperimentalPagerApi::class)
@Preview
@Composable
fun Booking(
    bookingViewModel: BookingViewModel = singletonViewModel(),
    bookingItemViewModel: BookingItemViewModel = singletonViewModel(),
    objectViewModel: ObjectViewModel = singletonViewModel()
){
    val bookingsDto = bookingViewModel.bookingsResourceFlow.collectAsState().value
    val objectsDto = objectViewModel.objectsResourceFlow.collectAsState().value
    val typesDto = objectViewModel.objectTypesResourceFlow.collectAsState().value
    val roomsDto = objectViewModel.objectRoomsResourceFlow.collectAsState().value


    val navController = LocalNavController.current

    val booking = bookingViewModel.bookingsFlow.collectAsState().value

    val pagerState = rememberPagerState()
    var isRefreshing by remember { mutableStateOf(false) }


    val tabs = listOf(
        stringResource(R.string.Active),
        stringResource(R.string.Ended),
        stringResource(R.string.Canceled),
        stringResource(R.string.Awaited)

    )
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier
                    .padding(16.dp),
                onClick = {
                    navController.navigate("${AppScreen.BookingCreate.navLink}")

                },
                shape = RoundedCornerShape(8.dp),
                backgroundColor = Accent,
                contentColor = White,
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = stringResource(R.string.Add) )

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
                    bookingViewModel.onRefresh()
                }
            ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            ) {


                bookingsDto.handle(
                onLoading = {
                    LoadingIndicator()
                },
                onError = { msg ->
                    Text(text = msg)
                },
                onSuccess = { Dto ->
                    objectsDto.handle(
                        onLoading = {
                            LoadingIndicator()
                        },
                        onError = { msg ->
                            Text(text = msg)
                        },
                        onSuccess = { obj ->
                            typesDto.handle(
                                onLoading = {
                                    LoadingIndicator()
                                },
                                onError = { msg ->
                                    Text(text = msg)
                                },
                                onSuccess = { type ->
                                    roomsDto.handle(
                                        onLoading = {
                                            LoadingIndicator()
                                        },
                                        onError = { msg ->
                                            Text(text = msg)
                                        },
                                        onSuccess = { room ->
                                    bookingViewModel.onResourceSuccess(Dto, obj, type,room)

                                        when (tabs[currentPage]) {
                                            stringResource(R.string.Active) -> {
                                                LazyColumn(
                                                    modifier = Modifier
                                                        .fillMaxSize()
                                                ) {
                                                    items(booking.sortedBy { it.startTime }.reversed().filter { it.status== "ACTIVE" }) { b ->
                                                        ObjectCardWithDate(
                                                            modifier = Modifier
                                                                .clickable {
                                                                    navController.navigate("${AppScreen.BookingDetails.navLink}/${b.id}")

                                                                },
                                                            name = b.name,
                                                            status = b.status,
                                                            type = b.type,
                                                            room = "Комната №${b.room}",
                                                            startTime = b.startTime,
                                                            endTime = b.endTime,
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
                                                    items(booking.sortedBy { it.startTime }.reversed().filter { it.status== "DONE" }) { b ->
                                                        ObjectCardWithDate(
                                                            modifier = Modifier
                                                                .clickable {
                                                                    navController.navigate("${AppScreen.BookingDetails.navLink}/${b.id}")
                                                                },
                                                            name = b.name,
                                                            status = b.status,
                                                            type = b.type,
                                                            room = "Комната №${b.room}",
                                                            startTime = b.startTime,
                                                            endTime = b.endTime,
                                                            statusColor = White50
                                                        )
                                                    }
                                                }
                                            }
                                            stringResource(R.string.Awaited) -> {
                                                LazyColumn(
                                                    modifier = Modifier
                                                        .fillMaxSize()
                                                ) {
                                                    items(booking.sortedBy { it.startTime }.reversed().filter { it.status== "AWAITED" }) { b ->
                                                        ObjectCardWithDate(
                                                            modifier = Modifier
                                                                .clickable {
                                                                    navController.navigate("${AppScreen.BookingDetails.navLink}/${b.id}")
                                                                },
                                                            name = b.name,
                                                            status = b.status,
                                                            type = b.type,
                                                            room = "Комната №${b.room}",
                                                            startTime = b.startTime,
                                                            endTime = b.endTime,
                                                            statusColor = White
                                                        )
                                                    }
                                                }
                                            }
                                            stringResource(R.string.Canceled) -> {
                                                LazyColumn(
                                                    modifier = Modifier
                                                        .fillMaxSize()
                                                ) {
                                                    items(booking.sortedBy { it.startTime }.reversed().filter { it.status== "CANCELED" }) { b ->
                                                        ObjectCardWithDate(
                                                            modifier = Modifier
                                                                .clickable {
                                                                    navController.navigate("${AppScreen.BookingDetails.navLink}/${b.id}")
                                                                },
                                                            name = b.name,
                                                            status = b.status,
                                                            type = b.type,
                                                            room = "Комната №${b.room}",
                                                            startTime = b.startTime,
                                                            endTime = b.endTime,
                                                            statusColor = Red
                                                        )
                                                    }
                                                }
                                            }
                                        }
                                    })
                                }

)
                        }
                    )
                })
            }

            }

        }
    }
    }

}