package com.splanes.grocery.ui.feature.products.component.subcomponent

import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.OutlinedTextField
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.splanes.grocery.ui.component.bottomsheet.BottomSheetCloseButton
import com.splanes.grocery.ui.component.bottomsheet.BottomSheetTitle
import com.splanes.grocery.ui.component.bottomsheet.BottomSheets
import com.splanes.grocery.ui.component.form.model.Forms
import com.splanes.grocery.ui.component.form.model.Forms.Error
import com.splanes.grocery.ui.component.form.model.Forms.isError
import com.splanes.grocery.ui.component.icons.Icons
import com.splanes.grocery.ui.component.icons.painter
import com.splanes.grocery.ui.component.scaffold.Scaffolds
import com.splanes.grocery.ui.component.spacer.column.Space
import com.splanes.grocery.ui.component.spacer.row.Weight
import com.splanes.grocery.ui.feature.products.component.subcomponent.form.NotNullOrBlankValidators
import com.splanes.grocery.ui.feature.products.component.subcomponent.form.ProductFormField
import com.splanes.grocery.ui.feature.products.component.subcomponent.form.ProductFormViewModel
import com.splanes.grocery.ui.feature.products.component.subcomponent.form.onChanged
import com.splanes.grocery.ui.utils.anim.AnimDefaults
import com.splanes.grocery.ui.utils.anim.scrollTo
import com.splanes.grocery.ui.utils.field.FieldDefaults
import com.splanes.grocery.ui.utils.field.FieldType
import com.splanes.grocery.ui.utils.resources.Drawables
import com.splanes.grocery.ui.utils.resources.Strings
import com.splanes.grocery.ui.utils.resources.alpha
import com.splanes.grocery.ui.utils.resources.bodyStyle
import com.splanes.grocery.ui.utils.resources.color
import com.splanes.grocery.ui.utils.resources.dp
import com.splanes.grocery.ui.utils.resources.labelStyle
import com.splanes.grocery.ui.utils.resources.shape
import com.splanes.grocery.ui.utils.resources.string
import com.splanes.grocery.ui.utils.resources.titleStyle
import com.splanes.grocery.ui.utils.ripple.RippleStyle
import com.splanes.grocery.ui.utils.shape.TopRoundedCornerShape
import com.splanes.grocery.ui.utils.viewmodel.uiModel
import com.splanes.toolkit.compose.ui.components.common.utils.color.alpha
import com.splanes.toolkit.compose.ui.components.common.utils.color.composite
import kotlinx.coroutines.CoroutineScope

@OptIn(ExperimentalMaterialApi::class)
fun productFormBottomSheetUiState(onClose: () -> Unit): Scaffolds.BottomSheetUiState =
    Scaffolds.BottomSheetUiState(
        uiModel = BottomSheets.UiModel(
            id = "ProductForm",
            uiContent = BottomSheets.UiContent(
                title = {
                    BottomSheetTitle(
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = dp { mediumSmall }),
                        text = string { Strings.products_add_new_form_title },
                        align = TextAlign.Start
                    )
                },
                close = { BottomSheetCloseButton(onClick = onClose) },
                content = { scrollState ->
                    scrollState?.let { ProductFormBottomSheet(scrollState) }
                }
            )
        ),
        sheetValue = BottomSheets.Expanded,
        shape = { TopRoundedCornerShape(size = 28) }
    )

@Composable
fun ProductFormBottomSheet(
    scrollState: ScrollState
) {
    val coroutineScope = rememberCoroutineScope()
    val viewModel = hiltViewModel<ProductFormViewModel>()
    val uiModel = viewModel.uiModel()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = dp { medium }),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Space { mediumSmall }
        ProductFormDescription()
        Space { mediumSmall }
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(color = color { primary }.alpha(.15)),
            color = color { primary }.alpha(.15)
        )
        Space { medium }
        ProductFormNameField(
            state = uiModel.nameFormState,
            onFocused = {
                scrollTo(
                    coroutineScope,
                    scrollState,
                    field = ProductFormField.Name
                )
            },
            onChange = { input ->
                viewModel.onFieldUpdate {
                    copy(nameFormState = nameFormState.onChanged(input, NotNullOrBlankValidators))
                }
            }
        )
        ProductFormOptionalFieldsContainer {
            ProductFormDescriptionField(
                state = uiModel.descriptionFormState,
                onFocused = { scrollTo(coroutineScope, scrollState, field = ProductFormField.Description) },
                onChange = { input ->
                    viewModel.onFieldUpdate {
                        copy(descriptionFormState = descriptionFormState.onChanged(input))
                    }
                }
            )
            Space { mediumSmall }
            ProductFormCategoryField(
                state = uiModel.categoryFormState,
                onFocused = { scrollTo(coroutineScope, scrollState, field = ProductFormField.Category) },
                onChange = { input ->
                    viewModel.onFieldUpdate {
                        copy(categoryFormState = categoryFormState.onChanged(input))
                    }
                }
            )
            Space { mediumSmall }
            ProductFormMarketsPickerField(
                state = uiModel.marketsFormState,
                onFocused = { scrollTo(coroutineScope, scrollState, field = ProductFormField.Markets) },
                onChange = { input ->
                    viewModel.onFieldUpdate {
                        copy(marketsFormState = marketsFormState.onChanged(input))
                    }
                }
            )
            Space { mediumSmall }
        }
        Space { medium }
    }
}


