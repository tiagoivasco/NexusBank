package com.nexus.app

import com.nexus.core.network.NetworkConfig

/**
 * Reads [BuildConfig.BASE_URL] and [BuildConfig.DEBUG] that are injected by
 * Gradle at compile time from each flavor's [productFlavors] configuration.
 *
 * This lives in the **main** source set so it compiles for both flavors —
 * the values themselves differ per-flavor via [buildConfigField].
 */
class NetworkConfigImpl : NetworkConfig {
    override val baseUrl: String = BuildConfig.BASE_URL
    override val isDebug: Boolean = BuildConfig.DEBUG
}
