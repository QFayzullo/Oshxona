package uz.fayzullo.oshxona.presentation.home

import uz.fayzullo.oshxona.domain.Category
import uz.fayzullo.oshxona.domain.Recipe
import uz.fayzullo.oshxona.domain.RecipeDetail
import uz.fayzullo.oshxona.presentation.base.UiEffect
import uz.fayzullo.oshxona.presentation.base.UiEvent
import uz.fayzullo.oshxona.presentation.base.UiState

class HomeContract {

    data class State(
        val searchQuery: String = "",
        val isLoading: Boolean = false,
        val categories: List<Category> = emptyList(),
        val selectedCategoryKey: String? = null,
        val recipes: List<Recipe> = emptyList(),
        val randomRecipe: RecipeDetail? = null,
        val error: String? = null
    ) : UiState

    sealed interface Event : UiEvent {
        data class OnSearchQueryChanged(val query: String) : Event
        data object OnRefresh : Event
        data class OnCategorySelect(val categoryKey: String?) : Event
        data class OnRecipeClick(val recipeId: Int) : Event
        data object OnRandomRecipeClick : Event
    }

    sealed interface Effect : UiEffect {
        data class NavigateToDetails(val recipeId: Int) : Effect
        data class ShowToast(val message: String) : Effect
    }
}