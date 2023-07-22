package com.example.data.repository

import androidx.paging.Pager
import androidx.paging.PagingData
import androidx.paging.map
import com.example.data.api.RecipeApi
import com.example.data.database.RecipeDatabase
import com.example.data.mapper.toDomainModels
import com.example.data.model.RecipeEntity
import com.example.domain.model.Recipe
import com.example.domain.model.Response
import com.example.domain.repository.RecipesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class RecipesRepositoryImpl(
    private val recipePager: Pager<Int, RecipeEntity>,
    private val productService: RecipeApi,
    private val recipeDatabase: RecipeDatabase,
) : RecipesRepository {
    override fun getRecipes(): Flow<PagingData<Recipe>> {
        return recipePager.flow.map { pagingData ->
            pagingData.map { it.toDomainModels() }
        }
    }

    override suspend fun getRecipeDetail(recipeId: Int): Response<Recipe> {
        val recipeFromCache = recipeDatabase.recipeDao().getRecipe(recipeId)
        if (recipeFromCache != null) {
            return Response.Success(recipeFromCache.toDomainModels())
        }
        return try {
            val response = productService.getRecipe(recipeId)
            Response.Success(response.toDomainModels())
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }

}