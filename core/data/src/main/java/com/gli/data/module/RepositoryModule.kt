package com.gli.data.module

import com.gli.data.repository.MovieRepository
import com.gli.data.repository.MovieRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

  @Binds
  abstract fun bindMovieRepository(impl: MovieRepositoryImpl): MovieRepository
}