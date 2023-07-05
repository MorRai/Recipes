package com.example.data.di

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.data.database.RecipeDatabase
import com.example.data.repository.RecipesRemoteMediator
import org.koin.dsl.module



val recipeMediatorModule = module {
    single{
        @OptIn(ExperimentalPagingApi::class)
       Pager(
                config = PagingConfig(pageSize = 20,prefetchDistance = 10,
                    initialLoadSize = 20,),
                pagingSourceFactory = { get<RecipeDatabase>().recipeDao().getRecipes() },
                remoteMediator = RecipesRemoteMediator(get(),get()),
            )

    }
}