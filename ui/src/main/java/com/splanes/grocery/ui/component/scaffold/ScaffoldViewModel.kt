package com.splanes.grocery.ui.component.scaffold

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.splanes.grocery.ui.component.scaffold.Scaffolds.SubcomponentUiState
import com.splanes.grocery.utils.logger.utils.throwMessage
import com.splanes.toolkit.compose.base_arch.feature.presentation.component.viewmodel.utils.flowCollect
import com.splanes.toolkit.compose.base_arch.feature.presentation.component.viewmodel.utils.launch
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.receiveAsFlow

@HiltViewModel
open class ScaffoldViewModel @Inject constructor() : ViewModel(), CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Default

    protected open var uiStateAtStart: Scaffolds.UiState = Scaffolds.UiState.empty()

    private val uiStateMutable: MutableState<Scaffolds.UiState> by lazy { mutableStateOf(uiStateAtStart) }
    private val uiStateUpdateFlow: MutableSharedFlow<Scaffolds.UpdateEvent<*>> = MutableSharedFlow()
    private val uiSideEffectChannel: Channel<Scaffolds.SideEffect> = Channel()

    val uiState: State<Scaffolds.UiState> get() = uiStateMutable
    val uiSideEffectFlow: Flow<Scaffolds.SideEffect> get() = uiSideEffectChannel.receiveAsFlow()
    val scaffoldUiState: Scaffolds.UiState get() = uiState.value

    init {
        onSubscribeUiEvents()
    }

    private fun onSubscribeUiEvents() {
        flowCollect(uiStateUpdateFlow, onCollected = ::onUpdateEvent)
    }

    private fun onUpdateEvent(event: Scaffolds.UpdateEvent<*>) {
        when (val state = event.uiState) {
            is Scaffolds.TopAppBarUiState -> updateUiState { copy(topAppBarUiState = state) }
            is Scaffolds.ContainerUiState -> updateUiState { copy(containerUiState = state) }
            is Scaffolds.BottomBarUiState -> updateUiState { copy(bottomBarUiState = state) }
            is Scaffolds.BottomSheetUiState -> updateUiState { copy(bottomSheetUiState = state) }
            is Scaffolds.SnackbarUiState -> updateUiState { copy(snackbarUiState = state) }
            is Scaffolds.DialogUiState -> updateUiState { copy(dialogUiState = state) }
            is Scaffolds.LoaderUiState -> updateUiState { copy(loaderUiState = state) }
            else -> throwMessage { "Unsupported Scaffolds.SubcomponentUiState type `${state::class.simpleName}`" }
        }
    }

    fun updateUiState(updater: Scaffolds.UiState.() -> Scaffolds.UiState) {
        scaffoldUiState.updater().run {
            uiStateMutable.value = this
            forEachSideEffectLauncher(::handleSideEffectFromUpdate)
        }
    }

    private fun Scaffolds.UiState.forEachSideEffectLauncher(
        action: (SubcomponentUiState<*>) -> Unit
    ) {
        listOf(bottomSheetUiState, snackbarUiState).forEach(action)
    }

    private fun handleSideEffectFromUpdate(subcomponentUiState: SubcomponentUiState<*>) {
        if (subcomponentUiState.isSideEffectLaunched()) {
            when (subcomponentUiState) {
                is Scaffolds.BottomSheetUiState -> Scaffolds.SideEffect.BottomSheetStateChanged
                is Scaffolds.SnackbarUiState -> Scaffolds.SideEffect.SnackbarStateChanged
                else -> null
            }?.let { effect -> emitSideEffect { effect } }
        }
    }

    private fun SubcomponentUiState<*>.isSideEffectLaunched(): Boolean =
        when (this) {
            is Scaffolds.SnackbarUiState -> visible != scaffoldUiState.snackbarUiState.visible
            else -> this is Scaffolds.BottomSheetUiState
        }

    private fun emitSideEffect(builder: () -> Scaffolds.SideEffect) {
        builder().let { effect ->
            with(uiSideEffectChannel) {
                if (!trySend(effect).isSuccess) launch { send(effect) }
            }
        }
    }
}