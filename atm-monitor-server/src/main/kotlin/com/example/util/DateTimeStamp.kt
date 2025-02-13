package com.example.util

/*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
*/
import kotlinx.datetime.*
import kotlinx.datetime.format.FormatStringsInDatetimeFormats
import kotlinx.datetime.format.byUnicodePattern
import java.time.temporal.ChronoUnit

object DateTimeStamp  {

    /*
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS")

    fun observerStamp(stime : String) = LocalDateTime.parse(stime, formatter)

    fun currentStamp() = LocalDateTime.parse (
                         LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS).format(formatter)  );
   */

    @OptIn(FormatStringsInDatetimeFormats::class)
    val formatter = LocalDateTime.Format { byUnicodePattern("yyyy-MM-dd'T'HH:mm:ss.SSS") }
    fun stamp(stime : String) = LocalDateTime.parse(stime,)

    fun currentTime() =  Clock.System.now()
                         .toLocalDateTime(TimeZone.currentSystemDefault())
                         .format(formatter)


}