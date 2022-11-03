package ru.rtulab.smartdormitory.presentation.ui.common

import android.text.format.DateFormat
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.rtulab.smartdormitory.R
import ru.rtulab.smartdormitory.ui.theme.White50
import java.text.SimpleDateFormat
import java.util.*

@Preview
@Composable
fun ObjectCardWithDate(
    modifier: Modifier = Modifier,
    name:String="Name",
    status:String ="Status",
    type:String = "Type",
    room:String = "Owner",
    startTime:String = "2022-10-24T15:37:00.000+00:00",
    endTime:String = "2023-10-24T15:37:00.000+00:00",
    statusColor:Color = MaterialTheme.colors.onSecondary
) {

    val date: Date = Calendar.getInstance().time
    /*val dayOfWeek: Int = currentTime.day
val Month: Int = currentTime.month
val Year: Int = currentTime.year
val weekday: String = DateFormatSymbols().shortWeekdays.get(dayOfWeek)*/

    val dayOfTheWeek = DateFormat.format("EE", date) as String // Th
    //val day = DateFormat.format("dd", date.time) as String // 20
    val monthString = DateFormat.format("MMM", date) as String // Jun
    val monthNumber = DateFormat.format("MM", date) as String // 06
    val year = DateFormat.format("yyyy", date) as String // 2022

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        shape = RoundedCornerShape(8.dp),
        backgroundColor = MaterialTheme.colors.secondary



    ) {
        Column(
            modifier = Modifier
                .padding(12.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically

            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.5f, false)
                ) {
                    Text(
                        text = name,
                        fontSize = 16.sp,
                        color = MaterialTheme.colors.onSecondary

                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.5f, false),
                    horizontalArrangement = Arrangement.End,
                ) {
                    Text(

                        text = status,
                        fontSize = 14.sp,
                        color = statusColor
                    )
                }
            }
            Row() {
                Text(
                    text = type,
                    fontSize = 11.sp,
                    color = White50
                )
            }
            Row(
                modifier = Modifier.padding(top = 4.dp)
            ) {
                Text(
                    text = room,
                    fontSize = 14.sp,
                    color = MaterialTheme.colors.onSecondary

                )
            }
            Divider(
                modifier = Modifier.padding(vertical = 8.dp)

            )
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.5f, false),
                ) {
                    Row() {
                        Text(
                            text = stringResource(R.string.timeStart),
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
                            text = stringResource(R.string.timeEnd),
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
        }

    }
}
fun dMYhm(date2:String):String{
    val simple =SimpleDateFormat("yyyy-MM-dd'T'kk:mm:ss.SSS+00:00")
    val date = simple.parse(date2)?.time!!

    val z:TimeZone = Calendar.getInstance().timeZone




    return DateFormat.format("dd", date) as String+"."+
            DateFormat.format("MM", date.toLong()) as String+"."+
            DateFormat.format("yyyy", date.toLong()) as String+" "+
            DateFormat.format("kk", date.toLong()+z.getRawOffset()) as String+":"+
            DateFormat.format("mm", date.toLong()) as String

}