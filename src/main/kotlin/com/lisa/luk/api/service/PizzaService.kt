package com.lisa.luk.api.service

import com.lisa.luk.api.domain.ToppingInformation

interface PizzaService {

    fun retrieveToppingInformation(): ToppingInformation?
}