package com.lisa.luk.api.database

import org.ktorm.entity.Entity
import org.ktorm.schema.Table
import org.ktorm.schema.int
import org.ktorm.schema.varchar

interface ToppingCountDTO : Entity<ToppingCountDTO> {
    var name: String
    var totalCount: Int
    var uniqueCount: Int
}

object ToppingCounts : Table<ToppingCountDTO>("t_topping_counts") {
    val name = varchar("topping_name").bindTo { it.name }
    val totalCount = int("total_count").bindTo { it.totalCount }
    val uniqueCount = int("unique_count").bindTo { it.uniqueCount }
}