package com.splanes.grocery.ui.component.form.textinput

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.splanes.grocery.ui.component.form.Forms
import com.splanes.toolkit.compose.ui.theme.UiTheme

@Preview(name = "TextInputPreview", showBackground = true)
@Composable
private fun TextInputPreview() {
    UiTheme.AppTheme {
        TextInput(
            uiModel = Forms.TextInputUiModel(
                uiState = Forms.textInputInitialState(""),
                colors = Forms.textInputColors()
            ),
            label = "Name",
            onValueChange = {}
        )
    }
}