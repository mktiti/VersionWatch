package com.tset.versionwatch.spring

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@ComponentScan("com.tset.versionwatch")
class VersionWatchApplication

fun main(args: Array<String>) {
    runApplication<VersionWatchApplication>(*args)
}
