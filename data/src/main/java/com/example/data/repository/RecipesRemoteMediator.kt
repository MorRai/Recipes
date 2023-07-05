package com.example.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.data.api.RecipeApi
import com.example.data.database.RecipeDatabase
import com.example.data.mapper.toDomainModels
import com.example.data.mapper.toEntityModels
import com.example.data.model.RecipeEntity
import com.example.data.model.RemoteKeys
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalPagingApi::class)
class RecipesRemoteMediator(private val productService: RecipeApi, private val recipeDatabase: RecipeDatabase) : RemoteMediator<Int, RecipeEntity>() {


    val pageSize = 20

    override suspend fun initialize(): InitializeAction {
        val cacheTimeout = TimeUnit.MILLISECONDS.convert(1, TimeUnit.HOURS)

        return if (System.currentTimeMillis() - (recipeDatabase.remoteKeysDao().getCreationTime() ?: 0) < cacheTimeout) {
            //можно еще чек интеренета добаваить и не занулять если его нет
            // Cached data is up-to-date, so there is no need to re-fetch
            // from the network.
            InitializeAction.SKIP_INITIAL_REFRESH
        } else {
            // Need to refresh cached data from network; returning
            // LAUNCH_INITIAL_REFRESH here will also block RemoteMediator's
            // APPEND and PREPEND from running until REFRESH succeeds.
            InitializeAction.LAUNCH_INITIAL_REFRESH
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, RecipeEntity>): RemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                recipeDatabase.remoteKeysDao().getRemoteKeyByRecipeID(id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, RecipeEntity>): RemoteKeys? {
        return state.pages.firstOrNull {
            it.data.isNotEmpty()
        }?.data?.firstOrNull()?.let { movie ->
            recipeDatabase.remoteKeysDao().getRemoteKeyByRecipeID(movie.id)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, RecipeEntity>): RemoteKeys? {
        return state.pages.lastOrNull {
            it.data.isNotEmpty()
        }?.data?.lastOrNull()?.let { movie ->
            recipeDatabase.remoteKeysDao().getRemoteKeyByRecipeID(movie.id)
        }
    }


    override suspend fun load(loadType: LoadType, state: PagingState<Int, RecipeEntity>): MediatorResult {
        val page: Int = when (loadType) {
            LoadType.REFRESH -> {
                //New Query so clear the DB
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: 1
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                // If remoteKeys is null, that means the refresh result is not in the database yet.
                val prevKey = remoteKeys?.prevKey
                prevKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)

                // If remoteKeys is null, that means the refresh result is not in the database yet.
                // We can return Success with endOfPaginationReached = false because Paging
                // will call this method again if RemoteKeys becomes non-null.
                // If remoteKeys is NOT NULL but its nextKey is null, that means we've reached
                // the end of pagination for append.
                val nextKey = remoteKeys?.nextKey
                nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
            }
        }

        try {
            val recipes = productService.getProducts(from = (page-1)*pageSize, size = pageSize).results.toDomainModels()

            val endOfPaginationReached = recipes.isEmpty()

            recipeDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    recipeDatabase.remoteKeysDao().clearRemoteKeys()
                    recipeDatabase.recipeDao().clearAllRecipes()
                }
                val prevKey = if (page > 1) page - 1 else null
                val nextKey = if (endOfPaginationReached) null else page + 1
                val remoteKeys = recipes.map {
                    RemoteKeys(recipeID = it.id, prevKey = prevKey, currentPage = page, nextKey = nextKey)
                }

                recipeDatabase.remoteKeysDao().insertAll(remoteKeys)
                recipeDatabase.recipeDao().insert(recipes.toEntityModels())
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (error: IOException) {
            return MediatorResult.Error(error)
        } catch (error: HttpException) {
            return MediatorResult.Error(error)
        }

    }

}