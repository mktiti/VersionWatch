package com.tset.versionwatch.model

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

internal class SystemStateUpsertTest {

    @Test
    fun `test service insert`() {
        val startState = SystemState(10, mapOf("serviceA" to 1, "serviceB" to 2))

        val updatedState = startState.withServiceIfChanged("serviceC", 3)

        val expectedState = SystemState(11, mapOf("serviceA" to 1, "serviceB" to 2, "serviceC" to 3))
        assertEquals(expectedState, updatedState)
    }

    @Test
    fun `test service update version`() {
        val startState = SystemState(10, mapOf("serviceA" to 1, "serviceB" to 2, "serviceC" to 3))

        val updatedState = startState.withServiceIfChanged("serviceB", 10)

        val expectedState = SystemState(11, mapOf("serviceA" to 1, "serviceB" to 10, "serviceC" to 3))
        assertEquals(expectedState, updatedState)
    }

    @Test
    fun `test service update no change`() {
        val startState = SystemState(10, mapOf("serviceA" to 1, "serviceB" to 2, "serviceC" to 3))

        val updatedState = startState.withServiceIfChanged("serviceB", 2)

        assertNull(updatedState)
    }

    @Test
    fun `test service update if changed no change return same`() {
        val startState = SystemState(10, mapOf("serviceA" to 1, "serviceB" to 2, "serviceC" to 3))

        val updatedState = startState.withService("serviceB", 2)

        assertEquals(startState, updatedState)
    }

}