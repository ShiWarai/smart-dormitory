package ru.rtulab.smartdormitory.presentation.ui.booking

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.rtulab.smartdormitory.R
import ru.rtulab.smartdormitory.presentation.navigation.AppScreen
import ru.rtulab.smartdormitory.presentation.navigation.LocalNavController
import ru.rtulab.smartdormitory.presentation.ui.common.*
import ru.rtulab.smartdormitory.presentation.ui.common.topAppBar.AppBarViewModel
import ru.rtulab.smartdormitory.presentation.ui.objects.ObjectViewModel
import ru.rtulab.smartdormitory.presentation.viewmodel.singletonViewModel
import ru.rtulab.smartdormitory.ui.theme.Green
import ru.rtulab.smartdormitory.ui.theme.Red
import ru.rtulab.smartdormitory.ui.theme.White
import ru.rtulab.smartdormitory.ui.theme.White50

@Composable
fun BookingDetails(
    bookingItemViewModel: BookingItemViewModel = singletonViewModel(),
    bookingViewModel: BookingViewModel = singletonViewModel(),
    objectViewModel: ObjectViewModel = singletonViewModel(),
    bookingId:String
){
    LaunchedEffect(null) {
        bookingItemViewModel.fetchBookingDetails(bookingId)

    }
    DisposableEffect(Unit){
        onDispose {
            bookingItemViewModel.fetchBookingDetails(bookingId)
        }
    }
    val objres = objectViewModel.objectsResourceFlow.collectAsState().value
    val objtypesres = objectViewModel.objectTypesResourceFlow.collectAsState().value
    val objroomsres = objectViewModel.objectRoomsResourceFlow.collectAsState().value
    var objok = objectViewModel.objectsFlow.collectAsState().value

    val navController = LocalNavController.current


    val bookingres = bookingItemViewModel.bookingResourceFlow.collectAsState().value
    objtypesres.handle(
        onLoading = {
        },
        onError = { msg ->
            LoadingError(msg = msg)
        },
        onSuccess = { types ->
            objroomsres.handle(
                onLoading = {
                },
                onError = { msg ->
                    LoadingError(msg = msg)
                },
                onSuccess = { rooms ->
                    objres.handle(
                        onLoading = {
                        },
                        onError = { msg ->
                            LoadingError(msg = msg)
                        },
                        onSuccess = { obj ->
                            bookingres.handle(
                                onLoading = {
                                },
                                onError = { msg ->
                                    LoadingError(msg = msg)
                                },
                                onSuccess = { booking ->
                                    objok = objectViewModel.onResourceSuccess(obj,types,rooms)

                                    val objl = booking.toBooking(objok.find { o -> o.id == booking.objectId.toString() }!!)
                                    objl.run {
                                        Column(
                                            modifier = Modifier
                                                .padding(horizontal = 16.dp, vertical = 32.dp)
                                        ) {
                                            H1(text = stringResource(R.string.booking_details) + " №" + id)
                                            Text(
                                                text = status,
                                                modifier = Modifier
                                                    .padding(top = 8.dp),
                                                fontSize = 14.sp,
                                                lineHeight = 20.sp,
                                                color = when (status) {
                                                    "AWAITED" -> White
                                                    "CANCELED" -> Red
                                                    "ACTIVE" -> Green
                                                    "DONE" -> White50
                                                    else -> White
                                                }
                                            )
                                            Text(
                                                text = stringResource(R.string.object_details),
                                                modifier = Modifier
                                                    .padding(top = 24.dp),
                                                fontSize = 16.sp,
                                                lineHeight = 24.sp
                                            )

                                            ObjectCard(
                                                modifier = Modifier
                                                    .padding(top = 12.dp)
                                                    .clickable {
                                                        navController.navigate("${AppScreen.ObjectDetails.navLink}/${objectId}")

                                                    },
                                                name = name,
                                                room = "Комната №$room",
                                                type = type,
                                                statusColor = Color.Transparent
                                            )

                                            Text(
                                                text = stringResource(R.string.period_used),
                                                modifier = Modifier
                                                    .padding(top = 24.dp),
                                                fontSize = 16.sp,
                                                lineHeight = 24.sp
                                            )
                                            Row(
                                                modifier = Modifier
                                                    .padding(top = 12.dp)
                                            ) {
                                                Column(
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                        .weight(0.5f, false),
                                                ) {
                                                    Row() {
                                                        Text(
                                                            text = stringResource(R.string.Start),
                                                            fontSize = 11.sp
                                                        )
                                                    }
                                                    Row() {
                                                        Text(
                                                            text = dMYhm(startTime),
                                                            fontSize = 14.sp
                                                        )
                                                    }
                                                }
                                                Column(
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                        .weight(0.5f, false),
                                                ) {
                                                    Row() {
                                                        Text(
                                                            text = stringResource(R.string.End),
                                                            fontSize = 11.sp
                                                        )
                                                    }
                                                    Row() {
                                                        Text(
                                                            text = dMYhm(endTime),
                                                            fontSize = 14.sp
                                                        )
                                                    }
                                                }
                                            }
                                            if (status != "DONE" && status != "CANCELED"
                                            ) {
                                                ButtonFill(
                                                    modifier = Modifier
                                                        .padding(top = 34.dp),
                                                    text = when (status) {
                                                        "AWAITED" -> stringResource(
                                                            R.string.CancelBooking
                                                        )
                                                        "ACTIVE" -> stringResource(
                                                            R.string.EndBoooking
                                                        )
                                                        else -> stringResource(R.string.SomethingWrong)
                                                    },
                                                    onClick = {
                                                        bookingItemViewModel.cancel(bookingId)
                                                        navController.popBackStack()
                                                        bookingViewModel.onRefresh()
                                                    }
                                                )
                                            }
                                        }
                                    }
                                })
                        })
                })

    }
    )
}