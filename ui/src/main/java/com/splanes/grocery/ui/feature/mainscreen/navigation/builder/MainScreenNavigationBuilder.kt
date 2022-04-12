package com.splanes.grocery.ui.feature.mainscreen.navigation.builder

import androidx.navigation.NavHostController
import com.splanes.grocery.ui.feature.mainscreen.component.MainScreenComponent
import com.splanes.grocery.ui.infra.activity.navigation.navGraphRoute
import com.splanes.grocery.ui.infra.navigation.builder.NavigationBuilder
import com.splanes.toolkit.compose.ui.components.feature.navhost.graph.NavGraphDestination
import com.splanes.toolkit.compose.ui.components.feature.navhost.graph.transition.NavGraphTransition
import javax.inject.Inject

class MainScreenNavigationBuilder @Inject constructor() : NavigationBuilder {

    override fun build(navController: NavHostController): NavGraphDestination =
        NavGraphDestination(
            route = navGraphRoute { MainScreen },
            transitions = NavGraphTransition.PushUp(duration = 750),
            builder = { MainScreenComponent(navController) }
        )
}