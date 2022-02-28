package com.kotlinsamples.stockpricesdemo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import java.time.Duration
import java.time.LocalDateTime
import java.util.*
import java.util.concurrent.ThreadLocalRandom

@SpringBootApplication
class StockPricesDemoApplication

fun main(args: Array<String>) {
    runApplication<StockPricesDemoApplication>(*args)
}

@RestController
class StockServices{

    @GetMapping(value=["/stocks/{symbol}"],produces=[MediaType.TEXT_EVENT_STREAM_VALUE])
    fun prices(@PathVariable symbol: String) : Flux<StockPrices>{

        return Flux.interval(Duration.ofSeconds(40)).map{
            StockPrices(symbol, LocalDateTime.now(),randomPrice())
        }
    }

    fun randomPrice(): Double {
        return ThreadLocalRandom.current().nextDouble(100.0)
    }

}

data class StockPrices(val symbol: String,val timeDate: LocalDateTime,val prices:Double)