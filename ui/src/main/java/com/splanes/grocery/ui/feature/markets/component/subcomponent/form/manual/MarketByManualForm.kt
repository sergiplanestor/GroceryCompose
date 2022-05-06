package com.splanes.grocery.ui.feature.markets.component.subcomponent.form.manual

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.rounded.AssignmentInd
import androidx.compose.material3.Divider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.splanes.grocery.domain.feature.market.model.Market
import com.splanes.grocery.domain.feature.market.model.util.all
import com.splanes.grocery.domain.feature.market.model.util.find
import com.splanes.grocery.ui.component.form.Forms
import com.splanes.grocery.ui.component.form.market.brandpicker.MarketBrandPicker
import com.splanes.grocery.ui.component.form.market.brandpicker.itemAt
import com.splanes.grocery.ui.component.form.textinput.TextInput
import com.splanes.grocery.ui.component.form.textinput.textInputColors
import com.splanes.grocery.ui.component.form.textinput.textInputInitialState
import com.splanes.grocery.ui.component.form.utils.NotNullOrBlank
import com.splanes.grocery.ui.component.form.utils.doOnValid
import com.splanes.grocery.ui.component.icons.Icons
import com.splanes.grocery.ui.component.icons.painter
import com.splanes.grocery.ui.component.icons.rounded
import com.splanes.grocery.ui.component.separator.Separator
import com.splanes.grocery.ui.component.spacer.column.Space
import com.splanes.grocery.ui.feature.markets.component.subcomponent.form.contract.MarketManualFormUiModel
import com.splanes.grocery.ui.feature.markets.model.logoResId
import com.splanes.grocery.ui.feature.products.component.subcomponent.form.onChanged
import com.splanes.grocery.ui.utils.resources.Strings
import com.splanes.grocery.ui.utils.resources.bodyStyle
import com.splanes.grocery.ui.utils.resources.dp
import com.splanes.grocery.ui.utils.resources.palette
import com.splanes.grocery.ui.utils.resources.shape
import com.splanes.grocery.ui.utils.resources.string
import com.splanes.grocery.ui.utils.resources.titleStyle
import com.splanes.toolkit.compose.ui.components.common.utils.color.alpha

@Composable
fun MarketByManualForm() {

    val formScrollState = rememberScrollState()
    var uiModel by remember { mutableStateOf(initMarketManualFormModel()) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = dp { medium })
    ) {
        Separator()
        Space { small }
        Column(
            modifier = Modifier
                .fillMaxWidth()
            //.verticalScroll(formScrollState)
        ) {
            var isOptionalVisible by remember { mutableStateOf(true) }
            Space { medium }
            MarketManualFormMandatoryContainer {
                MarketManualFormBrand(
                    selected = uiModel.brand.value,
                    onBrandSelected = { brand -> uiModel = uiModel.copy(brand = uiModel.brand.onChanged(brand)) }
                )
                Space { mediumSmall }
                MarketManualFormAlias(
                    input = uiModel.name.value.orEmpty(),
                    brand = uiModel.brand.value,
                    onMarketNameChanged = { name -> uiModel = uiModel.copy(name = uiModel.name.onChanged(name)) },
                    onImeNextClick = {}
                )
                Space { mediumSmall }
                MarketManualFormAddress()
            }
        }
    }
}

@Composable
fun MarketManualFormDescription(
    isOptionalVisible: Boolean, onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape = shape { large },
        color = palette { inverseSurface.alpha(.12) }
    ) {
        Column(
            modifier = Modifier.padding(
                vertical = dp { mediumSmall },
                horizontal = dp { small }
            )
        ) {
            Text(
                text = string { Strings.market_form_manual_description },
                style = bodyStyle(),
                color = palette { onSurface },
                textAlign = TextAlign.Justify
            )
            Space { medium }
            TextButton(
                modifier = Modifier.align(Alignment.End),
                onClick = onClick
            ) {
                Crossfade(isOptionalVisible) { visible ->
                    Text(
                        text = string {
                            if (visible) {
                                Strings.form_show_optional_fields
                            } else {
                                Strings.form_hide_optional_fields
                            }
                        }.uppercase(),
                        style = bodyStyle(),
                        fontWeight = FontWeight.Medium,
                        color = palette { primary }
                    )
                }
            }
        }
    }
}

