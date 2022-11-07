package ru.rtulab.smartdormitory.presentation.navigation

import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import androidx.annotation.StringRes
import androidx.compose.runtime.saveable.Saver
import androidx.core.os.bundleOf
import kotlinx.parcelize.Parcelize
import ru.rtulab.smartdormitory.R

sealed class AppTab(
    val route: String,
    val startDestination: String,
    @StringRes val resourceId: Int,
    val icon: Int,
    var accessible: Boolean = true
) {
    object Home : AppTab("home_tab", AppScreen.Home.route, R.string.home, R.drawable.home)
    object Objects :
        AppTab("objects_tab", AppScreen.Objects.route, R.string.objects, R.drawable.objects)

    object Booking :
        AppTab("booking_tab", AppScreen.Booking.route, R.string.booking, R.drawable.booking)

    object Profile :
        AppTab("profile_tab", AppScreen.Profile.route, R.string.profile, R.drawable.profile)

    object Reports : AppTab(
        "reports_tab", AppScreen.Reports.route, R.string.reports, android.R.drawable.ic_dialog_alert
    )


    fun saveState() = bundleOf(SCREEN_KEY to route)

    fun asScreen() = when (this) {
        Home -> AppScreen.Home
        Objects -> AppScreen.Objects
        Booking -> AppScreen.Booking
        Profile -> AppScreen.Profile
        else -> AppScreen.Home
    }

    companion object {
        const val SCREEN_KEY = "SCREEN_KEY"

        val four
            get() = listOf(
                Home,
                Objects,
                Booking,
                Profile,
            )

        val all
            get() = listOf(
                Home,
                Objects,
                Booking,
                Profile,
                Reports
            )

        fun saver() = Saver<AppTab, Bundle>(
            save = { it.saveState() },
            restore = { restoreState(it) }
        )

        private fun restoreState(bundle: Bundle) = when (bundle.getString(SCREEN_KEY, null)) {
            Reports.route -> Reports
            Profile.route -> Profile
            Home.route -> Home
            Objects.route -> Objects
            Booking.route -> Booking
            else -> {
                throw IllegalArgumentException("Invalid route. Maybe you forgot to add a new screen to AppTabSaver.kt?")
            }
        }

    }
}

@Parcelize
open class AppScreen(
    @StringRes val screenNameResource: Int,
    val route: String,
    val navLink: String = route.substringBefore("/{")
) : Parcelable {
    object Home : AppScreen(R.string.home, "home")
    object Objects : AppScreen(R.string.objects, "objects")
    object Booking : AppScreen(R.string.booking, "booking")
    object Profile : AppScreen(R.string.profile, "profile")

    @Parcelize
    class ObjectDetails(val title: String) :
        AppScreen(R.string.object_details, "objects/{objectId}") { // Has back button
        companion object {
            const val route = "objects/{objectId}"
            val navLink: String = route.substringBefore("/{")
        }
    }

    @Parcelize
    class BookingDetails(val title: String) :
        AppScreen(R.string.booking_details, "booking/{bookingId}") { // Has back button
        companion object {
            const val route = "booking/{bookingId}"
            val navLink: String = route.substringBefore("/{")
        }
    }

    object BookingCreate : AppScreen(R.string.booking_new, "booking/new") // Has back button
    object BookingCreateSecond : AppScreen(R.string.booking_new, "booking/new/2") // Has back button

    object Reports : AppScreen(R.string.reports, "reports")
    object ReportCreate : AppScreen(R.string.report_new, "reports/new") // Has back button


    companion object {
        fun getAll(context: Context) = listOf(
            Home,
            Objects,
            Booking,
            Profile,
            ObjectDetails(context.resources.getString(R.string.object_details)),
            BookingDetails(context.resources.getString(R.string.booking_details)),
            BookingCreate,
            BookingCreateSecond,
            Reports,
            ReportCreate
        )
    }
}