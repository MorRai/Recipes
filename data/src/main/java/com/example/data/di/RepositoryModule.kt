package com.example.data.di

import com.example.data.repository.RecipesRepositoryImpl
import com.example.domain.repository.RecipesRepository
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val productRepositoryModule = module {
    singleOf(::RecipesRepositoryImpl){bind<RecipesRepository>() }
}