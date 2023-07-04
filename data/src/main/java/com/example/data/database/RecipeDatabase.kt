package com.example.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.model.RecipeEntity
import com.example.data.model.RemoteKeys

@Database(entities = [RecipeEntity::class, RemoteKeys::class], version = 1, exportSchema = false)
abstract class RecipeDatabase: RoomDatabase() {
    abstract fun recipeDao(): RecipeDao
    abstract fun remoteKeysDao(): RemoteKeysDao
}