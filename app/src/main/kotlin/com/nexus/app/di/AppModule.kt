package com.nexus.app.di

import com.nexus.app.FlavorConfigImpl
import com.nexus.app.NetworkConfigImpl
import com.nexus.core.network.NetworkConfig
import com.nexus.core.ui.config.FlavorConfig
import org.koin.dsl.module

/**
 * App-level Koin module.
 *
 * [FlavorConfig] → resolved from the flavor's source set (bankCorp or bankDigital).
 * [NetworkConfig] → resolved from the main source set, reads BuildConfig values.
 *
 * Because only one flavor is ever compiled into the final APK, there is
 * exactly one [FlavorConfigImpl] class available at build time.
 */
val appModule = module {
    single<FlavorConfig> { FlavorConfigImpl() }
    single<NetworkConfig> { NetworkConfigImpl() }
}
