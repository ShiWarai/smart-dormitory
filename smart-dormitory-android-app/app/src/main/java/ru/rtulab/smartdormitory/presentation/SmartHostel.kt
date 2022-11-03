package ru.rtulab.smartdormitory.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import ru.rtulab.smartdormitory.presentation.navigation.AppScreen
import ru.rtulab.smartdormitory.presentation.navigation.LocalNavController
import ru.rtulab.smartdormitory.presentation.navigation.NavigationGraph
import ru.rtulab.smartdormitory.presentation.ui.Profile.ProfileViewModel
import ru.rtulab.smartdormitory.presentation.ui.common.BottomSheet
import ru.rtulab.smartdormitory.presentation.ui.common.header.BasicTopAppBar
import ru.rtulab.smartdormitory.presentation.ui.common.bottomsheet.BottomSheetViewModel
import ru.rtulab.smartdormitory.presentation.ui.common.burgermenu.BurgerMenuViewModel
import ru.rtulab.smartdormitory.presentation.ui.common.sharedElements.LocalSharedElementsRootScope
import ru.rtulab.smartdormitory.presentation.ui.common.sharedElements.SharedElementsRoot
import ru.rtulab.smartdormitory.presentation.ui.common.topAppBar.AppBarViewModel
import ru.rtulab.smartdormitory.presentation.ui.common.topAppBar.AppTabsViewModel
import ru.rtulab.smartdormitory.presentation.viewmodel.singletonViewModel
import ru.rtulab.smartdormitory.ui.theme.Accent
import ru.rtulab.smartdormitory.ui.theme.White
import ru.rtulab.smartdormitory.ui.theme.White50

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SmartDormitory(
    appBarViewModel: AppBarViewModel = singletonViewModel(),
    appTabsViewModel: AppTabsViewModel = singletonViewModel(),
    bottomSheetViewModel: BottomSheetViewModel = singletonViewModel(),
    burgerMenuViewModel: BurgerMenuViewModel = singletonViewModel(),
){

    val currentScreen by appBarViewModel.currentScreen.collectAsState()

    val navController = LocalNavController.current

    var sharedElementScope = LocalSharedElementsRootScope.current

    val onBackAction: () -> Unit = {
        if (sharedElementScope?.isRunningTransition == false)
            if (!navController.popBackStack()) appBarViewModel.handleDeepLinkPop()
    }

    LaunchedEffect(bottomSheetViewModel.bottomSheetState.currentValue) {
        if (bottomSheetViewModel.bottomSheetState.currentValue == ModalBottomSheetValue.Hidden)
            bottomSheetViewModel.hide(this)
    }
    LaunchedEffect(burgerMenuViewModel.bottomSheetState) {
        if (!burgerMenuViewModel.bottomSheetState)
            burgerMenuViewModel.bottomSheetState =!burgerMenuViewModel.bottomSheetState
    }

    val tabs = appTabsViewModel.appTabs.collectAsState().value

    ModalBottomSheetLayout(
        sheetState = bottomSheetViewModel.bottomSheetState,//заменить
        sheetContent = {
            BottomSheet()
        },
        sheetShape = RoundedCornerShape(
            topStart = 16.dp,
            topEnd = 16.dp
        ),
        sheetBackgroundColor = Accent,

    ) {
        Scaffold(
            modifier = if(bottomSheetViewModel.visibilityAsState.collectAsState().value) Modifier.blur(50.dp) else Modifier.blur(0.dp),
            drawerContent = {

            },
            topBar ={
                when (currentScreen) {
                    AppScreen.BookingCreate -> BasicTopAppBar(
                        backgroundColor = Accent,
                        text = stringResource(currentScreen.screenNameResource),
                        //onBackAction = onBackAction
                    )
                    AppScreen.BookingCreateSecond -> BasicTopAppBar(
                        backgroundColor = Accent,
                        text = stringResource(currentScreen.screenNameResource),
                        //onBackAction = onBackAction
                    )
                    else -> BasicTopAppBar(
                        text = stringResource(currentScreen.screenNameResource),
                        //onBackAction = onBackAction
                    )
                }

            },
            content = {
                Box(
                    modifier = Modifier.padding(
                        bottom = it.calculateBottomPadding(),
                        top = it.calculateTopPadding()
                    )
                ) {
                    SharedElementsRoot {
                        sharedElementScope = LocalSharedElementsRootScope.current
                        NavigationGraph(navController)
                    }
                }
            },
            bottomBar = {

                val currentTab by appBarViewModel.currentTab.collectAsState()

                BottomNavigation(
                    backgroundColor = Accent,
                    contentColor = White
                ) {
                    tabs.forEach { tab ->

                        val navBackStackEntry by navController.currentBackStackEntryAsState()
                        val currentDestination = navBackStackEntry?.destination

                        BottomNavigationItem(
                            icon = {Icon( painter = painterResource(tab.icon), contentDescription = tab.route) },
                            selectedContentColor = White,
                            unselectedContentColor = White50,
                            alwaysShowLabel = false,
                            selected = currentDestination?.hierarchy?.any { it.route == tab.route } == true,
                            onClick = {
                                // As per https://stackoverflow.com/questions/71789903/does-navoptionsbuilder-launchsingletop-work-with-nested-navigation-graphs-in-jet,

                                // it seems to not be possible to have all three of multiple back stacks, resetting tabs and single top behavior at once by the means
                                // of Jetpack Navigation APIs, but only two of the above.
                                // This code provides resetting and singleTop behavior for the default tab.
                                if (tab == currentTab) {
                                    navController.popBackStack(
                                        route = tab.startDestination,
                                        inclusive = false
                                    )
                                    return@BottomNavigationItem
                                }
                                // This code always leaves default tab's start destination on the bottom of the stack. Workaround needed?
                                navController.navigate(tab.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true

                                    // We want to reset the graph if it is clicked while already selected
                                    restoreState = tab != currentTab
                                }
                                appBarViewModel.setCurrentTab(tab)
                            }
                        )
                    }
                }
            }
        )
    }
}