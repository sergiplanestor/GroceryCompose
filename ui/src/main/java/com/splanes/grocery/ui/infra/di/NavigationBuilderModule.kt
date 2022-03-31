package com.splanes.grocery.ui.infra.di

import com.splanes.grocery.ui.feature.auth.navigation.builder.AuthNavigationBuilder
import com.splanes.grocery.ui.feature.dashboard.navigation.builder.DashboardNavigationBuilder
import com.splanes.grocery.ui.infra.navigation.builder.NavigationBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
object NavigationBuilderModule {

    @Provides
    fun provideGroceryNavigationBuilders(
        auth: AuthNavigationBuilder,
        dashboard: DashboardNavigationBuilder,
    ): List<NavigationBuilder> = listOf(
        auth,
        dashboard
    )

}