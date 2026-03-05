package com.nexus.app

import androidx.compose.material3.ColorScheme
import androidx.compose.ui.graphics.Color
import com.nexus.core.ui.config.AccountType
import com.nexus.core.ui.config.FlavorConfig
import com.nexus.core.ui.theme.BankCorpColorScheme
import com.nexus.core.ui.theme.BankCorpColors

/**
 * NexusBank — Conta Corrente
 *
 * Corporate visual identity: deep navy blue with gold accents.
 * Targets B2B and traditional banking customers.
 */
class FlavorConfigImpl : FlavorConfig {
    override val appName: String = "NexusBank"
    override val accountType: AccountType = AccountType.CHECKING
    override val colorScheme: ColorScheme = BankCorpColorScheme
    override val gradientStart: Color = BankCorpColors.Navy900
    override val gradientEnd: Color = BankCorpColors.Navy700
}
