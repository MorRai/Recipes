package com.example.data.di

import androidx.room.Room
import com.example.data.database.RecipeDatabase
import org.koin.dsl.module

val recipeDatabaseModule = module{

    single {
        Room.databaseBuilder(
            get(),
            RecipeDatabase::class.java,
            "recipe_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }
}