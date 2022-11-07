package ru.rtulab.smartdormitory.presentation.ui.booking

import android.text.format.DateFormat
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.rtulab.smartdormitory.R
import ru.rtulab.smartdormitory.presentation.navigation.LocalNavController
import ru.rtulab.smartdormitory.presentation.ui.common.ButtonFill
import ru.rtulab.smartdormitory.presentation.ui.common.ButtonOutlined
import ru.rtulab.smartdormitory.presentation.ui.common.H1
import ru.rtulab.smartdormitory.presentation.ui.dateTime.Clock
import ru.rtulab.smartdormitory.presentation.ui.dateTime.LineOfTimeWithSelect
import ru.rtulab.smartdormitory.presentation.ui.dateTime.WeekDayItem
import ru.rtulab.smartdormitory.presentation.ui.objects.ObjectViewModel
import ru.rtulab.smartdormitory.presentation.viewmodel.singletonViewModel
import ru.rtulab.smartdormitory.ui.theme.Accent
import ru.rtulab.smartdormitory.ui.theme.White
import java.util.*


@Composable
fun BookingCreateSecond(
    bookingViewModel: BookingViewModel = singletonViewModel(),
    objectViewModel: ObjectViewModel = singletonViewModel()
) {
    val types = objectViewModel.objectTypesResourceFlow.collectAsState().value

    val objdto = objectViewModel.objectsResourceFlow.collectAsState().value

    val navController = LocalNavController.current

    val snackbarHostState = SnackbarHostState()
    Scaffold(
        scaffoldState = rememberScaffoldState(snackbarHostState = bookingViewModel.snackbarHostState)
    ) {

        Surface(
            color = Accent
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 8.dp),
                verticalArrangement = Arrangement.Center

            ) {
                H1(
                    modifier = Modifier
                        .padding(top = 24.dp),
                    text = stringResource(R.string.ChoseTime)
                )
                val date: Date = Calendar.getInstance().time
                val dayOfTheWeek = DateFormat.format("EE", date) as String // Th
                //val day = DateFormat.format("dd", date.time) as String // 20
                val monthString = DateFormat.format("MMM", date) as String // Jun
                val monthNumber = DateFormat.format("MM", date) as String // 06
                val year = DateFormat.format("yyyy", date) as String // 2022

                val hh = DateFormat.format("hh", date) as String //

                val z: TimeZone = Calendar.getInstance().timeZone
                val zchislo = z.getRawOffset() / 1000 / 60 / 60
                val tz = if (zchislo < 10 && zchislo > -10) "0$zchislo" else "$zchislo"

                // val isDay = if(date.hours>12) 0 else 1;


                val day = 86400000
                val hour = 3600000
                val sec = 60000

                val dayOfWeek = remember { mutableStateOf(0) }
                val (leftTwoDig, ltd) = remember {
                    mutableStateOf(
                        DateFormat.format(
                            "hh",
                            (date.time + 3600000 * 12)
                        ).toString().toInt()
                    )
                }
                val (leftTwoDigM, ltdM) = remember {
                    mutableStateOf(
                        DateFormat.format("mm", date.time).toString().toInt()
                    )
                }
                val (rightTwoDig, rtd) = remember {
                    mutableStateOf(
                        DateFormat.format(
                            "hh",
                            (date.time + 3600000)
                        ).toString().toInt()
                    )
                }
                val (rightTwoDigM, rtdM) = remember {
                    mutableStateOf(
                        DateFormat.format(
                            "mm",
                            (date.time)
                        ).toString().toInt()
                    )
                }

                val context = LocalContext.current
                Column(
                    modifier = Modifier
                        .padding(vertical = 24.dp),
                ) {
                    Text(text = monthString + " " + year)
                    Row(
                        horizontalArrangement = Arrangement.Center
                    ) {
                        LazyRow() {
                            items(listOf<Int>(0, 1, 2, 3, 4, 5, 6)) { num ->
                                WeekDayItem(
                                    weekDay = DateFormat.format(
                                        "EE",
                                        date.time + 3600000 * 12 + day * num
                                    ) as String,
                                    date = DateFormat.format("dd", date.time + day * num) as String,
                                    active = num == dayOfWeek.value,
                                    onClick = {
                                        dayOfWeek.value = num
                                    }
                                )
                            }
                        }
                    }
                    Row(
                        modifier = Modifier
                            .padding()
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(0.5f, false)
                                .padding(end = 8.dp)
                        ) {

                            Text(
                                modifier = Modifier.padding(top = 24.dp),
                                text = stringResource(R.string.timeStart)
                            )
                            Clock(
                                modifier = Modifier.padding(top = 10.dp),
                                leftTwoDig,
                                ltd,
                                leftTwoDigM,
                                ltdM
                            )
                        }
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(0.5f, false)
                                .padding(start = 8.dp)
                        ) {
                            Text(
                                modifier = Modifier.padding(top = 24.dp),

                                text = stringResource(R.string.timeEnd)
                            )
                            Clock(
                                modifier = Modifier.padding(top = 10.dp),
                                rightTwoDig,
                                rtd,
                                rightTwoDigM,
                                rtdM
                            )
                        }
                    }
                    Row(
                        modifier = Modifier
                            .padding(top = 30.dp)
                    ) {

                        LineOfTimeWithSelect(
                            startSelect = (leftTwoDig * 60 + leftTwoDigM) / (24 * 60f),
                            endSelect = (rightTwoDig * 60 + rightTwoDigM) / (24 * 60f),
                        )
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 3.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "00:00"
                        )
                        Text(
                            text = "06:00"
                        )
                        Text(
                            text = "12:00"
                        )
                        Text(
                            text = "18:00"
                        )
                        Text(
                            text = "23:59"
                        )
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxHeight(),
                        verticalArrangement = Arrangement.Bottom,
                    ) {

                        Row {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(0.5f, false)
                                    .padding(end = 8.dp)
                            ) {
                                ButtonOutlined(
                                    text = stringResource(R.string.Cancel),
                                    onClick = {
                                        navController.popBackStack()
                                    }
                                )
                            }
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(0.5f, false)
                                    .padding(start = 8.dp)
                            ) {
                                ButtonFill(
                                    text = stringResource(R.string.Book),
                                    colorFill = White,
                                    onClick = {
                                        bookingViewModel.beginTime.value = DateFormat.format(
                                            "yyyy-MM-dd'T'HH:mm:ss+$tz:00",
                                            date.time - (date.time % day) - zchislo * hour + day * (dayOfWeek.value) + (leftTwoDig * 60 + leftTwoDigM) * sec
                                        ) as String
                                        bookingViewModel.endTime.value = DateFormat.format(
                                            "yyyy-MM-dd'T'HH:mm:ss+$tz:00",
                                            date.time - (date.time % day) - zchislo * hour + day * (dayOfWeek.value) + (rightTwoDig * 60 + rightTwoDigM) * sec
                                        ) as String
                                        Log.d("TIMEZONE", bookingViewModel.beginTime.value)
                                        bookingViewModel.createBook()

                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}