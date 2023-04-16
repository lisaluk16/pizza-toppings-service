package com.lisa.luk.api.extensions

import com.lisa.luk.api.domain.ToppingCount

/**
 * Returns a list of just the names of the ToppingCounts found in the database
 */
fun List<ToppingCount>.getToppingNames(): List<String> = this.map { it.name }

/**
 * Determines the total count of the most popular topping(s) and returns a list of one or more
 */
fun List<ToppingCount>.getMostPopularToppings(): List<ToppingCount> {
    val mostPopularCount = this[0].totalCount
    return this.filter { it.totalCount == mostPopularCount }
}

/**
 * Determines the total count of the least popular topping(s) and returns a list of one or more
 */
fun List<ToppingCount>.getLeastPopularToppings(): List<ToppingCount> {
    val leastPopularCount = this[this.size - 1].totalCount
    return this.filter { it.totalCount == leastPopularCount }
}