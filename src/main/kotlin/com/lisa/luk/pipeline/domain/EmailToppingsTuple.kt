package com.lisa.luk.pipeline.domain

import java.io.Serializable
data class EmailToppingsTuple(
    val first: String,
    val second: List<String>
) : Serializable