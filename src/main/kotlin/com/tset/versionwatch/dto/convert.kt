package com.tset.versionwatch.dto

import com.tset.versionwatch.model.SystemState
import com.tset.versionwatch.model.VersionedService
import com.tset.versionwatch.dto.VersionedService as VersionedServiceDto

fun VersionedService.toDto() = VersionedServiceDto(first, second)

fun SystemState.toDto(): List<VersionedServiceDto> = serviceVersions.map { (s, v) -> VersionedServiceDto(s, v) }