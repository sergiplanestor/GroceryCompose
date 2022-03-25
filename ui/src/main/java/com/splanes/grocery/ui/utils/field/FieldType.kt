package com.splanes.grocery.ui.utils.field

import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType

sealed class FieldType(open val caps: KeyboardCapitalization, open val keyboardType: KeyboardType) {

    object Email : FieldType(caps = KeyboardCapitalization.None, keyboardType = KeyboardType.Email)

    data class Text(
        override val caps: KeyboardCapitalization = KeyboardCapitalization.Sentences,
        override val keyboardType: KeyboardType = KeyboardType.Text
    ) : FieldType(caps, keyboardType)

    object Password : FieldType(caps = KeyboardCapitalization.None, keyboardType = KeyboardType.Password)

    object Number : FieldType(caps = KeyboardCapitalization.None, keyboardType = KeyboardType.Number)

}
