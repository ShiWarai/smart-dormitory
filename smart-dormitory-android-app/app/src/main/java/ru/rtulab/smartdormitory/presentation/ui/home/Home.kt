package ru.rtulab.smartdormitory.presentation.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.rtulab.smartdormitory.R
import ru.rtulab.smartdormitory.presentation.ui.common.H1
import ru.rtulab.smartdormitory.presentation.ui.common.ObjectCard
import ru.rtulab.smartdormitory.presentation.ui.common.ObjectCardWithDate
import ru.rtulab.smartdormitory.presentation.ui.home.components.ImageDownloadCard

@Preview
@Composable
fun Home(){
    //val array = listOf<>()
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        H1(
            Modifier.padding(vertical = 16.dp),
            stringResource(R.string.LastBooking)
        )
        //LastBooking
        ObjectCardWithDate()

        ImageDownloadCard()

        H1(
            Modifier.padding(vertical = 16.dp),
            stringResource(R.string.SoonEnd))

        val arraySoonEnd = listOf<Nothing>()
        LazyColumn(){
            items(arraySoonEnd){ item ->
                ObjectCardWithDate()//item info

            }
        }

        H1(
            Modifier.padding(vertical = 16.dp),
            stringResource(R.string.RecentlyFree)
        )

        val arrayRecently = listOf<Nothing>()
        LazyColumn(
        ){
            items(arrayRecently){ item ->
                ObjectCard()//item info

            }
        }






    }
}