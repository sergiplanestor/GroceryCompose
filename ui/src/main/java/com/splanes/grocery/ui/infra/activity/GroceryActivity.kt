package com.splanes.grocery.ui.infra.activity

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.splanes.grocery.ui.feature.auth.navigation.builder.AuthNavigationBuilder
import com.splanes.grocery.ui.feature.mainscreen.navigation.builder.MainScreenNavigationBuilder
import com.splanes.grocery.ui.infra.activity.navigation.GroceryNavigatorComponent
import com.splanes.grocery.ui.infra.navigation.builder.NavigationBuilder
import com.splanes.grocery.ui.utils.resources.palette
import com.splanes.toolkit.compose.base_arch.feature.presentation.activity.BaseComponentActivity
import com.splanes.toolkit.compose.ui.components.feature.scaffold.model.ScaffoldColors
import com.splanes.toolkit.compose.ui.components.feature.statusbar.model.StatusBarColors
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class GroceryActivity : BaseComponentActivity<GroceryActivityViewModel>() {

    @Inject
    lateinit var authNavigationBuilder: AuthNavigationBuilder

    @Inject
    lateinit var mainScreenNavigationBuilder: MainScreenNavigationBuilder

    private val navigationBuilders: List<NavigationBuilder> by lazy {
        listOf(
            authNavigationBuilder,
            mainScreenNavigationBuilder
        )
    }

    override val initialStatusBarColor: StatusBarColors
        @Composable
        get() = StatusBarColors(palette { primaryContainer })

    override val activityViewModel: GroceryActivityViewModel
        @Composable
        get() = hiltViewModel<GroceryActivityViewModel>().apply {
            ready(
                initialStatusBarColor,
                ScaffoldColors(container = palette { primaryContainer }, content = palette { surface })
            )
        }

    @Composable
    override fun ActivityContentComponent(activityViewModel: GroceryActivityViewModel) {
        GroceryNavigatorComponent { navController ->
            navigationBuilders.map { builder -> builder.build(navController) }
        }
    }

}