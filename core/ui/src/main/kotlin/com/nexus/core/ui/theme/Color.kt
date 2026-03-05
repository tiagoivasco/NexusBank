package com.nexus.core.ui.theme

import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

// ─── NexusBank — Corporate Navy / Gold ────────────────────────────────────────
object BankCorpColors {
    val Navy900 = Color(0xFF0A2463)
    val Navy700 = Color(0xFF1B3A80)
    val Navy500 = Color(0xFF3E6BBA)
    val Gold500 = Color(0xFFD4AF37)
    val Gold300 = Color(0xFFEDD85D)
    val Blue400 = Color(0xFF3E92CC)
    val Background = Color(0xFFF4F6FB)
    val Surface = Color(0xFFFFFFFF)
    val SurfaceVar = Color(0xFFE8EDF5)
    val TextPrimary = Color(0xFF0D1B2A)
    val TextSecondary = Color(0xFF5C6B7A)
    val Success = Color(0xFF2E7D32)
    val Error = Color(0xFFB00020)
}

// ─── NexusPay — Modern Purple / Teal ─────────────────────────────────────────
object BankDigitalColors {
    val Purple700 = Color(0xFF5E35B1)
    val Purple500 = Color(0xFF7E57C2)
    val Purple300 = Color(0xFFB39DDB)
    val Teal500 = Color(0xFF00BCD4)
    val Teal300 = Color(0xFF4DD0E1)
    val Pink400 = Color(0xFFEC407A)
    val Background = Color(0xFFF3F0FF)
    val Surface = Color(0xFFFFFFFF)
    val SurfaceVar = Color(0xFFEDE7F6)
    val TextPrimary = Color(0xFF1A1035)
    val TextSecondary = Color(0xFF6B5D8A)
    val Success = Color(0xFF00897B)
    val Error = Color(0xFFAD1457)
}

// ─── Material3 ColorScheme — NexusBank ────────────────────────────────────────
val BankCorpColorScheme = lightColorScheme(
    primary = BankCorpColors.Navy900,
    onPrimary = Color.White,
    primaryContainer = BankCorpColors.Navy700,
    onPrimaryContainer = Color.White,
    secondary = BankCorpColors.Gold500,
    onSecondary = BankCorpColors.TextPrimary,
    secondaryContainer = BankCorpColors.Gold300,
    onSecondaryContainer = BankCorpColors.TextPrimary,
    tertiary = BankCorpColors.Blue400,
    onTertiary = Color.White,
    background = BankCorpColors.Background,
    onBackground = BankCorpColors.TextPrimary,
    surface = BankCorpColors.Surface,
    onSurface = BankCorpColors.TextPrimary,
    surfaceVariant = BankCorpColors.SurfaceVar,
    onSurfaceVariant = BankCorpColors.TextSecondary,
    error = BankCorpColors.Error,
    onError = Color.White,
)

// ─── Material3 ColorScheme — NexusPay ─────────────────────────────────────────
val BankDigitalColorScheme = lightColorScheme(
    primary = BankDigitalColors.Purple700,
    onPrimary = Color.White,
    primaryContainer = BankDigitalColors.Purple500,
    onPrimaryContainer = Color.White,
    secondary = BankDigitalColors.Teal500,
    onSecondary = BankDigitalColors.TextPrimary,
    secondaryContainer = BankDigitalColors.Teal300,
    onSecondaryContainer = BankDigitalColors.TextPrimary,
    tertiary = BankDigitalColors.Pink400,
    onTertiary = Color.White,
    background = BankDigitalColors.Background,
    onBackground = BankDigitalColors.TextPrimary,
    surface = BankDigitalColors.Surface,
    onSurface = BankDigitalColors.TextPrimary,
    surfaceVariant = BankDigitalColors.SurfaceVar,
    onSurfaceVariant = BankDigitalColors.TextSecondary,
    error = BankDigitalColors.Error,
    onError = Color.White,
)
