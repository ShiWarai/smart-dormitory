package ru.rtulab.smartdormitory.presentation.ui.booking

import androidx.compose.foundation.layout.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.rtulab.smartdormitory.R
import ru.rtulab.smartdormitory.presentation.navigation.AppScreen
import ru.rtulab.smartdormitory.presentation.navigation.LocalNavController
import ru.rtulab.smartdormitory.presentation.ui.common.*
import ru.rtulab.smartdormitory.presentation.ui.objects.ObjectViewModel
import ru.rtulab.smartdormitory.presentation.viewmodel.singletonViewModel
import ru.rtulab.smartdormitory.ui.theme.Accent
import ru.rtulab.smartdormitory.ui.theme.White

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BookingCreate(
    bookingViewModel: BookingViewModel = singletonViewModel(),
    objectViewModel: ObjectViewModel = singletonViewModel()
) {
    val types = objectViewModel.objectTypesResourceFlow.collectAsState().value

    val objdto = objectViewModel.objectsResourceFlow.collectAsState().value

    val navController = LocalNavController.current

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
                text = stringResource(R.string.ChoseObject)
            )
            types.handle(
                onLoading = {
                    LoadingIndicator()
                },
                onError = { msg ->
                    Text(text = msg)
                },
                onSuccess = { ts ->
                    var (mSelectedType, selType) = remember { mutableStateOf(ts[0].name) }


                    DropDown(
                        label = stringResource(R.string.type),
                        array = ts.map { t -> t.name },
                        seltext = mSelectedType,
                        selTextfun = selType
                    )
                    objdto.handle(
                        onLoading = {
                            LoadingIndicator()
                        },
                        onError = { msg ->
                            Text(text = msg)
                        },
                        onSuccess = { obj ->
                            var (mSelectedObj, selObj) = remember { mutableStateOf(obj.filter { it.typeId == (ts.find { t -> t.name == mSelectedType }!!.id) }[0].name) }

                            val typeObj =
                                obj.filter { o -> o.typeId == (ts.find { it.name == mSelectedType }!!.id) }
                            DropDown(
                                label = stringResource(R.string.objects),
                                array = typeObj.map { t -> t.name },
                                seltext = mSelectedObj,
                                selTextfun = selObj
                            )
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
                                            text = stringResource(R.string.Continue),
                                            colorFill = White,
                                            onClick = {
                                                bookingViewModel.cachedObjId.value =
                                                    obj.find { it.name == mSelectedObj }!!.id
                                                navController.navigate("${AppScreen.BookingCreateSecond.navLink}")
                                            }
                                        )
                                    }
                                }
                            }
                        }
                    )
                }
            )
        }
    }
}