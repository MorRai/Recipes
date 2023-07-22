package com.example.recipes.ui.recipe_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.Recipe
import com.example.domain.model.Response
import com.example.domain.usecase.GetRecipeDetailUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RecipeDetailViewModel(private val getRecipeDetailUseCase: GetRecipeDetailUseCase): ViewModel() {
    private val _recipeFlow = MutableStateFlow<Response<Recipe>>(Response.Loading)
    val recipeFlow: StateFlow<Response<Recipe>> = _recipeFlow

    // Метод для загрузки рецепта по его ID
    fun loadRecipeById(recipeId: Int) {
        viewModelScope.launch {
            val recipe = getRecipeDetailUseCase(recipeId)
            _recipeFlow.value = recipe
        }
    }
}