package com.nexus.core.ui.config

import androidx.compose.material3.ColorScheme
import androidx.compose.ui.graphics.Color

/**
 * FlavorConfig is the single contract between the white-label shell and each product flavor.
 *
 * Each flavor (bankCorp / bankDigital) provides its own implementation via
 * the flavor source set: app/src/<flavorName>/kotlin/.../FlavorConfigImpl.kt
 *
 * This interface is registered with Koin in AppModule and injected throughout
 * the app via [LocalFlavorConfig] CompositionLocal.
 */
interface FlavorConfig {
    /** Marketing name of this product (e.g. "NexusBank", "NexusPay") */
    val appName: String

    /** Account product type for business-logic branching */
    val accountType: AccountType

    /** Full Material3 ColorScheme for this flavor */
    val colorScheme: ColorScheme

    /** Start color for the gradient hero card */
    val gradientStart: Color

    /** End color for the gradient hero card */
    val gradientEnd: Color
}

/**
 * Represents which banking product this flavor builds.
 */
enum class AccountType(val displayName: String) {
    CHECKING("Conta Corrente"),
    DIGITAL("Conta Digital")
}
