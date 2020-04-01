package com.spogss.wibb.controller

import com.spogss.wibb.data.Beer
import com.spogss.wibb.data.Offer
import com.spogss.wibb.data.Store

object WibbController {
    val beers = mutableListOf<Beer>()
    val stores = mutableListOf<Store>()
    val offers = mutableListOf<Offer>()
}