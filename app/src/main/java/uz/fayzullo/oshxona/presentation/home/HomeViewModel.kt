package uz.fayzullo.oshxona.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import uz.fayzullo.oshxona.domain.repository.OshxonaRepository
import uz.fayzullo.oshxona.presentation.base.MviViewModel
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: OshxonaRepository
) : ViewModel(), MviViewModel<HomeContract.State, HomeContract.Event, HomeContract.Effect> {

    private val _uiState = MutableStateFlow(HomeContract.State())
    override val uiState: StateFlow<HomeContract.State> = _uiState.asStateFlow()

    private val _effect = Channel<HomeContract.Effect>()
    val effect = _effect.receiveAsFlow()

    private val searchQueryFlow = MutableStateFlow("")

    init {
        loadHomeScreenData()
        observeSearchQuery()
    }

    override fun onEvent(event: HomeContract.Event) {
        when (event) {
            is HomeContract.Event.OnSearchQueryChanged -> {
                _uiState.update { it.copy(searchQuery = event.query) }
                searchQueryFlow.value = event.query
            }
            is HomeContract.Event.OnRefresh -> loadHomeScreenData()
            is HomeContract.Event.OnCategorySelect -> selectCategory(event.categoryKey)
            is HomeContract.Event.OnRecipeClick -> {
                viewModelScope.launch { _effect.send(HomeContract.Effect.NavigateToDetails(event.recipeId)) }
            }
            is HomeContract.Event.OnRandomRecipeClick -> {
                _uiState.value.randomRecipe?.let { recipe ->
                    viewModelScope.launch { _effect.send(HomeContract.Effect.NavigateToDetails(recipe.id)) }
                }
            }
        }
    }

    private fun observeSearchQuery() {
        searchQueryFlow
            .debounce(300L)
            .distinctUntilChanged()
            .onEach { query ->
                if (query.isNotBlank()) {
                    searchRecipes(query)
                } else if (_uiState.value.selectedCategoryKey == null) {
                    loadHomeScreenData()
                }
            }
            .launchIn(viewModelScope)
    }

    private fun searchRecipes(query: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            repository.searchRecipes(query)
                .onSuccess { recipes ->
                    _uiState.update { it.copy(isLoading = false, recipes = recipes) }
                }
                .onFailure {
                    _uiState.update { it.copy(isLoading = false, error = "Qidiruvda xatolik") }
                }
        }
    }

    private fun loadHomeScreenData() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            val categoriesResult = repository.getCategories()
            val recipesResult = repository.getRecipes(page = 0)
            val randomRecipeResult = repository.getRandomRecipe()

            if (categoriesResult.isSuccess && recipesResult.isSuccess) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        categories = categoriesResult.getOrDefault(emptyList()),
                        recipes = recipesResult.getOrDefault(emptyList()),
                        randomRecipe = randomRecipeResult.getOrNull()
                    )
                }
            } else {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = "Ma'lumotlarni yuklashda xatolik yuz berdi"
                    )
                }
            }
        }
    }

    private fun selectCategory(categoryKey: String?) {
        viewModelScope.launch {
            val newKey = if (_uiState.value.selectedCategoryKey == categoryKey) null else categoryKey
            _uiState.update { it.copy(selectedCategoryKey = newKey, isLoading = true) }

            val result = if (newKey == null) {
                repository.getRecipes(page = 0)
            } else {
                repository.getCategoryRecipes(categoryKey = newKey, page = 0)
            }

            result.onSuccess { recipes ->
                _uiState.update { it.copy(isLoading = false, recipes = recipes) }
            }.onFailure {
                _uiState.update { it.copy(isLoading = false, error = "Kategoriya bo'yicha yuklashda xatolik") }
            }
        }
    }
}