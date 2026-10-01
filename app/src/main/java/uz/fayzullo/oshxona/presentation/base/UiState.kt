package uz.fayzullo.oshxona.presentation.base

import kotlinx.coroutines.flow.StateFlow

interface UiState
interface UiEvent
interface UiEffect

interface MviViewModel<S : UiState, E : UiEvent, F : UiEffect> {
    val uiState: StateFlow<S>
    fun onEvent(event: E)
}