package com.spogss.wibb.tools

import android.content.SharedPreferences
import com.spogss.wibb.controller.WibbController
import com.spogss.wibb.data.GridDisplayable
import com.spogss.wibb.data.Offer

object FavouriteFilter {
    const val DEFAULT_FAVOURITE = true

    val favourites = mutableListOf<GridDisplayable>()

    operator fun invoke(item: GridDisplayable): Boolean {
        val res = favourites.any {
            it.text == item.text
        }
        return res
    }

    operator fun invoke(it: Offer): Boolean {
        val b1 = invoke(it.beer!!)
        val b2 = invoke(it.store!!)
        return b1 && b2
    }

    fun readFrom(sp: SharedPreferences) {
        favourites.clear()
        WibbController.beers.forEach {
            if (sp.getBoolean(it.name, DEFAULT_FAVOURITE))
                favourites.add(it)
        }
        WibbController.stores.forEach {
            if (sp.getBoolean(it.name, DEFAULT_FAVOURITE))
                favourites.add(it)
        }
    }
}