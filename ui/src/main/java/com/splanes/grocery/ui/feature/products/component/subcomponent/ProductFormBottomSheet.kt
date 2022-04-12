package com.splanes.grocery.ui.feature.products.component.subcomponent

import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.splanes.grocery.ui.component.bottomsheet.BottomSheetCloseButton
import com.splanes.grocery.ui.component.bottomsheet.BottomSheetTitle
import com.splanes.grocery.ui.component.bottomsheet.BottomSheets
import com.splanes.grocery.ui.component.scaffold.Scaffolds
import com.splanes.grocery.ui.component.spacer.column.Space
import com.splanes.grocery.ui.utils.field.FieldDefaults
import com.splanes.grocery.ui.utils.resources.Strings
import com.splanes.grocery.ui.utils.resources.bodyStyle
import com.splanes.grocery.ui.utils.resources.color
import com.splanes.grocery.ui.utils.resources.dp
import com.splanes.grocery.ui.utils.resources.shape
import com.splanes.grocery.ui.utils.resources.string
import com.splanes.grocery.ui.utils.resources.titleStyle
import com.splanes.grocery.ui.utils.ripple.RippleStyle
import com.splanes.toolkit.compose.ui.components.common.utils.color.alpha
import com.splanes.toolkit.compose.ui.components.common.utils.color.composite

@OptIn(ExperimentalMaterialApi::class)
fun productFormBottomSheetUiState(): Scaffolds.BottomSheetUiState =
    Scaffolds.BottomSheetUiState(
        uiModel = BottomSheets.UiModel(
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
                close = { BottomSheetCloseButton(onClick = { }) },
                content = { ProductFormBottomSheet(onCreate = {}) }
            )
        ),
        state = BottomSheets.Expanded,
        shape = { shape(size = 28) }
    )

@Composable
fun ProductFormBottomSheet(
    onCreate: () -> Unit
) {
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
        ProductFormNameField()
        Space { mediumSmall }
        ProductFormOptionalFieldsContainer {
            Space { mediumSmall }
            ProductFormNameField()
            Space { mediumSmall }
            ProductFormNameField()
            Space { mediumSmall }
            ProductFormNameField()
            Space { mediumSmall }
            ProductFormNameField()
            Space { mediumSmall }
            ProductFormNameField()
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
fun ProductFormOptionalFieldsContainer(
    content: @Composable ColumnScope.() -> Unit
) {
    var visible by remember { mutableStateOf(false) }
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
        ShowMoreOptionalFieldsButton(
            textRes = if (visible) Strings.show_less else Strings.show_more,
            onClick = { visible = !visible }
        )
    }
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
            Space { medium }
            content()
            Space { medium }
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
            contentPadding = PaddingValues(8.dp),
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
fun ColumnScope.ProductFormNameField() {
    var hasFocus by remember { mutableStateOf(false) }
    var isClearIconVisible by remember { mutableStateOf(false) }
    var isErrorMessageVisible by remember { mutableStateOf(false) }
    val colors = FieldDefaults.colorsOutlined(
        textColor = color { onSurface },
        errorColor = color { error.composite(onSurface, .75) },
    )
    OutlinedTextField(
        modifier = Modifier
            .align(Alignment.CenterHorizontally),
        value = "",
        onValueChange = { },
        colors = colors,
        textStyle = bodyStyle { medium },
        shape = FieldDefaults.shapeOutlined(),
        label = { FieldDefaults.Label("Field", colors, error = false, focused = false) },
        singleLine = true,
        leadingIcon = {
            /*FieldDefaults.Icon(
                imageVector = icon,
                iconType = Leading,
                focused = hasFocus,
                colors = colors
            )*/
        },
        trailingIcon = {
            FieldDefaults.IconClear(
                visible = isClearIconVisible,
                onClick = {  },
                size = 18,
                colors = colors
            )
        },
        isError = false
    )
}

@Composable
fun ProductFormMarketsPickerField() {

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
