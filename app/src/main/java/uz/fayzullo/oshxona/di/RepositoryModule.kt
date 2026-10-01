package uz.fayzullo.oshxona.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.fayzullo.oshxona.data.repository.OshxonaRepositoryImpl
import uz.fayzullo.oshxona.domain.repository.OshxonaRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindOshxonaRepository(
        impl: OshxonaRepositoryImpl
    ): OshxonaRepository
}