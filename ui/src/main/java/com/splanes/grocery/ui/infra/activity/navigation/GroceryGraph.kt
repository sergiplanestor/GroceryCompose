package com.splanes.grocery.ui.infra.activity.navigation

import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.Navigator
import androidx.navigation.navOptions

object GroceryGraph {

    class Route internal constructor() {
        val Auth = "Grocery/Auth"
        val MainScreen = "Grocery/Main/"

        val Start = Auth
    }

    fun NavHostController.redirect(
        options: (NavOptionsBuilder.() -> Unit)? = null,
        extras: Navigator.Extras? = null,
        block: Route.() -> String
    ) = navigate(block(Route()), options?.run(::navOptions), extras)

}

fun navGraphRoute(block: GroceryGraph.Route.() -> String): String = block(GroceryGraph.Route())