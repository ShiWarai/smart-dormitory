package ru.rtulab.smartdormitory.presentation.ui.report

import android.text.format.DateFormat
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.rtulab.smartdormitory.R
import ru.rtulab.smartdormitory.data.remote.api.report.models.Report
import ru.rtulab.smartdormitory.ui.theme.Green
import ru.rtulab.smartdormitory.ui.theme.White50
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun ReportCard(
    report:Report,
    modifier:Modifier = Modifier,
    status:String,
    statusColor:Color = Green
){
    val date: Date = Calendar.getInstance().time

    val dayOfTheWeek = DateFormat.format("EE", date) as String // Th
    //val day = DateFormat.format("dd", date.time) as String // 20
    val monthString = DateFormat.format("MMM", date) as String // Jun
    val monthNumber = DateFormat.format("MM", date) as String // 06
    val year = DateFormat.format("yyyy", date) as String // 2022
report.run {

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
                        text = objectFull.name,
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
                    text = objectFull.type,
                    fontSize = 11.sp,
                    color = White50
                )
            }
            Row(
                modifier = Modifier.padding(top = 4.dp)
            ) {
                Text(
                    text = "Комната №"+objectFull.room,
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

                /*Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.5f, false),
                ) {
                    Row() {
                        Text(
                            text = stringResource(R.string.TimeOfSend),
                            fontSize = 11.sp
                        )
                    }
                    Row() {
                        Text(
                            text = dMYhm("2022-11-07-"),
                            fontSize = 14.sp
                        )
                    }
                }*/
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.5f, false),
                ) {
                    Row() {
                        Text(
                            text = stringResource(R.string.Sender),
                            fontSize = 11.sp
                        )
                    }
                    Row() {
                        Text(
                            text = resident.fio,
                            fontSize = 14.sp
                        )
                    }
                }

            }

        }
    }
}
fun dMYhm(date2:String):String{
    val simple = SimpleDateFormat("yyyy-MM-dd'T'kk:mm:ss.SSS+00:00")
    val date = simple.parse(date2)?.time!!

    val z: TimeZone = Calendar.getInstance().timeZone




    return DateFormat.format("dd", date) as String+"."+
            DateFormat.format("MM", date.toLong()) as String+"."+
            DateFormat.format("yyyy", date.toLong()) as String+" "+
            DateFormat.format("kk", date.toLong()+z.getRawOffset()) as String+":"+
            DateFormat.format("mm", date.toLong()) as String

}

}