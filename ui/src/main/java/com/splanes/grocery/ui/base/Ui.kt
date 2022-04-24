package com.splanes.grocery.ui.base

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewModelScope
import com.splanes.grocery.ui.base.Ui.SideEffect
import com.splanes.grocery.ui.base.Ui.Stable
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import androidx.compose.runtime.State as ComposeState
import androidx.lifecycle.ViewModel as AndroidViewModel

interface Ui {
    sealed class State<out E, out M> {
        override fun equals(other: Any?): Boolean {
            return super.equals(other) && (other as? State<*, *>)?.let { otherState ->
                when (otherState) {
                    is Stable -> this is Stable && this.model == otherState.model
                    is Error -> this is Error && this.error == otherState.error
                }
            } ?: false
        }
        override fun hashCode(): Int {
            return javaClass.hashCode()
        }
        companion object
    }

    interface SideEffect

    data class Stable<out M>(val model: M) : State<Nothing, M>()
    data class Error<out E>(val error: E?) : State<E, Nothing>()

    abstract class ViewModel<E, M, S : SideEffect> : AndroidViewModel(), CoroutineScope {

        internal lateinit var _uiState: MutableStateFlow<State<E, M>>
        internal val _uiSideEffect = MutableSharedFlow<S>()
        private val _uiModel: M? get() = withUiState { state -> state.modelOrNull() }

        override val coroutineContext: CoroutineContext
            get() = Job() + Dispatchers.Default

        internal fun <T> withUiState(action: (from: State<E, M>) -> T?): T? =
            if (::_uiState.isInitialized) action(_uiState.value) else null

        internal fun emitUiState(state: State<E, M>) {
            _uiState.run { if (!tryEmit(state)) viewModelScope.launch { emit(state) } }
        }

        fun updateUiState(error: E? = null, model: M? = null) {
            withUiState { from ->
                when {
                    from is Stable && (from.model != model || error == null) -> from.copy(model = model)
                    from is Stable && error != null -> Error(error)
                    from is Error && (from.error != error || model == null) -> from.copy(error = error)
                    from is Error && model != null -> Stable(model = model)
                    else /*from is Error*/ -> from.copy(model = model, error = error)
                }.let { uiState -> emitUiState(uiState) }
            }
        }

        @Composable
        fun uiState(initial: State<E, M>): ComposeState<State<E, M>> {
            if (!::_uiState.isInitialized) {
                _uiState = MutableStateFlow(initial)
            }
            return _uiState.collectAsState()
        }

        @Composable
        fun uiSideEffect(initial: S? = null): ComposeState<S?> {
            return _uiSideEffect.collectAsState(initial)
        }
    }
}

infix fun <M, VM : Ui.ViewModel<*, M, *>> VM.updateUiModel(update: M.() -> M) {
    withUiState { from ->
        if (from is Stable) {
            emitUiState(from.copy(model = from.model.update()))
        }
    }
}

infix fun <S : SideEffect, VM : Ui.ViewModel<*, *, S>> VM.emitSideEffect(effect: S) {
    _uiSideEffect.run { if (!tryEmit(effect)) viewModelScope.launch { emit(effect) } }
}
