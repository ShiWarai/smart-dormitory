package ru.rtulab.smartdormitory.presentation.navigation

import android.content.res.Resources
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navDeepLink
import androidx.navigation.navigation
import com.google.accompanist.pager.ExperimentalPagerApi
import ru.rtulab.smartdormitory.R
import ru.rtulab.smartdormitory.presentation.ui.Profile.Profile
import ru.rtulab.smartdormitory.presentation.ui.booking.Booking
import ru.rtulab.smartdormitory.presentation.ui.booking.BookingCreate
import ru.rtulab.smartdormitory.presentation.ui.booking.BookingCreateSecond
import ru.rtulab.smartdormitory.presentation.ui.booking.BookingDetails
import ru.rtulab.smartdormitory.presentation.ui.common.sharedElements.LocalSharedElementsRootScope
import ru.rtulab.smartdormitory.presentation.ui.common.topAppBar.AppBarViewModel
import ru.rtulab.smartdormitory.presentation.ui.home.Home
import ru.rtulab.smartdormitory.presentation.ui.objects.ObjectDetals
import ru.rtulab.smartdormitory.presentation.ui.objects.Objects
import ru.rtulab.smartdormitory.presentation.ui.report.ReportDescription
import ru.rtulab.smartdormitory.presentation.ui.report.Reports
import ru.rtulab.smartdormitory.presentation.viewmodel.hiltViewModel

@ExperimentalPagerApi
@Composable
fun NavigationGraph(
    navController: NavHostController,
    appBarViewModel: AppBarViewModel = viewModel(),
    ){

val allScreens = AppScreen.getAll(LocalContext.current)

    val resources = LocalContext.current.resources
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    LaunchedEffect(navBackStackEntry) {
        // If a deep link is opened from a killed state, nav host's back stack does not yet exist,
        // thus resulting in a NullPointerException.
        // Deep link will be handled on the next composition tree pass
        try {
            appBarViewModel.onNavigate(
                allScreens.find { it.route == navBackStackEntry?.destination?.route }!!,
                navController
            )

            // This condition is possible to be true if a system "Back" press was detected in a non-default tab,
            // navigating the user to app's start destination.
            // To correctly reflect that in bottom navigation, this code is needed
            if (navBackStackEntry?.destination?.route == appBarViewModel.defaultTab.startDestination)
                appBarViewModel.setCurrentTab(appBarViewModel.defaultTab)
        } catch (e: NullPointerException) {}
    }
    // Disabling system "Back" button during transition
    BackHandler(LocalSharedElementsRootScope.current!!.isRunningTransition) {}


    NavHost(
        navController = navController,
        startDestination = appBarViewModel.defaultTab.route){
        homeGraph(
            resources = resources
        )
        objectsGraph(
            resources = resources
        )
        bookingGraph(
            resources = resources
        )
        profileGraph(
            resources = resources
        )
        reportGraph(
            resources = resources
        )
    }
}

private fun NavGraphBuilder.homeGraph(
    resources:Resources
){
   navigation(
       startDestination = AppTab.Home.startDestination,
       route = AppTab.Home.route
   ) {
           composable(AppScreen.Home.route){
               Home()
           }
       }

}
private fun NavGraphBuilder.objectsGraph(
    resources:Resources
){
    navigation(
        startDestination = AppTab.Objects.startDestination,
        route = AppTab.Objects.route
    ) {
        composable(AppScreen.Objects.route){
            Objects(
            )
        }
        composable(
            route = AppScreen.ObjectDetails.route,
            deepLinks = listOf(
                navDeepLink {
                    uriPattern =
                        "https://"+ resources.getString(R.string.HOST_URI) +"/objects/{objectId}"
                }
            )
        ) {
            ObjectDetals(
                objectId = it.arguments?.getString("objectId")!!
            )
        }
    }

}
private fun NavGraphBuilder.bookingGraph(
    resources:Resources
){
    navigation(
        startDestination = AppTab.Booking.startDestination,
        route = AppTab.Booking.route
    ) {
        composable(AppScreen.Booking.route){
            Booking(

            )
        }
        composable(AppScreen.BookingCreate.route){
            BookingCreate()
        }
        composable(AppScreen.BookingCreateSecond.route){
            BookingCreateSecond()
        }
        composable(
            route = AppScreen.BookingDetails.route,
            deepLinks = listOf(
                navDeepLink {
                    uriPattern =
                        "https://"+"${resources.getString(R.string.HOST_URI)}/booking/{bookingId}"
                }
            )
        ) {

                BookingDetails(
                    bookingId = it.arguments?.getString("bookingId")!!
                )
        }
    }

}
private fun NavGraphBuilder.profileGraph(
    resources:Resources
){
    navigation(
        startDestination = AppTab.Profile.startDestination,
        route = AppTab.Profile.route
    ) {
        composable(AppScreen.Profile.route){
            Profile()
        }

    }

}
@ExperimentalPagerApi
private fun NavGraphBuilder.reportGraph(
    resources:Resources
){
    navigation(
        startDestination = AppTab.Reports.startDestination,
        route = AppTab.Reports.route
    ) {
        composable(AppScreen.Reports.route){
            Reports()
        }
        composable(AppScreen.ReportCreate.route){
            ReportDescription()
        }

    }

}