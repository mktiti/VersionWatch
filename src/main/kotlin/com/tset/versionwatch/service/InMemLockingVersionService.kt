package com.tset.versionwatch.service

import com.tset.versionwatch.model.SystemVersions
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Service
import org.springframework.web.context.WebApplicationContext
import java.util.concurrent.locks.ReentrantReadWriteLock
import kotlin.concurrent.read
import kotlin.concurrent.write

/**
 * Simple lock-based in-memory version management service
 */
@Service
@Scope(WebApplicationContext.SCOPE_APPLICATION)
class InMemLockingVersionService : VersionService {

    private val lock = ReentrantReadWriteLock()
    private var versions: SystemVersions = SystemVersions.Empty

    override fun byVersion(systemVersion: Int) = lock.read {
        versions[systemVersion]
    }

    override fun upsertService(service: String, version: Int) = lock.write {
        versions.withService(service, version).also { newVersion ->
            versions = newVersion
        }
    }
}