package com.spogss.wibb.controller

import com.spogss.wibb.data.GridDisplayable
import com.spogss.wibb.data.Offer

fun WibbController.isFavourite(vararg items: GridDisplayable): Boolean {
    return items.all { favourites.contains(it.text) }
}

fun WibbController.isFavourite(vararg offers: Offer): Boolean {
    return offers.all { isFavourite(it.beer!!) && isFavourite(it.store!!) }
}