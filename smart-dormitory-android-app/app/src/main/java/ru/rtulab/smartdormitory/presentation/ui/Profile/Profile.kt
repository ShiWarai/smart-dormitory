package ru.rtulab.smartdormitory.presentation.ui.Profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.request.ImageRequest
import ru.rtulab.smartdormitory.R
import ru.rtulab.smartdormitory.presentation.ui.common.H1
import ru.rtulab.smartdormitory.presentation.ui.common.LoadingIndicator
import ru.rtulab.smartdormitory.presentation.ui.common.ObjectCardWithDate
import ru.rtulab.smartdormitory.presentation.viewmodel.singletonViewModel
import ru.rtulab.smartdormitory.ui.theme.White
import ru.rtulab.smartdormitory.ui.theme.White50

@Preview
@Composable
fun Profile(
    profileViewModel: ProfileViewModel = singletonViewModel()
){
    val profileDto = profileViewModel.profileResourceFlow.collectAsState().value
    val login ="19И1337"
    val room = "228"
    val role = "Житель"
    val lastname = "LastName"
    val firstname = "FirstName"

    val imageLink =""

    val array = listOf<Nothing>()
    profileDto.handle(
        onLoading = {
            LoadingIndicator()
        },
        onError = { msg ->
            Text(text = msg)
        },
        onSuccess = { meDto ->
            val me = meDto.toProfile()
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AsyncImage(
                    modifier = Modifier
                        .clip(RoundedCornerShape(100.dp))
                        .width(96.dp)
                        .height(96.dp)
                        .padding(top = 32.dp),
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(imageLink)
                        .crossfade(true)
                        .build(),
                    contentDescription = stringResource(R.string.avatar),
                    contentScale = ContentScale.FillBounds,
                    placeholder = painterResource(R.drawable.placeholderimage),

                    )
                Row {
                    H1(
                        Modifier.padding(top = 8.dp),
                        text = me.lastName
                    )
                }
                Row {
                    H1(
                        Modifier.padding(top = 8.dp),
                        text = me.firstName
                    )
                }
                Row(
                    Modifier.padding(top = 24.dp),
                ) {
                    Column() {
                        Text(
                            text = stringResource(R.string.BaseInfo),
                            fontSize = 16.sp,
                            lineHeight = 24.sp,
                            color = White

                        )
                    }
                    Column(
                        Modifier.padding(start = 6.dp)
                    ) {
                        Image(
                            painter = painterResource(R.drawable.help),
                            contentDescription = stringResource(R.string.help)
                        )
                    }

                }
                Row(
                    Modifier.padding(top = 16.dp),
                    horizontalArrangement = Arrangement.Center

                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally

                    ) {
                        Text(
                            text = stringResource(R.string.login),
                            fontSize = 14.sp,
                            lineHeight = 20.sp,
                            color = White50,
                            fontWeight = FontWeight.Medium

                        )
                        Text(
                            text = me.login,
                            fontSize = 16.sp,
                            lineHeight = 24.sp,
                            color = White
                        )
                    }
                }
                Row(
                    Modifier.padding(top = 8.dp),
                    horizontalArrangement = Arrangement.Center

                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally

                    ) {
                        Text(
                            text = stringResource(R.string.Room),
                            fontSize = 14.sp,
                            lineHeight = 20.sp,
                            color = White50,
                            fontWeight = FontWeight.Medium

                        )
                        Text(
                            text = me.room,
                            fontSize = 16.sp,
                            lineHeight = 24.sp,
                            color = White
                        )
                    }
                }
                Row(
                    Modifier.padding(top = 8.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally

                    ) {
                        Text(
                            text = stringResource(R.string.Role),
                            fontSize = 14.sp,
                            lineHeight = 20.sp,
                            color = White50,
                            fontWeight = FontWeight.Medium
                        )
                        Text(
                            text = me.role,
                            fontSize = 16.sp,
                            lineHeight = 24.sp,
                            color = White
                        )
                    }
                }
                /*Row(
                    Modifier.padding(top = 24.dp),
                ) {
                    Column() {
                        Text(
                            text = stringResource(R.string.Bookings),
                            fontSize = 16.sp,
                            lineHeight = 24.sp,
                            color = White

                        )
                    }

                }*/
                /*LazyColumn() {
                    items(array) {
                        ObjectCardWithDate()
                    }
                }*/
            }
        }
    )
}