package com.example.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.domain.model.Recipe

@Database(entities = [Recipe::class], version = 1, exportSchema = false)
abstract class RecipeDatabase: RoomDatabase() {
    abstract fun recipeDao(): RecipeDao
}