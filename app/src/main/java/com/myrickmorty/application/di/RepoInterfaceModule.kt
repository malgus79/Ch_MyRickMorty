package com.myrickmorty.application.di

import com.myrickmorty.data.RepositoryImpl
import com.myrickmorty.domain.RepositoryCharacter
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepoInterfaceModule {
    @Binds
    abstract fun datasource(impl: RepositoryImpl): RepositoryCharacter
}