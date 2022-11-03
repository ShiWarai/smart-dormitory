package ru.rtulab.smartdormitory.presentation.navigation

import ru.rtulab.smartdormitory.R

sealed class NavItem(var title:String, var icon:Int, var screen_route:String){

    object Home : NavItem("Home", R.drawable.home,"home")
    object Objects: NavItem("Objects",R.drawable.objects,"objects")
    object Booking: NavItem("Booking",R.drawable.booking,"booking")
    object Profile: NavItem("Profile",R.drawable.profile,"profile")
}
