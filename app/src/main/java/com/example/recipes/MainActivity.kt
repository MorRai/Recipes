package com.example.recipes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.recipes.ui.theme.RecipesTheme
import androidx.compose.material3.*
import androidx.navigation.compose.rememberNavController
import com.example.recipes.navigation.NavGraph

@ExperimentalMaterial3Api
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RecipesTheme {
                NavGraph(
                    navController = rememberNavController()
                )
            }
        }
    }
}
