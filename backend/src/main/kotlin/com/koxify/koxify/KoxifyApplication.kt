package com.koxify.koxify

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class KoxifyApplication

fun main(args: Array<String>) {
	runApplication<KoxifyApplication>(*args)
}
