package uz.fayzullo.oshxona.presentation.coocking

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import uz.fayzullo.oshxona.domain.repository.OshxonaRepository
import uz.fayzullo.oshxona.presentation.base.MviViewModel
import javax.inject.Inject

@HiltViewModel
class CookingViewModel @Inject constructor(
    private val repository: OshxonaRepository
) : ViewModel(), MviViewModel<CookingContract.State, CookingContract.Event, CookingContract.Effect> {

    private val _uiState = MutableStateFlow(CookingContract.State())
    override val uiState: StateFlow<CookingContract.State> = _uiState.asStateFlow()

    override fun onEvent(event: CookingContract.Event) {
        when (event) {
            is CookingContract.Event.LoadRecipe -> loadRecipe(event.id)
        }
    }

    private fun loadRecipe(id: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            repository.getRecipeById(id)
                .onSuccess { recipe ->
                    _uiState.update { it.copy(isLoading = false, recipe = recipe) }
                }
                .onFailure {
                    _uiState.update { it.copy(isLoading = false, error = "Xatolik yuz berdi") }
                }
        }
    }
}