package com.example.recipes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.Recipe
import com.example.domain.model.Response
import com.example.domain.usecase.GetRecipeUseCase
import com.example.domain.usecase.GetRecipesDaoUseCase
import com.example.domain.usecase.SaveRecipesUseCase
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.internal.NopCollector.emit
import kotlinx.coroutines.launch

class RecipeListViewModel(private val getRecipeUseCase: GetRecipeUseCase,private val getRecipeDaoUseCase: GetRecipesDaoUseCase,private val saveRecipesUseCase: SaveRecipesUseCase) : ViewModel(){

    private var currentPage = 1
    private var isLoading = false

    private val loadRecipesFlow = MutableSharedFlow<Unit>(
        replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    private val _recipeFlow = MutableStateFlow<Response<List<Recipe>>>(Response.Loading)
    val recipeFlow: StateFlow<Response<List<Recipe>>> = _recipeFlow.asStateFlow()

    fun onLoadRecipes() {
        viewModelScope.launch {
            try {
                loadRecipesFlow.emit(Unit)
            } catch (e: Exception) {
                _recipeFlow.value = Response.Failure(e)
            }
        }
    }

    val recipesFlow =
         loadRecipesFlow
            .onEach {
                isLoading = true
                _recipeFlow.value = Response.Loading
            }
            .map {
                // Load the next page of pokemons and update the state accordingly
                getRecipeUseCase.invoke(currentPage)
                    .apply { isLoading = false }
                    .fold(
                        onSuccess = {
                            saveRecipesUseCase.invoke(it)
                            currentPage++
                            _recipeFlow.value = Response.Success(it)
                            it
                        },
                        onFailure = {
                            _recipeFlow.value = Response.Failure(Exception(it.message))
                            emptyList()
                        }
                    )
            }
            // Start by loading data from the database and then continue with new data from the API
            .onStart {
                onLoadRecipes()
                emit(getRecipeDaoUseCase.invoke())
            }
            // Deduplicate the list of pokemons by ID to avoid duplicates when combining the old and new data
            .scan(emptyList<Recipe>()) { accumulator, value ->
                val newElements = value.filter { newValue ->
                    !accumulator.any { it.id == newValue.id }
                }
                accumulator + newElements
            }
            // Only emit new data if it's different from the previous data
            .distinctUntilChanged()
            .shareIn(
                scope = viewModelScope,
                started = SharingStarted.Eagerly,
                replay = 1,
            ) .onEach { _recipeFlow.value = Response.Success(it) }






    /* val recipesFlow = recipeDataFlow()
         .shareIn(
             scope = viewModelScope,
             started = SharingStarted.Eagerly,
             replay = 1,
         )*/









   /* init {
        viewModelScope.launch {
            _recipeFlow.value = Response.Loading

            val response = getRecipeUseCase.invoke()

            _recipeFlow.value = response
        }
    }*/

//    val recipeFlow = getRecipeUseCase.invoke()
//        .stateIn(
//            scope = viewModelScope,
//            started = SharingStarted.Eagerly,
//            initialValue = Response.Loading
//        )
}