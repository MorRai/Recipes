package com.example.domain.repository


import androidx.paging.PagingData
import com.example.domain.model.Recipe
import com.example.domain.model.Response
import kotlinx.coroutines.flow.Flow

interface RecipesRepository {
    fun getRecipes(): Flow<PagingData<Recipe>>

    suspend fun getRecipeDetail(recipeId: Int): Response<Recipe>
}