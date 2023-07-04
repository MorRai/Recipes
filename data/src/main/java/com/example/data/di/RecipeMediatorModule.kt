package com.example.data.di

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.data.api.RecipeApi
import com.example.data.database.RecipeDatabase
import com.example.data.model.RecipeEntity
import com.example.data.repository.RecipesRemoteMediator
import org.koin.dsl.module



val recipeMediatorModule = module {
    single{
        @OptIn(ExperimentalPagingApi::class)
        fun recipePager(
            recipeDatabase: RecipeDatabase,
            productService: RecipeApi,
        ): Pager<Int, RecipeEntity> {
            return Pager(
                config = PagingConfig(pageSize = 20,prefetchDistance = 10,
                    initialLoadSize = 20,),
                pagingSourceFactory = { recipeDatabase.recipeDao().getRecipes() },
                remoteMediator = RecipesRemoteMediator(productService,recipeDatabase),
            )
        }
    }
}