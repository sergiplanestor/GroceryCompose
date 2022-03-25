package com.splanes.grocery.ui.feature.auth.activity

import androidx.compose.runtime.Composable
import com.splanes.grocery.ui.feature.auth.component.AuthActivityComponent
import com.splanes.toolkit.compose.base_arch.feature.presentation.activity.BaseComponentActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthActivity : BaseComponentActivity<AuthActivityViewModel>() {

    @Composable
    override fun ActivityContentComponent(activityViewModel: AuthActivityViewModel) {
        AuthActivityComponent()
    }
}

