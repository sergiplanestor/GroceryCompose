package com.splanes.grocery.ui.feature.mainscreen.component

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.splanes.grocery.ui.component.bar.BottomAppBar
import com.splanes.grocery.ui.component.bar.TopAppBar
import com.splanes.grocery.ui.component.scaffold.Scaffold
import com.splanes.grocery.ui.component.scaffold.ScaffoldViewModel
import com.splanes.grocery.ui.component.scaffold.Scaffolds
import com.splanes.grocery.ui.feature.grocerylistdashboard.component.GroceryListDashboardComponent
import com.splanes.grocery.ui.feature.mainscreen.component.subcomponent.bottombar.BottomBarItem
import com.splanes.grocery.ui.feature.mainscreen.component.subcomponent.bottombar.BottomBarItem.Account
import com.splanes.grocery.ui.feature.mainscreen.component.subcomponent.bottombar.BottomBarItem.GroceryListDashboard
import com.splanes.grocery.ui.feature.mainscreen.component.subcomponent.bottombar.BottomBarItem.Markets
import com.splanes.grocery.ui.feature.mainscreen.component.subcomponent.bottombar.BottomBarItem.Products
import com.splanes.grocery.ui.feature.mainscreen.component.subcomponent.bottombar.BottomBarItem.Templates
import com.splanes.grocery.ui.feature.mainscreen.viewmodel.MainScreenViewModel
import com.splanes.grocery.ui.feature.markets.component.MarketsComponent
import com.splanes.grocery.ui.feature.notifications.component.NotificationsComponent
import com.splanes.grocery.ui.feature.products.component.ProductsComponent
import com.splanes.grocery.ui.feature.templates.component.TemplatesComponent
import com.splanes.grocery.ui.utils.shape.TopRoundedCornerShape
import com.splanes.toolkit.compose.base_arch.feature.presentation.component.contract.UiState

@Composable
fun MainScreenComponent(navHostController: NavHostController) {

    val viewModel: MainScreenViewModel = hiltViewModel()
    val scaffoldViewModel: ScaffoldViewModel = hiltViewModel()

    val scaffoldState = (viewModel.uiState.value as UiState.Ready).data.scaffoldUiState
    scaffoldViewModel.updateUiState { scaffoldState }
    Scaffold(
        viewModel = scaffoldViewModel,
        topBar = {
            TopAppBar(uiModel = scaffoldState.topBarUiModel, navHostController = navHostController)
        },
        bottomBar = {
            BottomAppBar(
                uiModel = scaffoldState.bottomBarUiModel,
                onSelected = { item -> viewModel.onBottomBarRedirect(BottomBarItem.map(item.id)) },
                shape = TopRoundedCornerShape(size = 16)
            )
        },
        content = {
            when (scaffoldState.bottomBarItemSelected()) {
                Markets -> MarketsComponent(navHostController, viewModel)
                Products -> ProductsComponent(navHostController, viewModel)
                GroceryListDashboard -> GroceryListDashboardComponent(navHostController, viewModel)
                Templates -> TemplatesComponent(navHostController, viewModel)
                Account -> NotificationsComponent(navHostController, viewModel)
            }
        }
    )
}


private fun Scaffolds.UiState.bottomBarItemSelected(): BottomBarItem =
    BottomBarItem.map(bottomBarUiModel.selected?.id)