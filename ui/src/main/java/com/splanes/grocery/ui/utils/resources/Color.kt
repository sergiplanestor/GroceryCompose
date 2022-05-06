package com.splanes.grocery.ui.utils.resources

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver
import com.splanes.toolkit.compose.ui.theme.feature.colors.ThemeColorScheme

fun Color.same(other: Color?): Boolean = value == other?.value

@Composable
fun Color.disabled(): Color = alpha { disabled }

@Composable
fun Color.unfocused(): Color = alpha { medium }

@Composable
fun Color.over(
    before: @Composable ThemeColorScheme.(Color) -> Color = { it -> it },
    after: @Composable ThemeColorScheme.(Color) -> Color = { it -> it },
    other: @Composable ThemeColorScheme.() -> Color
): Color = palette { after(before(this@over).compositeOver(other())) }