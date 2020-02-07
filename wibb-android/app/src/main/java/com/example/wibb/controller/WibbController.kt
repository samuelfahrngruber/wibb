package com.example.wibb.controller

import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.example.wibb.connection.WibbConnection
import com.example.wibb.data.Beer
import com.example.wibb.data.Offer
import com.example.wibb.data.Store
import com.example.wibb.tools.URLUnifier
import org.json.JSONArray

class WibbController private constructor() {
    val beers = mutableListOf<Beer>()
    val stores = mutableListOf<Store>()
    val offers = mutableListOf<Offer>()

    companion object {
        val instance = WibbController()
    }
}