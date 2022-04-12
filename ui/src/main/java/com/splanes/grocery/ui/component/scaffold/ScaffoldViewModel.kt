package com.splanes.grocery.ui.component.scaffold

import androidx.compose.material.ExperimentalMaterialApi
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

    private fun emitSideEffect(builder: () -> Scaffolds.SideEffect) {
        launch { uiSideEffectChannel.send(builder()) }
    }

    fun updateUiState(updater: Scaffolds.UiState.() -> Scaffolds.UiState) {
        val newState = scaffoldUiState.updater()
        listOf(
            newState.containerUiState,
            newState.bottomSheetUiState,
            newState.snackbarUiState
        ).forEach { it.handleUpdateSideEffect() }
        uiStateMutable.value = newState
    }

    @OptIn(ExperimentalMaterialApi::class)
    private fun SubcomponentUiState<*>.handleUpdateSideEffect() = takeIf { it.isSideEffectUpdate() }?.run {
        when (this) {
            is Scaffolds.BottomSheetUiState -> Scaffolds.SideEffect.BottomSheet(state)
            is Scaffolds.SnackbarUiState -> Scaffolds.SideEffect.Snackbar
            else -> null
        }?.let { effect -> emitSideEffect { effect } }
    }

    @OptIn(ExperimentalMaterialApi::class)
    private fun SubcomponentUiState<*>.isSideEffectUpdate(): Boolean =
        when (this) {
            is Scaffolds.BottomSheetUiState -> {
                state != scaffoldUiState.bottomSheetUiState.state
            }
            is Scaffolds.SnackbarUiState -> {
                scaffoldUiState.snackbarUiState.visible && visible != scaffoldUiState.snackbarUiState.visible
            }
            else -> false
        }

}