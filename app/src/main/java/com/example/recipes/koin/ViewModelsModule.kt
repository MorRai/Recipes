package com.example.recipes.koin

import com.example.recipes.ui.recipe_details.RecipeDetailViewModel
import com.example.recipes.ui.recipe_list.RecipeListViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val viewModelsModule = module {
    viewModelOf(::RecipeListViewModel)
    viewModelOf(::RecipeDetailViewModel)
}