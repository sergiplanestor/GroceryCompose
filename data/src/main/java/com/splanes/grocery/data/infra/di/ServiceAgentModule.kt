package com.splanes.grocery.data.infra.di

import com.splanes.grocery.data.feature.auth.AuthServiceAgentImpl
import com.splanes.grocery.data.feature.location.PlacesServiceAgentImpl
import com.splanes.grocery.domain.feature.auth.AuthServiceAgent
import com.splanes.grocery.domain.feature.location.agent.PlacesServiceAgent
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ServiceAgentModule {

    @Binds
    abstract fun bindAuthServiceAgent(impl: AuthServiceAgentImpl): AuthServiceAgent

    @Binds
    abstract fun bindPlacesServiceAgent(impl: PlacesServiceAgentImpl): PlacesServiceAgent
}