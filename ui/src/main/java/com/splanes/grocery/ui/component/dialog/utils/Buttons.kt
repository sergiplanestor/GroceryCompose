package com.splanes.grocery.ui.component.dialog.utils

import com.splanes.grocery.ui.component.dialog.Dialog

fun Dialog.Button.Companion.styleOfType(type: Dialog.Button.Type): Dialog.Button.Style =
    when (type) {
        Dialog.Button.Type.Positive -> Dialog.Button.Style.Filled
        Dialog.Button.Type.Negative -> Dialog.Button.Style.Flat
        Dialog.Button.Type.Neutral -> Dialog.Button.Style.Flat
        Dialog.Button.Type.Undefined -> Dialog.Button.Style.Undefined
    }

fun List<Dialog.Button>.isCustomContainer(): Boolean =
    size > 2 || filterNot { button -> button.isPositive() || button.isNegative() }.isNotEmpty()

fun Dialog.Button.isPositive(): Boolean =
    this.type == Dialog.Button.Type.Positive

fun Dialog.Button.isNegative(): Boolean =
    this.type == Dialog.Button.Type.Negative

fun Dialog.Button.isNeutral(): Boolean =
    this.type == Dialog.Button.Type.Neutral

fun Dialog.ButtonsConfig.isEmpty(): Boolean =
    this.buttons.isEmpty()

fun Dialog.ButtonsConfig.isNotEmpty(): Boolean =
    this.buttons.isNotEmpty()

fun Dialog.ButtonsConfig.isSingle(): Boolean =
    this.buttons.run { size == 1 && positive() != null }

fun Dialog.ButtonsConfig.isBinary(): Boolean =
    this.buttons.run { size == 2 && positive() != null && negative() != null }

fun Dialog.ButtonsConfig.positive(): Dialog.Button? =
    this.buttons.find { button -> button.isPositive() }

fun Dialog.ButtonsConfig.negative(): Dialog.Button? =
    this.buttons.find { button -> button.isNegative() }

fun Dialog.ButtonsConfig.neutral(): Dialog.Button? =
    this.buttons.find { button -> button.isNeutral() }