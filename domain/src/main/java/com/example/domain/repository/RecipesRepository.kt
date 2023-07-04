package com.example.domain.repository


import androidx.paging.PagingData
import com.example.domain.model.Recipe
import kotlinx.coroutines.flow.Flow

interface RecipesRepository {
    fun getRecipes(): Flow<PagingData<Recipe>>
}