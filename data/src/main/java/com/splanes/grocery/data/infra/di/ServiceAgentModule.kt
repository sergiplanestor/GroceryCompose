package com.splanes.grocery.data.infra.di

import com.splanes.grocery.data.feature.auth.AuthServiceAgentImpl
import com.splanes.grocery.domain.feature.auth.AuthServiceAgent
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ServiceAgentModule {

    @Binds
    abstract fun bindAuthServiceAgent(impl: AuthServiceAgentImpl): AuthServiceAgent

}