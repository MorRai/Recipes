package com.example.recipes

import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.domain.model.Recipe
import com.example.domain.model.Response
import com.example.recipes.ui.recipe_details.RecipeDetailViewModel
import org.koin.androidx.compose.koinViewModel


@Composable
fun RecipeDetailScreen(
    recipeId: Int,
    navigateBack: () -> Unit
) {
    val viewModel = koinViewModel<RecipeDetailViewModel>()
    LaunchedEffect(recipeId) {
        viewModel.loadRecipeById(recipeId)
    }
    val recipeState = viewModel.recipeFlow.collectAsState()

    when (recipeState.value) {
        is Response.Loading -> {
            // Отображение состояния загрузки
            // Например, показываем ProgressBar
            CircularProgressIndicator()
        }
        is Response.Success -> {
            val recipe = (recipeState.value as Response.Success<Recipe>).data
                RecipeDetailsContent(recipe)
        }
        is Response.Failure -> {
            val errorMessage = (recipeState.value as Response.Failure).e.message ?: "Unknown error"
            // Отображение ошибки
            Text("Error: $errorMessage")
        }
    }
}




@Composable
fun RecipeDetailsContent(
    recipe: Recipe,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = recipe.image,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.height(150.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = recipe.name, style = MaterialTheme.typography.bodyLarge)
        // Continue with other details of the recipe if available
    }
    }



