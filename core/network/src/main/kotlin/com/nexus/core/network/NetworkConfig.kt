package com.nexus.core.network

/**
 * Each flavor's [AppModule] provides a concrete implementation of this interface,
 * reading [baseUrl] from [BuildConfig.BASE_URL] — so the network layer never
 * needs to depend on the :app module directly.
 */
interface NetworkConfig {
    val baseUrl: String
    val isDebug: Boolean
}
