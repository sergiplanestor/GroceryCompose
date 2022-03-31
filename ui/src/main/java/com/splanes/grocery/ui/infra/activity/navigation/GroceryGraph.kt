package com.splanes.grocery.ui.infra.activity.navigation

import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.Navigator

object GroceryGraph {

    class Route internal constructor() {
        val Auth = "Grocery/Auth"
        val Dashboard = "Grocery/Dashboard"

        val Start = Auth
    }

    fun NavHostController.redirect(
        navOptions: NavOptions? = null,
        navigatorExtras: Navigator.Extras? = null,
        block: Route.() -> String
    ) = navigate(block(Route()), navOptions, navigatorExtras)

}

fun navGraphRoute(block: GroceryGraph.Route.() -> String): String = block(GroceryGraph.Route())