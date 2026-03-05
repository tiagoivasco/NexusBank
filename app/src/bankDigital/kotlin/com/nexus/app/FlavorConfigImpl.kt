package com.nexus.app

import androidx.compose.material3.ColorScheme
import androidx.compose.ui.graphics.Color
import com.nexus.core.ui.config.AccountType
import com.nexus.core.ui.config.FlavorConfig
import com.nexus.core.ui.theme.BankDigitalColorScheme
import com.nexus.core.ui.theme.BankDigitalColors

/**
 * NexusPay — Conta Digital
 *
 * Modern visual identity: vibrant purple with teal accents.
 * Targets B2C, digital-native, zero-fee positioning.
 */
class FlavorConfigImpl : FlavorConfig {
    override val appName: String = "NexusPay"
    override val accountType: AccountType = AccountType.DIGITAL
    override val colorScheme: ColorScheme = BankDigitalColorScheme
    override val gradientStart: Color = BankDigitalColors.Purple700
    override val gradientEnd: Color = BankDigitalColors.Purple500
}
