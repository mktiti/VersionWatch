package com.tset.versionwatch.service

import com.tset.versionwatch.model.NonEmptySysVer
import com.tset.versionwatch.model.SystemState

/**
 * Service interface for version management
 */
interface VersionService {

    fun byVersion(systemVersion: Int): SystemState?

    operator fun get(systemVersion: Int) = byVersion(systemVersion)

    fun upsertService(service: String, version: Int): NonEmptySysVer

}