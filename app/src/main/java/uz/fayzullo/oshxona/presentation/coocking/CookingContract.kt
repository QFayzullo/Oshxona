package uz.fayzullo.oshxona.presentation.coocking

import uz.fayzullo.oshxona.domain.RecipeDetail
import uz.fayzullo.oshxona.presentation.base.UiEffect
import uz.fayzullo.oshxona.presentation.base.UiEvent
import uz.fayzullo.oshxona.presentation.base.UiState

class CookingContract {
    data class State(
        val isLoading: Boolean = false,
        val recipe: RecipeDetail? = null,
        val error: String? = null
    ) : UiState

    sealed interface Event : UiEvent {
        data class LoadRecipe(val id: Int) : Event
    }

    sealed interface Effect : UiEffect
}