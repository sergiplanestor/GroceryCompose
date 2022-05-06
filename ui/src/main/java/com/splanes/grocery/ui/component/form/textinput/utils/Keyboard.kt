package com.splanes.grocery.ui.component.form.textinput.utils

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.KeyboardType.Companion
import com.splanes.grocery.ui.component.form.Forms

fun Forms.Keyboard.toKeyboardOptions(): KeyboardOptions =
    KeyboardOptions(
        capitalization,
        autoCorrect,
        keyboardType,
        imeAction
    )

fun Forms.Keyboard.Caps.toKeyboardCapitalization(): KeyboardCapitalization =
    when (this) {
        Forms.Keyboard.Caps.All -> KeyboardCapitalization.Characters
        Forms.Keyboard.Caps.Sentence -> KeyboardCapitalization.Sentences
        Forms.Keyboard.Caps.Word -> KeyboardCapitalization.Words
        Forms.Keyboard.Caps.None -> KeyboardCapitalization.None
    }

fun Forms.Keyboard.Input.toKeyboardType(): KeyboardType =
    when (this) {
        Forms.Keyboard.Input.Text -> KeyboardType.Text
        Forms.Keyboard.Input.Email -> Companion.Email
        Forms.Keyboard.Input.Phone -> Companion.Phone
        Forms.Keyboard.Input.Uri -> Companion.Uri
        Forms.Keyboard.Input.Number -> Companion.Number
        Forms.Keyboard.Input.Decimal -> Companion.Decimal
        Forms.Keyboard.Input.Password -> Companion.Password
        Forms.Keyboard.Input.NumericPassword -> Companion.NumberPassword
    }

fun Forms.Keyboard.Ime.toImeAction(): ImeAction =
    when (this) {
        Forms.Keyboard.Ime.None -> ImeAction.None
        Forms.Keyboard.Ime.Default -> ImeAction.Default
        Forms.Keyboard.Ime.Go -> ImeAction.Go
        Forms.Keyboard.Ime.Search -> ImeAction.Search
        Forms.Keyboard.Ime.Send -> ImeAction.Send
        Forms.Keyboard.Ime.Previous -> ImeAction.Previous
        Forms.Keyboard.Ime.Next -> ImeAction.Next
        Forms.Keyboard.Ime.Done -> ImeAction.Done
    }