package com.nexus.core.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import com.nexus.core.ui.config.FlavorConfig
import org.koin.compose.koinInject

/**
 * CompositionLocal that provides [FlavorConfig] anywhere in the composition tree.
 *
 * Access it in any composable:
 * ```
 * val flavorConfig = LocalFlavorConfig.current
 * ```
 */
val LocalFlavorConfig = staticCompositionLocalOf<FlavorConfig> {
    error("No FlavorConfig provided. Did you wrap your app in NexusTheme?")
}

/**
 * Root application theme. Retrieves [FlavorConfig] from Koin and provides it
 * down the tree via [LocalFlavorConfig], while also configuring MaterialTheme
 * with the flavor's ColorScheme, typography, and shapes.
 *
 * Usage: call once in [MainActivity.setContent].
 */
@Composable
fun NexusTheme(content: @Composable () -> Unit) {
    val flavorConfig: FlavorConfig = koinInject()

    CompositionLocalProvider(
        LocalFlavorConfig provides flavorConfig
    ) {
        MaterialTheme(
            colorScheme = flavorConfig.colorScheme,
            typography = NexusTypography,
            shapes = NexusShapes,
            content = content
        )
    }
}
