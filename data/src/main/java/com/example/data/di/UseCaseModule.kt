package com.example.data.di

import com.example.domain.usecase.GetRecipeDetailUseCase
import com.example.domain.usecase.GetRecipesUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val useCaseModule = module {
    factoryOf(::GetRecipesUseCase)
    factoryOf(::GetRecipeDetailUseCase)
}
