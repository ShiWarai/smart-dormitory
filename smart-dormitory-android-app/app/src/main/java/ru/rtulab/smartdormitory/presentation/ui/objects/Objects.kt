package ru.rtulab.smartdormitory.presentation.ui.objects

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
import ru.rtulab.smartdormitory.presentation.ui.common.ObjectCard
import ru.rtulab.smartdormitory.presentation.ui.common.SearchView
import ru.rtulab.smartdormitory.presentation.viewmodel.singletonViewModel
import ru.rtulab.smartdormitory.ui.theme.Green
import ru.rtulab.smartdormitory.ui.theme.Red

@OptIn(ExperimentalPagerApi::class)
@Preview
@Composable
fun Objects(
     objectViewModel: ObjectViewModel = singletonViewModel()
) {
    val navController = LocalNavController.current


    val objectsDto = objectViewModel.objectsResourceFlow.collectAsState().value
    val objs = objectViewModel.objectsFlow.collectAsState().value
    val pagerState = rememberPagerState()
    val typesResource = objectViewModel.objectTypesResourceFlow.collectAsState().value
    val roomsResource = objectViewModel.objectRoomsResourceFlow.collectAsState().value
    roomsResource.handle(
        onLoading = {
            LoadingIndicator()
        },
        onError = { msg ->
            Text(text = msg)
        },
        onSuccess = { rooms ->
    typesResource.handle(
        onLoading = {
            LoadingIndicator()
        },
        onError = { msg ->
            Text(text = msg)
        },
        onSuccess = { types ->

            val tabs = types.map { t -> t.name }

            var isRefreshing by remember { mutableStateOf(false) }

            val stringSearch = remember { mutableStateOf("") }


            Column(
                modifier = Modifier
                    .fillMaxSize(),
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
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalAlignment = Alignment.Top,
                    count = tabs.size,
                    state = pagerState,
                    itemSpacing = 1.dp

                ) { currentPage ->
                    SwipeRefresh(
                        modifier = Modifier.fillMaxSize(),
                        state = rememberSwipeRefreshState(isRefreshing),
                        onRefresh = {
                            objectViewModel.onRefresh()
                        }
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                        ) {

                            Row(
                                modifier = Modifier
                                    .padding(vertical = 24.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .weight(0.9f),
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    SideEffect {
                                        objectViewModel.onSearch(stringSearch.value)
                                    }
                                    SearchView(
                                        query = stringSearch.value,
                                        onQueryChange = {
                                            stringSearch.value = it
                                            objectViewModel.onSearch(stringSearch.value)
                                        },
                                        onClearQuery = { stringSearch.value = "" },
                                        searching = false,

                                        )
                                    DisposableEffect(Unit) {
                                        //focusRequester.requestFocus()
                                        onDispose {
                                            objectViewModel.onSearch("")
                                        }
                                    }
                                }
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .weight(0.1f),
                                    verticalArrangement = Arrangement.Center
                                ) {

                                    IconButton(
                                        modifier = Modifier
                                            .height(36.dp)
                                            .width(36.dp),
                                        onClick = { /*to Filter*/ }
                                    ) {
                                        Icon(
                                            painter = painterResource(R.drawable.filter),
                                            contentDescription = stringResource(R.string.filter),
                                            modifier = Modifier
                                        )
                                    }
                                }
                            }
                            Row(
                                modifier = Modifier
                                    .padding(bottom = 16.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = tabs[currentPage],
                                    color = MaterialTheme.colors.onBackground,
                                    fontSize = 14.sp,
                                    lineHeight = 20.sp
                                )
                            }
                            objectsDto.handle(
                                onLoading = {
                                    LoadingIndicator()
                                },
                                onError = { msg ->
                                    Text(text = msg)
                                },
                                onSuccess = { dto ->
                                    objectViewModel.onResourceSuccess(dto, types,rooms)
                                    LazyColumn(
                                        modifier = Modifier
                                            .fillMaxSize(),
                                    ) {
                                        items(objs) { o ->
                                            ObjectCard(
                                                modifier = Modifier
                                                    .padding(vertical = 4.dp)
                                                    .clickable {
                                                        navController.navigate("${AppScreen.ObjectDetails.navLink}/${o.id}")

                                                    },
                                                name = o.name,
                                                status = if (o.status == 200) stringResource(R.string.free) else stringResource(
                                                    R.string.busy
                                                ),
                                                type = o.type,
                                                room ="Комната №${o.room}",
                                                statusColor = if (o.status == 200) Green else Red
                                            )
                                        }
                                    }
                                }
                            )
                        }

                    }
                }
            }
        }
    )
        }
    )
}

