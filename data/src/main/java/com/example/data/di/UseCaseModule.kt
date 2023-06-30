package com.example.data.di

import com.example.domain.usecase.GetRecipeUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val useCaseModule = module {
    factoryOf(::GetRecipeUseCase)
}
