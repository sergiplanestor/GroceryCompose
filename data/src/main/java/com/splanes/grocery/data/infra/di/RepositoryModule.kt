package com.splanes.grocery.data.infra.di

import com.splanes.grocery.data.feature.user.repositoryimpl.UserRepositoryImpl
import com.splanes.grocery.domain.feature.user.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindsUserRepository(repository: UserRepositoryImpl): UserRepository

}