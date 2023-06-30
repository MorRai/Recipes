package com.example.recipes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.Recipe
import com.example.domain.model.Response
import com.example.domain.usecase.GetRecipeUseCase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class RecipeListViewModel(getRecipeUseCase: GetRecipeUseCase) : ViewModel(){
    private val _recipeFlow = MutableStateFlow<Response<List<Recipe>>>(Response.Loading)
    val recipeFlow: StateFlow<Response<List<Recipe>>> = _recipeFlow.asStateFlow()

    init {
        viewModelScope.launch {
            _recipeFlow.value = Response.Loading

            val response = getRecipeUseCase.invoke()

            _recipeFlow.value = response
        }
    }

//    val recipeFlow = getRecipeUseCase.invoke()
//        .stateIn(
//            scope = viewModelScope,
//            started = SharingStarted.Eagerly,
//            initialValue = Response.Loading
//        )
}