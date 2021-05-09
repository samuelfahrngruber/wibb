package com.spogss.wibb.controller

import com.spogss.wibb.data.Beer
import com.spogss.wibb.data.GridDisplayable
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
    val favourites = mutableListOf<GridDisplayable>()

    fun setBeers(newBeers: List<Beer>) {
        beers.clear()
        beers.addAll(newBeers)
    }

    fun setStores(newStores: List<Store>) {
        stores.clear()
        stores.addAll(newStores)
    }

    fun setOffers(newOffers: List<Offer>) {
        offers.clear()
        offers.addAll(newOffers)
    }

    fun setFavourites(newFavourites: List<GridDisplayable>) {
        favourites.clear()
        favourites.addAll(newFavourites)
    }
}