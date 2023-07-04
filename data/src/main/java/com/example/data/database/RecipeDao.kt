package com.example.data.database

import androidx.paging.PagingSource
import androidx.room.*
import com.example.data.model.RecipeEntity

@Dao
interface RecipeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(recipes: List<RecipeEntity>)

    @Update
    suspend fun update(recipe: RecipeEntity)

    @Delete
    suspend fun delete(recipe: RecipeEntity)

    @Query("SELECT * from recipe_database WHERE id = :id")
    suspend fun getRecipe(id: Int): RecipeEntity

    @Query("SELECT * from recipe_database order by name asc")
    fun getRecipes(): PagingSource<Int, RecipeEntity>

    @Query("Delete From recipe_database")
    suspend fun clearAllRecipes()

}