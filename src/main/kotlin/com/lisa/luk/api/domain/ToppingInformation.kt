package com.lisa.luk.api.domain

import java.io.Serializable

data class ToppingInformation(
    val toppings: List<ToppingCount>,
    val mostPopularToppings: List<String>,
    val leastPopularToppings: List<String>
): Serializable
