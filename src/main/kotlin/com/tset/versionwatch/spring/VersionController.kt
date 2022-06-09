package com.tset.versionwatch.spring

import com.tset.versionwatch.dto.VersionedService
import com.tset.versionwatch.dto.toDto
import com.tset.versionwatch.service.VersionService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController("/")
class VersionController(
    private val versionService: VersionService
) {

    companion object {
        private fun <T> T?.asResponse(): ResponseEntity<T> = when (this) {
            null -> ResponseEntity.notFound().build()
            else -> ResponseEntity.ok(this)
        }
    }

    @PostMapping("deploy")
    fun onDeploy(@RequestBody deployed: VersionedService): Int {
        return versionService.upsertService(deployed.name, deployed.version).currentState.systemVersion
    }

    @GetMapping("services")
    fun byVersion(@RequestParam systemVersion: Int): ResponseEntity<List<VersionedService>> {
        return versionService[systemVersion]?.toDto().asResponse()
    }

}