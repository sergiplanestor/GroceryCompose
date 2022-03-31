package com.splanes.grocery.ui.feature.auth.navigation.builder

import androidx.navigation.NavHostController
import com.splanes.grocery.ui.feature.auth.component.GroceryAuthComponent
import com.splanes.grocery.ui.infra.activity.navigation.GroceryGraph.redirect
import com.splanes.grocery.ui.infra.activity.navigation.navGraphRoute
import com.splanes.grocery.ui.infra.navigation.builder.NavigationBuilder
import com.splanes.toolkit.compose.ui.components.feature.navhost.graph.NavGraphDestination
import com.splanes.toolkit.compose.ui.components.feature.navhost.graph.transition.NavGraphTransition
import javax.inject.Inject

class AuthNavigationBuilder @Inject constructor() : NavigationBuilder {

    override fun build(navController: NavHostController): NavGraphDestination =
        NavGraphDestination(
            route = navGraphRoute { Auth },
            transitions = NavGraphTransition.Fade(),
            builder = { GroceryAuthComponent { navController.redirect { Dashboard } } }
        )
}