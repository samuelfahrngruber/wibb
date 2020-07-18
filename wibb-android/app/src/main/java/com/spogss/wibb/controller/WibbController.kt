package com.spogss.wibb.controller

import com.spogss.wibb.data.Beer
import com.spogss.wibb.data.Offer
import com.spogss.wibb.data.Store

/**
 * Class that contains the current state of the application.
 * Mostly modified by [com.spogss.wibb.connection.WibbConnection].
 * Should not be modified from any other class.
 */
object WibbController {
    val beers = mutableListOf<Beer>()
    val stores = mutableListOf<Store>()
    val offers = mutableListOf<Offer>()
}