@Composable
fun ProductFormDescription() {
    Text(
        text = string { Strings.products_add_new_form_description },
        style = bodyStyle(),
        color = color { onSurface },
        textAlign = TextAlign.Justify
    )
}

@Composable
fun ColumnScope.ProductFormOptionalFieldsContainer(
    content: @Composable ColumnScope.() -> Unit
) {
    var visible by remember { mutableStateOf(false) }
    val weight by animateFloatAsState(
        targetValue = if (visible) 0.1f else 1.0f,
        animationSpec = tween(durationMillis = 1000)
    )
    val distance by animateDpAsState(
        targetValue = (if (visible) 4 else 12).dp,
        animationSpec = tween(durationMillis = 1000)
    )

    Space(dp = distance)
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Weight()
        ShowMoreOptionalFieldsButton(
            textRes = if (visible) Strings.show_less else Strings.show_more,
            onClick = { visible = !visible }
        )
        Spacer(modifier = Modifier.weight(weight = weight))
    }
    Space(dp = distance)
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(animationSpec = tween(durationMillis = 250)) +
                expandVertically(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioLowBouncy,
                        stiffness = Spring.StiffnessMedium
                    )
                ),
        exit = fadeOut(animationSpec = tween(durationMillis = 250)) + shrinkVertically(
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioLowBouncy,
                stiffness = Spring.StiffnessMedium
            )
        )
    ) {
        Column(
            modifier = Modifier,
        ) {
            content()
        }
    }
}

@Composable
fun ShowMoreOptionalFieldsButton(
    @StringRes textRes: Int,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    RippleStyle(color = color { secondary }) {
        TextButton(
            modifier = modifier,
            contentPadding = PaddingValues(16.dp),
            colors = ButtonDefaults.textButtonColors(
                contentColor = color { secondary },
                containerColor = Color.Transparent
            ),
            shape = shape(size = 16),
            onClick = onClick
        ) {
            Text(
                text = string { textRes },
                style = titleStyle { small },
                color = color { secondary }
            )
        }
    }
}

@Composable
fun ProductFormNameField(
    state: Forms.State<String>,
    onFocused: () -> Unit,
    onChange: (String) -> Unit
) {
    ProductFormTextField(
        value = state.value.orEmpty(),
        onChange = onChange,
        onFocused = onFocused,
        label = string { Strings.product_name_form_label },
        icon = Icons.painter(Drawables.ic_grocery_product),
        isError = state.isError(),
        errorMessage = (state as? Error<String>)?.message,
        keyboardOptions = FieldDefaults.keyboardOption(FieldType.Text(), ImeAction.Next)
    )
}

@Composable
fun ProductFormDescriptionField(
    state: Forms.State<String>,
    onFocused: () -> Unit,
    onChange: (String) -> Unit
) {
    ProductFormTextField(
        value = state.value.orEmpty(),
        onChange = onChange,
        onFocused = onFocused,
        label = string { Strings.product_description_form_label },
        icon = Icons.painter(Drawables.ic_grocery_product),
        isError = state.isError(),
        errorMessage = (state as? Error<String>)?.message,
        keyboardOptions = FieldDefaults.keyboardOption(FieldType.Text(), ImeAction.Next)
    )
}

