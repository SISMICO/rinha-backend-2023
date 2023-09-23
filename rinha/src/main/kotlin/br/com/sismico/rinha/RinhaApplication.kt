package br.com.sismico.rinha

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class RinhaApplication

fun main(args: Array<String>) {
	runApplication<RinhaApplication>(*args)
}
