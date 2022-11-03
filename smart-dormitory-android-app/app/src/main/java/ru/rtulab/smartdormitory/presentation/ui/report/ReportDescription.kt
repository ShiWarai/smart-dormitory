package ru.rtulab.smartdormitory.presentation.ui.report

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusOrder
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.runBlocking
import ru.rtulab.smartdormitory.R
import ru.rtulab.smartdormitory.presentation.navigation.AppScreen
import ru.rtulab.smartdormitory.presentation.navigation.LocalNavController
import ru.rtulab.smartdormitory.presentation.ui.common.*
import ru.rtulab.smartdormitory.presentation.viewmodel.singletonViewModel
import ru.rtulab.smartdormitory.ui.theme.Accent
import ru.rtulab.smartdormitory.ui.theme.White
import ru.rtulab.smartdormitory.ui.theme.White50

@Composable
fun ReportDescription(
    reportViewModel: ReportViewModel = singletonViewModel()
) {

    val navController = LocalNavController.current

    var description = remember { mutableStateOf("") }

    val focusRequester = remember { FocusRequester()}

    Scaffold(
        scaffoldState = rememberScaffoldState(snackbarHostState = reportViewModel.snackbarHostState)
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
                    text = stringResource(R.string.DescribeProblem)
                )
                LaunchedEffect(Unit ){
                    focusRequester.requestFocus()
                }
                OutlinedTextField(
                    value = description.value,
                    onValueChange = { description.value = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .padding(top = 24.dp)
                        .focusOrder(focusRequester),
                    placeholder = {
                        Text(
                            text = stringResource(R.string.DescribeYourProblem),
                            fontSize = 14.sp,
                            color = White50
                        )
                    },
                    label = {
                        Text(
                            text = stringResource(R.string.problem),
                            fontSize = 12.sp,
                            color = White50
                        )
                    },

                    shape = RoundedCornerShape(8.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        placeholderColor = White50,
                        backgroundColor = Color.Transparent,
                        textColor = White50,
                        cursorColor = White50,
                        focusedBorderColor = White50,
                        unfocusedBorderColor = White50
                    ),
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
                                text = stringResource(R.string.Send),
                                colorFill = White,
                                onClick = {
                                    reportViewModel._description.value = description.value
                                    reportViewModel.createReport()
                                }
                            )
                        }
                    }
                }

            }
        }
    }
}