@Composable
fun ProductFormCategoryField(
    state: Forms.State<String>,
    onFocused: () -> Unit,
    onChange: (String) -> Unit
) {
    ProductFormTextField(
        value = state.value.orEmpty(),
        onChange = onChange,
        onFocused = onFocused,
        label = string { Strings.product_category_form_label},
        icon = Icons.painter(Drawables.ic_grocery_product),
        isError = state.isError(),
        errorMessage = (state as? Error<String>)?.message,
        keyboardOptions = FieldDefaults.keyboardOption(FieldType.Text(), ImeAction.Next)
    )
}

@Composable
fun ProductFormMarketsPickerField(
    state: Forms.State<String?>,
    onFocused: () -> Unit,
    onChange: (String) -> Unit
) {
    ProductFormTextField(
        value = state.value.orEmpty(),
        onChange = onChange,
        onFocused = onFocused,
        label = string { Strings.product_markets_form_label },
        icon = Icons.painter(Drawables.ic_grocery_market),
        isError = state.isError(),
        errorMessage = (state as? Error<String?>)?.message,
        keyboardOptions = FieldDefaults.keyboardOption(FieldType.Text(), ImeAction.Next)
    )
}

@Composable
fun ProductFormTypeField() {

}

@Composable
fun ProductFormPriceField() {

}

@Composable
fun ProductFormAddToCurrentField() {

}

@Composable
fun ProductFormIsHighlightField() {

}

@Composable
fun ProductFormTextField(
    value: String,
    onChange: (String) -> Unit,
    label: String,
    icon: Icons.Source,
    isError: Boolean,
    errorMessage: Int?,
    keyboardOptions: KeyboardOptions,
    modifier: Modifier = Modifier,
    onFocused: () -> Unit = {}
) {
    var hasFocus by remember { mutableStateOf(false) }
    var isClearIconVisible by remember { mutableStateOf(false) }
    var isErrorMessageVisible by remember { mutableStateOf(false) }
    val colors = FieldDefaults.colorsOutlined(
        borderFocusedColor = color { primary },
        borderUnfocusedColor = color { onSurface },
        textColor = color { onSurface },
        errorColor = color { error.composite(onSurface, .75) },
    )
    Column(modifier = modifier) {
        OutlinedTextField(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .onFocusEvent { focusState ->
                    hasFocus = focusState.hasFocus
                    isClearIconVisible = hasFocus && value.isNotBlank()
                    if (hasFocus) onFocused()
                },
            value = value,
            onValueChange = { input ->
                isErrorMessageVisible = (isError && input.isBlank()).not()
                onChange(input)
            },
            colors = colors,
            textStyle = bodyStyle { medium },
            shape = FieldDefaults.shapeOutlined(),
            label = { FieldDefaults.Label(label, colors, error = isError, focused = hasFocus) },
            singleLine = true,
            leadingIcon = {
                Icons.Icon(
                    source = icon,
                    size = 20.dp,
                    color = colors.leadingIconColor(
                        enabled = true,
                        isError = false
                    ).value.alpha(if (hasFocus) .7 else .45)
                )
            },
            trailingIcon = {
                FieldDefaults.IconClear(
                    visible = isClearIconVisible,
                    onClick = { onChange("") },
                    size = 18,
                    colors = colors
                )
            },
            isError = isError,
            keyboardOptions = keyboardOptions
        )
        AnimatedVisibility(
            visible = isErrorMessageVisible,
            enter = com.splanes.toolkit.compose.ui.components.feature.navhost.graph.transition.fadeIn(duration = AnimDefaults.DurationShort.toInt()) +
                    expandVertically(animationSpec = com.splanes.grocery.ui.utils.anim.tween(duration = AnimDefaults.DurationShort)),
            exit = com.splanes.toolkit.compose.ui.components.feature.navhost.graph.transition.fadeOut(duration = AnimDefaults.DurationShort.toInt()) +
                    shrinkVertically(animationSpec = com.splanes.grocery.ui.utils.anim.tween(duration = AnimDefaults.DurationShort)),
        ) {
            errorMessage?.let { text ->
                androidx.compose.material.Text(
                    modifier = Modifier.padding(vertical = dp { small }),
                    text = string { text },
                    style = labelStyle { medium },
                    color = color {
                        error.composite(
                            onSurface,
                            .75
                        )
                    }.alpha { if (hasFocus) high else medium }
                )
            }
        }
    }

}

private fun scrollTo(scope: CoroutineScope, state: ScrollState, field: ProductFormField) {
    scope.scrollTo(
        scrollState = state,
        target = field.position.coerceAtMost(state.maxValue),
        spec = tween(delayMillis = 100, durationMillis = 300)
    )
}