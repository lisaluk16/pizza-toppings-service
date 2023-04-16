package com.lisa.luk.pipeline.domain

import com.lisa.luk.api.domain.ToppingCount
import org.apache.beam.sdk.coders.Coder
import org.apache.beam.sdk.coders.CoderRegistry
import org.apache.beam.sdk.coders.SerializableCoder

object Coders {

    fun toppingCountCoder(): Coder<ToppingCount> = SerializableCoder.of(ToppingCount::class.java)

    fun registerCoders(coderRegistry: CoderRegistry) {
        coderRegistry.registerCoderForClass(ToppingCount::class.java, toppingCountCoder())
    }

}