@Composable
fun MarketManualFormMandatoryContainer(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    Surface(
        modifier = modifier,
        shape = shape { small },
        border = BorderStroke(width = 3.dp, color = palette { tertiaryContainer.alpha(.5) }),
        color = Color.Transparent
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Divider(
                    modifier = Modifier
                        .width(dp { large })
                        .height(1.dp)
                        .background(color = palette { tertiary.alpha(.3) }, shape = shape(size = 20)),
                    color = palette { tertiary.alpha(.3) }
                )
                Text(
                    modifier = Modifier.padding(horizontal = dp { mediumSmall }),
                    text = "Mandatory",
                    style = titleStyle(),
                    color = palette { tertiary }
                )
                Divider(
                    modifier = Modifier
                        .width(dp { large })
                        .height(1.dp)
                        .background(color = palette { tertiary.alpha(.3) }, shape = shape(size = 20)),
                    color = palette { tertiary.alpha(.3) }
                )
            }
            Space { small }
            content()
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun MarketManualFormBrand(
    selected: Market.Brand? = null,
    onBrandSelected: (Market.Brand) -> Unit
) {
    var model by remember {
        mutableStateOf(
            MarketBrandPicker.UiModel(
                dataSet = Market.Brand.all().map { brand -> brand.toMarketPickerItem() },
                selected = selected?.toMarketPickerItem()
            )
        )
    }

    MarketBrandPicker(
        modifier = Modifier.padding(horizontal = dp { medium }),
        model = model,
        onPageChanged = { index ->
            with(model.itemAt(index)) {
                model = model.copy(selected = this)
                onBrandSelected(toMarketBrand())
            }
        },
        onEditStateChanged = {
            model = model.copy(editState = !model.editState)
        }
    )
}

@Composable
fun MarketManualFormAlias(
    input: String,
    brand: Market.Brand?,
    onMarketNameChanged: (String) -> Unit,
    onImeNextClick: () -> Unit
) {
    val colors = Forms.textInputColors(focusedBorderColor = palette { tertiary })
    var model by remember {
        mutableStateOf(
            Forms.TextInputUiModel(
                uiState = Forms.textInputInitialState(input),
                validators = listOf(
                    Forms.Validator(
                        error = Strings.market_form_error_name_blank,
                        isValueValid = Forms.Validator.NotNullOrBlank
                    ),
                ),
                colors = colors,
                keyboard = Forms.TextKeyboard(ime = Forms.Keyboard.Ime.Next)
            )
        )
    }
    TextInput(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = dp { medium }),
        uiModel = model,
        label = string { Strings.market_form_name_label },
        placeholder = if (brand != null) {
            string({ Strings.market_form_name_placeholder_with_brand }, brand.name)
        } else {
            string { Strings.market_form_name_placeholder }
        },
        leadingIcon = Icons.rounded { AssignmentInd },
        onValueChange = { textInputState ->
            model = model.copy(uiState = textInputState)
            textInputState.doOnValid { value?.run(onMarketNameChanged) }
        }
    )
}

@Composable
fun MarketManualFormAddress(
    input: String,
    onInputChange: (String) -> Unit,
    onImeNextClick: () -> Unit
) {
    val colors = Forms.textInputColors(focusedBorderColor = palette { tertiary })
    var model by remember {
        mutableStateOf(
            Forms.TextInputUiModel(
                uiState = Forms.textInputInitialState(input),
                validators = listOf(
                    Forms.Validator(
                        error = Strings.market_form_error_name_blank,
                        isValueValid = Forms.Validator.NotNullOrBlank
                    ),
                ),
                colors = colors,
                keyboard = Forms.TextKeyboard(ime = Forms.Keyboard.Ime.Next)
            )
        )
    }


}

private fun initMarketManualFormModel() = MarketManualFormUiModel(
    name = Forms.Valid(""),
    brand = Forms.Valid(null)
)

private fun MarketBrandPicker.ItemUiModel.toMarketBrand(): Market.Brand =
    Market.Brand.find { brand -> brand.id == id }

private fun Market.Brand.toMarketPickerItem(): MarketBrandPicker.ItemUiModel =
    MarketBrandPicker.ItemUiModel(
        id = id,
        icon = Icons.painter(logoResId()),
        name = name
    )