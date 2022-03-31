package com.splanes.grocery.data.infra.di

import com.splanes.grocery.data.common.storage.local.PreferencesDataSource
import com.splanes.grocery.data.common.storage.network.FirebaseDatabaseDataSource
import com.splanes.grocery.data.feature.user.datasource.UserLocalDataSource
import com.splanes.grocery.data.feature.user.datasource.UserNetworkDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    abstract fun bindsUserLocalDataSource(dataSource: PreferencesDataSource): UserLocalDataSource

    @Binds
    abstract fun bindsUserNetworkDataSource(dataSource: FirebaseDatabaseDataSource): UserNetworkDataSource

}