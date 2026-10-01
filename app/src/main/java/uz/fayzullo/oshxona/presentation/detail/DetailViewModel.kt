package uz.fayzullo.oshxona.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import uz.fayzullo.oshxona.domain.repository.OshxonaRepository
import uz.fayzullo.oshxona.presentation.base.MviViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: OshxonaRepository
) : ViewModel(),
    MviViewModel<DetailContract.State, DetailContract.Event, DetailContract.Effect> {

    private val _uiState = MutableStateFlow(DetailContract.State())
    override val uiState: StateFlow<DetailContract.State> = _uiState.asStateFlow()

    private val _effect = Channel<DetailContract.Effect>()
    val effect = _effect.receiveAsFlow()

    override fun onEvent(event: DetailContract.Event) {
        when (event) {
            is DetailContract.Event.LoadRecipe -> loadRecipe(event.id)
            is DetailContract.Event.OnBackClick -> {
                viewModelScope.launch { _effect.send(DetailContract.Effect.PopBackStack) }
            }
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