package com.example.data.repository

import androidx.paging.Pager
import androidx.paging.PagingData
import androidx.paging.map
import com.example.data.mapper.toDomainModels
import com.example.data.model.RecipeEntity
import com.example.domain.model.Recipe
import com.example.domain.repository.RecipesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class RecipesRepositoryImpl(private val recipePager: Pager<Int, RecipeEntity>): RecipesRepository {
    override fun getRecipes(): Flow<PagingData<Recipe>>{
        return recipePager.flow.map { pagingData ->
            pagingData.map { it.toDomainModels() }
        }
    }

}