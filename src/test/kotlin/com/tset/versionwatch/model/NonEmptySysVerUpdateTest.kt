package com.tset.versionwatch.model

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class NonEmptySysVerUpdateTest {

    companion object {
        private val initialState = NonEmptySysVer.initial(
            "serviceA" to 1,
            "serviceB" to 2,
            "serviceC" to 3
        )
    }

    @Test
    fun `test service insert`() {
        val updatedState = initialState.withService("serviceD", 10)

        val expectedState = NonEmptySysVer(
            currentState = SystemState(2, mapOf("serviceA" to 1, "serviceB" to 2, "serviceC" to 3, "serviceD" to 10)),
            oldStates = listOf(initialState.currentState)
        )
        assertEquals(expectedState, updatedState)
    }

    @Test
    fun `test service update`() {
        val updatedState = initialState.withService("serviceB", 10)

        val expectedState = NonEmptySysVer(
            currentState = SystemState(2, mapOf("serviceA" to 1, "serviceB" to 10, "serviceC" to 3)),
            oldStates = listOf(initialState.currentState)
        )
        assertEquals(expectedState, updatedState)
    }

    @Test
    fun `test service update no change`() {
        val updatedState = initialState.withService("serviceB", 2)

        assertEquals(initialState, updatedState)
    }

}