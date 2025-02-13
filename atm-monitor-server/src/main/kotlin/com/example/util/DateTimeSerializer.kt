package com.example.util

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

/*
object DateTimeSerializer : KSerializer<LocalDateTime> {

    private  val formatter =  DateTimeStamp.formatter  //DateTimeFormatter.ISO_LOCAL_DATE_TIME
    override val descriptor: SerialDescriptor
        get() = PrimitiveSerialDescriptor("local-date-time", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): LocalDateTime {
       return LocalDateTime.parse(decoder.decodeString(), formatter)
    }

    override fun serialize(encoder: Encoder, value: LocalDateTime) {
          encoder.encodeString( value.format(formatter) )
    }
}*/