package com.splanes.grocery.ui.utils.resources

import android.content.res.Resources
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.splanes.grocery.utils.logger.utils.throwMessage
import com.splanes.toolkit.compose.ui.theme.feature.colors.ThemeColorScheme
import com.splanes.toolkit.compose.ui.theme.utils.accessors.Body
import com.splanes.toolkit.compose.ui.theme.utils.accessors.Colors
import com.splanes.toolkit.compose.ui.theme.utils.accessors.ComponentPaddings
import com.splanes.toolkit.compose.ui.theme.utils.accessors.Display
import com.splanes.toolkit.compose.ui.theme.utils.accessors.Headline
import com.splanes.toolkit.compose.ui.theme.utils.accessors.Label
import com.splanes.toolkit.compose.ui.theme.utils.accessors.Shapes
import com.splanes.toolkit.compose.ui.theme.utils.accessors.Title
import com.splanes.toolkit.compose.ui.theme.utils.accessors.Typographies
import com.splanes.toolkit.compose.ui.theme.utils.accessors.ViewportPaddings
import com.splanes.toolkit.compose.ui.theme.utils.size.UiSize

typealias Strings = com.splanes.grocery.ui.R.string
typealias Drawables = com.splanes.grocery.ui.R.drawable

@Composable
fun string(block: () -> Int): String =
    stringResource(id = block())

@Composable
fun string(block: () -> Int, vararg args: Any): String =
    stringResource(id = block(), formatArgs = args.map { it.toString() }.toTypedArray())

@Composable
fun painter(block: () -> Int): Painter =
    painterResource(id = block())

@Composable
fun <T> resource(block: Resources.() -> T): T =
    LocalContext.current.resources.block()

@Composable
fun color(block: ThemeColorScheme.() -> Color): Color =
    Colors.run(block)

@Composable
fun shape(size: Int): Shape = RoundedCornerShape(size.dp)

@Composable
fun shape(block: UiSize<RoundedCornerShape>.() -> Shape): Shape = Shapes.block()

@Composable
fun dp(isComponent: Boolean = true, block: UiSize.Extended<Dp>.() -> Dp): Dp =
    (if (isComponent) ComponentPaddings else ViewportPaddings).block()

@Composable
inline fun <reified T> dpValue(
    isComponent: Boolean = true,
    noinline block: UiSize.Extended<Dp>.() -> Dp
): T =
    with(dp(isComponent, block).value) {
        when (T::class) {
            Float::class -> this
            Int::class -> toInt()
            Long::class -> toLong()
            Double::class -> toDouble()
            else -> throwMessage { "Unsupported type ${T::class.simpleName}" }
        } as T
    }

@Composable
fun padding(block: UiSize.Extended<Dp>.() -> Dp): Dp =
    dp(block = block)

@Composable
fun viewport(block: UiSize.Extended<Dp>.() -> Dp): Dp =
    dp(isComponent = false, block)

@Composable
fun display(block: UiSize<TextStyle>.() -> TextStyle): TextStyle =
    Typographies.Display.block()

@Composable
fun headline(block: UiSize<TextStyle>.() -> TextStyle): TextStyle =
    Typographies.Headline.block()

@Composable
fun title(block: UiSize<TextStyle>.() -> TextStyle): TextStyle =
    Typographies.Title.block()

@Composable
fun body(block: UiSize<TextStyle>.() -> TextStyle): TextStyle =
    Typographies.Body.block()

@Composable
fun label(block: UiSize<TextStyle>.() -> TextStyle): TextStyle =
    Typographies.Label.block()