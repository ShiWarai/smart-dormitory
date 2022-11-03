package ru.rtulab.smartdormitory.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

@Module
@InstallIn(SingletonComponent::class)
object RepositoriesModule {

    @Provides
    fun provideRepositoryCoroutineScope() =
        CoroutineScope(Dispatchers.IO + SupervisorJob())
}