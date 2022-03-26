package com.splanes.grocery.ui.feature.auth.activity

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.splanes.grocery.ui.feature.auth.component.AuthActivityComponent
import com.splanes.grocery.ui.utils.resources.color
import com.splanes.toolkit.compose.base_arch.feature.presentation.activity.BaseComponentActivity
import com.splanes.toolkit.compose.ui.components.feature.scaffold.model.ScaffoldColors
import com.splanes.toolkit.compose.ui.components.feature.statusbar.model.StatusBarColors
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthActivity : BaseComponentActivity<AuthActivityViewModel>() {

    override val initialStatusBarColor: StatusBarColors
        @Composable
        get() = StatusBarColors(color { primaryContainer })

    override val activityViewModel: AuthActivityViewModel
        @Composable
        get() = hiltViewModel<AuthActivityViewModel>().apply {
            ready(
                initialStatusBarColor,
                ScaffoldColors(container = color { primaryContainer }, content = color { surface })
            )
        }

    @Composable
    override fun ActivityContentComponent(activityViewModel: AuthActivityViewModel) {
        AuthActivityComponent()
    }
}

