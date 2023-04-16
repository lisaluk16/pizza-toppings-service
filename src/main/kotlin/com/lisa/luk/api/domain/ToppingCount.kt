package com.lisa.luk.api.domain

import java.io.Serializable

data class ToppingCount(
    val name: String,
    var totalCount: Int,
    var uniqueCount: Int
): Serializable