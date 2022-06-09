package com.tset.versionwatch.service

import com.tset.versionwatch.model.NonEmptySysVer
import com.tset.versionwatch.model.SystemState
import com.tset.versionwatch.model.SystemVersions
import org.springframework.context.annotation.Primary
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Service
import org.springframework.web.context.WebApplicationContext
import java.util.concurrent.atomic.AtomicReference

/**
 * Simple lock-free in-memory version management service
 * Optimized for frequent reads and rare write-collisions (optimistic, but safe concurrency)
 * Used as the default implementation for [VersionService]
 */
@Service
@Scope(WebApplicationContext.SCOPE_APPLICATION)
@Primary
class InMemLockFreeVersionService : VersionService {

    private val versions: AtomicReference<SystemVersions> = AtomicReference(SystemVersions.Empty)

    override fun byVersion(systemVersion: Int): SystemState? {
        val versions: SystemVersions = versions.get()
        return versions[systemVersion]
    }

    override fun upsertService(service: String, version: Int): NonEmptySysVer {
        while (true) {
            val startState: SystemVersions = versions.get()
            val updated = startState.withService(service, version)
            if (updated == startState || versions.compareAndSet(startState, updated)) {
                return updated
            }
        }
    }
}