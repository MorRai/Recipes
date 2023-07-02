package com.example.data.database

import androidx.room.*
import com.example.data.model.RecipeEntity

@Dao
interface  RecipeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(recipe: RecipeEntity)

    @Update
    suspend fun update(recipe: RecipeEntity)

    @Delete
    suspend fun delete(recipe: RecipeEntity)

    @Query("SELECT * from recipe_database WHERE id = :id")
    suspend fun getRecipe(id: Int): RecipeEntity

    @Query("SELECT * from recipe_database order by name asc")
    suspend fun getRecipes(): List<RecipeEntity>

}