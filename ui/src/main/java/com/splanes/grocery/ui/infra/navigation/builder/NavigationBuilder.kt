package com.splanes.grocery.ui.infra.navigation.builder

import androidx.navigation.NavHostController
import com.splanes.toolkit.compose.ui.components.feature.navhost.graph.NavGraphDestination

interface NavigationBuilder {
    fun build(navController: NavHostController): NavGraphDestination
}