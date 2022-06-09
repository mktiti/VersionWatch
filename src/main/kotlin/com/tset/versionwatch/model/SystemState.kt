package com.tset.versionwatch.model

typealias VersionedService = Pair<String, Int>

/**
 * Data class representing a full system version -- a versioned set of service states
 */
data class SystemState(
    val systemVersion: Int,
    val serviceVersions: Map<String, Int>
) : Comparable<SystemState> {

    companion object {
        fun initial(services: Collection<VersionedService>) = SystemState(1, services.toMap())

        fun initial(vararg services: VersionedService) = initial(services.toList())
    }

    /**
     * Version of a given state if it is tracked
     */
    fun serviceVersion(service: String): Int? = serviceVersions[service]

    /**
     * Upserts a given service to a new version.
     * Returns null is the service was already at the given version and thus no change is made.
     */
    fun withServiceIfChanged(service: String, version: Int): SystemState? {
        return if (serviceVersion(service) == version) {
            null
        } else {
            SystemState(systemVersion + 1, serviceVersions + (service to version))
        }
    }

    /**
     * Same as [withServiceIfChanged], but returns this current state if no change is made.
     */
    fun withService(service: String, version: Int): SystemState = withServiceIfChanged(service, version) ?: this

    override fun compareTo(other: SystemState) = systemVersion.compareTo(other.systemVersion)

}