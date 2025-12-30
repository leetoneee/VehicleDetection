package com.bteamcoding.vehicledetection.feature_result.di

import com.bteamcoding.vehicledetection.feature_result.data.repository.DetectionRepositoryImpl
import com.bteamcoding.vehicledetection.feature_result.domain.repository.DetectionRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DetectionModule {
    @Binds
    @Singleton
    abstract fun bindDetectionRepository(
        impl: DetectionRepositoryImpl
    ) : DetectionRepository
}