package com.tset.versionwatch.model

/**
 * Represents an immutable collection of the current and previous full system versions
 * The two (sealed) implementations basically realize a NonEmpyList which is sadly missing from Kotlin's stdlib
 */
sealed interface SystemVersions {

    /**
     * Uninitialized system version, used before the first deploy is made
     */
    object Empty : SystemVersions {
        override fun get(version: Int): Nothing? = null
        override fun withService(service: String, version: Int) = NonEmptySysVer.initial(service to version)
    }

    /**
     * Returns a system state by ID
     */
    operator fun get(version: Int): SystemState?

    /**
     * Upserts a given service and returns the (optionally) modified and always non-empty history of all states
     */
    fun withService(service: String, version: Int): NonEmptySysVer
}

/**
 * Represents the history of full system versions - guaranteed to have had deploy(s) made previously
 */
class NonEmptySysVer(
    val currentState: SystemState,
    private val oldStates: List<SystemState>
) : SystemVersions {

    private val allStates = oldStates + currentState

    companion object {
        fun initial(vararg versions: VersionedService) = NonEmptySysVer(
            currentState = SystemState.initial(versions.toList()),
            oldStates = emptyList()
        )
    }

    override operator fun get(version: Int): SystemState? = allStates.find { it.systemVersion == version }

    fun withServiceIfChanged(service: String, version: Int): NonEmptySysVer? {
        return currentState.withServiceIfChanged(service, version)?.let { updatedState ->
            NonEmptySysVer(currentState = updatedState, oldStates = allStates)
        }
    }

    override fun withService(service: String, version: Int): NonEmptySysVer =
        withServiceIfChanged(service, version) ?: this

    override fun equals(other: Any?) = other is NonEmptySysVer && allStates == other.allStates

    override fun hashCode() = allStates.hashCode()

}