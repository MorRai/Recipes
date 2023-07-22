package com.example.domain.usecase

import com.example.domain.model.Recipe
import com.example.domain.model.Response
import com.example.domain.repository.RecipesRepository

class GetRecipeDetailUseCase(private val recipeRepository: RecipesRepository) {
    suspend operator fun invoke(recipeId: Int): Response<Recipe> {
        return recipeRepository.getRecipeDetail(recipeId)
    }
}