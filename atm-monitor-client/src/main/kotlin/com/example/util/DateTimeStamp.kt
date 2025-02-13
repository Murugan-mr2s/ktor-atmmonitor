package com.example.util
import kotlinx.datetime.*
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format.FormatStringsInDatetimeFormats
import kotlinx.datetime.format.byUnicodePattern

object DateTimeStamp  {


    @OptIn(FormatStringsInDatetimeFormats::class)
    val formatter = LocalDateTime.Format { byUnicodePattern("yyyy-MM-dd'T'HH:mm:ss.SSS") }

    fun currentTime() =  Clock.System.now()
        .toLocalDateTime(TimeZone.currentSystemDefault())
        .format(formatter)


}