package com.splanes.grocery.ui.infra.activity.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.splanes.toolkit.compose.ui.components.feature.navhost.component.AnimatedNavHost
import com.splanes.toolkit.compose.ui.components.feature.navhost.graph.NavGraphDestination
import com.splanes.toolkit.compose.ui.components.feature.navhost.graph.plusAssign
import com.splanes.toolkit.compose.ui.components.feature.navhost.graph.transition.NavGraphTransition

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun GroceryNavigatorComponent(
    destinations: (NavHostController) -> List<NavGraphDestination>
) {
    val navController = rememberAnimatedNavController()
    AnimatedNavHost(
        navController = navController,
        startDestinationRoute = navGraphRoute { Start },
        startDestinationTransition = NavGraphTransition.Fade(),
        builder = { destinations(navController).forEach { dest -> this += dest } }
    )
}