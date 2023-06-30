package com.example.recipes.koin

import com.example.recipes.RecipeListViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val viewModelsModule = module {
    viewModelOf(::RecipeListViewModel)
}