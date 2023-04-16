package com.lisa.luk.api.database

import com.lisa.luk.api.domain.ToppingCount

interface ToppingRepository {
    fun getToppingCounts(): List<ToppingCount>

}