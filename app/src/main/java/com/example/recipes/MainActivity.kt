package com.example.recipes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.recipes.ui.theme.CalorieCalculatorTheme
import com.example.domain.model.Response
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            CalorieCalculatorTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
    val viewModel = koinViewModel<RecipeListViewModel>()
    val recipeFlowState = viewModel.recipeFlow.collectAsState().value

    when (recipeFlowState) {
        is Response.Loading -> {
            // Отображение состояния загрузки
        }
        is Response.Success -> {
            val recipes = recipeFlowState.data
        }
        is Response.Failure -> {
            val errorMessage = recipeFlowState.e
            // Отображение ошибки
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CalorieCalculatorTheme {
        Greeting("Android")
    }
}