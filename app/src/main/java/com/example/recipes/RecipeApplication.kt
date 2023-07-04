package com.example.recipes

import android.app.Application
import com.example.data.di.*
import com.example.recipes.koin.viewModelsModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class RecipeApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@RecipeApplication)
            modules(
                productRepositoryModule,
                viewModelsModule,
                useCaseModule,
                networkModule,
                recipeDatabaseModule,
                recipeMediatorModule
            )
        }
    }
}