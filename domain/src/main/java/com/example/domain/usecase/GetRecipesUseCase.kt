package com.example.domain.usecase

import androidx.paging.PagingData
import com.example.domain.model.Recipe

import com.example.domain.repository.RecipesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn


class GetRecipesUseCase(private val recipeRepository: RecipesRepository) {
     operator fun invoke() : Flow<PagingData<Recipe>> {
            return recipeRepository.getRecipes()
                .flowOn(Dispatchers.IO)
        }
}