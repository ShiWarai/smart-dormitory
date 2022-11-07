package ru.rtulab.smartdormitory.presentation.ui.objects

import android.text.format.DateFormat
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.rtulab.smartdormitory.R
import ru.rtulab.smartdormitory.presentation.navigation.AppScreen
import ru.rtulab.smartdormitory.presentation.navigation.LocalNavController
import ru.rtulab.smartdormitory.presentation.ui.Profile.ProfileViewModel
import ru.rtulab.smartdormitory.presentation.ui.booking.BookingViewModel
import ru.rtulab.smartdormitory.presentation.ui.common.ButtonFill
import ru.rtulab.smartdormitory.presentation.ui.common.ButtonOutlined
import ru.rtulab.smartdormitory.presentation.ui.common.H1
import ru.rtulab.smartdormitory.presentation.ui.common.LoadingError
import ru.rtulab.smartdormitory.presentation.ui.dateTime.LineOfTime
import ru.rtulab.smartdormitory.presentation.ui.dateTime.WeekDayItem
import ru.rtulab.smartdormitory.presentation.ui.report.ReportViewModel
import ru.rtulab.smartdormitory.presentation.viewmodel.singletonViewModel
import ru.rtulab.smartdormitory.ui.theme.Accent
import ru.rtulab.smartdormitory.ui.theme.White
import ru.rtulab.smartdormitory.ui.theme.White50
import java.util.*

@Composable
fun ObjectDetals(
    reportViewModel: ReportViewModel = singletonViewModel(),
    profileViewModel: ProfileViewModel = singletonViewModel(),
    bookingViewModel: BookingViewModel = singletonViewModel(),
    objectViewModel: ObjectViewModel = singletonViewModel(),
    objectId:String
) {

    val objres = objectViewModel.objectsResourceFlow.collectAsState().value
    val objtypesres = objectViewModel.objectTypesResourceFlow.collectAsState().value
    val objroomsres = objectViewModel.objectRoomsResourceFlow.collectAsState().value

    val meres = profileViewModel.profileResourceFlow.collectAsState().value

    val navController = LocalNavController.current

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
                            Log.d("OBJECT",objectId)
                            Log.d("Objects",objectViewModel.onResourceSuccess(obj, types, rooms).toString())
                            var objok = objectViewModel.onResourceSuccess(obj, types, rooms).find { it.id == objectId }!!

                            objok.run {
                                Column(
                                    modifier = Modifier
                                        .padding(horizontal = 16.dp, vertical = 32.dp)
                                ) {
                                    H1(text = name)

                                    Text(
                                        text = stringResource(R.string.Busy),
                                        modifier = Modifier
                                            .padding(top = 24.dp),
                                        fontSize = 16.sp,
                                        lineHeight = 24.sp
                                    )
                                    val date: Date = Calendar.getInstance().time
                                    val dayOfTheWeek = DateFormat.format("EE", date) as String // Th
                                    //val day = DateFormat.format("dd", date.time) as String // 20
                                    val monthString =
                                        DateFormat.format("MMM", date) as String // Jun
                                    val monthNumber = DateFormat.format("MM", date) as String // 06
                                    val year = DateFormat.format("yyyy", date) as String // 2022

                                    val hh = DateFormat.format("hh", date) as String //

                                    val day = 86400000

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
                                            .fillMaxWidth()
                                            .padding(vertical = 12.dp),
                                    ) {
                                        Text(
                                            text = monthString + " " + year,
                                            fontSize = 14.sp,
                                            lineHeight = 20.sp
                                        )
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
                                                        date = DateFormat.format(
                                                            "dd",
                                                            date.time + day * num
                                                        ) as String,
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
                                                .padding(top = 30.dp)
                                        ) {

                                            LineOfTime()
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
                                    }
                                    H1(text = stringResource(R.string.BaseInfo))
                                    Row(
                                        modifier = Modifier
                                            .padding(top = 12.dp)
                                    ) {
                                        Column(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .weight(0.25f)
                                        ) {
                                            Text(
                                                text = stringResource(R.string.Room),
                                                color = White50,
                                                fontSize = 12.sp,
                                                lineHeight = 16.sp
                                            )
                                            Text(
                                                text = room,
                                                color = White,
                                                fontSize = 14.sp,
                                                lineHeight = 20.sp
                                            )
                                        }
                                        Column(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .weight(0.25f)
                                        ) {
                                            Text(
                                                text = stringResource(R.string.status),
                                                color = White50,
                                                fontSize = 12.sp,
                                                lineHeight = 16.sp
                                            )
                                            Text(
                                                text = status.toString(),
                                                color = White,
                                                fontSize = 14.sp,
                                                lineHeight = 20.sp
                                            )
                                        }
                                        Column(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .weight(0.5f)
                                        ) {
                                            Text(
                                                text = stringResource(R.string.typeObject),
                                                color = White50,
                                                fontSize = 12.sp,
                                                lineHeight = 16.sp
                                            )
                                            Text(
                                                text = type,
                                                color = White,
                                                fontSize = 14.sp,
                                                lineHeight = 20.sp
                                            )
                                        }
                                    }


                                    H1(
                                        modifier = Modifier
                                            .padding(top = 24.dp),
                                        text = stringResource(R.string.description)
                                    )
                                    Text(
                                        modifier = Modifier
                                            .padding(top = 12.dp),
                                        text = description,
                                        color = White,
                                        fontSize = 14.sp,
                                        lineHeight = 20.sp
                                    )
                                    Column(
                                        modifier = Modifier
                                            .fillMaxHeight(),
                                        verticalArrangement = Arrangement.Bottom
                                    ) {
                                        ButtonFill(
                                            text = when (status) {
                                                200 -> stringResource(R.string.Book)
                                                100 -> stringResource(R.string.gotoBook)
                                                else -> stringResource(R.string.SomethingWrong)
                                            },
                                            colorFill = Accent,
                                            onClick = {
                                                navController.navigate(AppScreen.BookingCreateSecond.navLink)
                                                bookingViewModel.cachedObjId.value = id.toInt()
                                            }
                                        )
                                        ButtonOutlined(
                                            text = stringResource(R.string.SayAboutCrash),
                                            colorBorder = Accent,
                                            onClick = {
                                                navController.navigate(AppScreen.ReportCreate.navLink)
                                                reportViewModel.objectId.value = id.toInt()
                                                meres.handle(
                                                    onSuccess = { me ->
                                                        reportViewModel.residentId.value = me.id

                                                    }
                                                )

                                            }
                                        )
                                    }


                                }
                            }
                        })
                })
        })
}