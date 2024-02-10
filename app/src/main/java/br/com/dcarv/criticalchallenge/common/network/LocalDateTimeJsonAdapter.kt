package br.com.dcarv.criticalchallenge.common.network

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.time.LocalDateTime
import java.time.format.DateTimeFormatterBuilder
import java.time.temporal.ChronoField

class LocalDateTimeJsonAdapter {

    @ToJson
    fun toJson(value: LocalDateTime): String {
        return FORMATTER.format(value)
    }

    @FromJson
    fun fromJson(value: String): LocalDateTime {
        println("DFC - value: $value")
        return LocalDateTime.from(FORMATTER.parse(value))
    }

    companion object {

        private val FORMATTER = DateTimeFormatterBuilder()
            .appendPattern("yyyy-MM-dd'T'HH:mm:ss")
            .appendFraction(ChronoField.NANO_OF_SECOND, 0, 9, true)
            .appendZoneId()
            .toFormatter()
    }
}
