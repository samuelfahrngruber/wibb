package com.example.wibb.connection

import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.wibb.controller.WibbController
import com.example.wibb.data.Beer
import com.example.wibb.data.Offer
import com.example.wibb.data.Report
import com.example.wibb.data.Store
import com.example.wibb.tools.URLUnifier
import com.example.wibb.tools.err.WibbError
import org.json.JSONArray
import org.json.JSONObject
import java.lang.Exception

class WibbConnection private constructor() {

    private var initialized: Boolean = false
    private var context: Context? = null
    private var requestQueue: RequestQueue? = null

    companion object {
        val instance = WibbConnection()
    }

    fun initialize(ctx: Context){
        if(!initialized) {
            context = ctx
            requestQueue = Volley.newRequestQueue(context)

            initialized = true
        }
    }

    fun loadAll(cbSuccess: (Boolean) -> Unit){
        // todo implement
    }

    fun loadBeers(cbSuccess: (Boolean) -> Unit){
        var apiurl = URLUnifier.instance.unifyApiUrl("/api/beers")
        getJSONArray(apiurl, {
        WibbController.instance.beers.clear()
            for (i in 0 until it.length()) {
                val item = it.getJSONObject(i)
                WibbController.instance.beers.add(Beer.fromJSON(item))
            }
            cbSuccess(true)
        }, {
            cbSuccess(false)
        })
    }

    fun loadStores(cbSuccess: (Boolean) -> Unit){
        getJSONArray(URLUnifier.instance.unifyApiUrl("/api/stores"), {
            WibbController.instance.stores.clear()
            for (i in 0 until it.length()) {
                val item = it.getJSONObject(i)
                WibbController.instance.stores.add(Store.fromJSON(item))
            }
            cbSuccess(true)
        }, {
            cbSuccess(false)
        })
    }

    fun loadOffers(cbSuccess: (Boolean) -> Unit){
        getJSONArray(URLUnifier.instance.unifyApiUrl("/api/offers"), {
            WibbController.instance.offers.clear()
            for (i in 0 until it.length()) {
                val item = it.getJSONObject(i)
                WibbController.instance.offers.add(Offer.fromJSON(item))
            }
            cbSuccess(true)
        }, {
            cbSuccess(false)
        })
    }

    fun addOffer(offer: Offer, cbSuccess: (Boolean) -> Unit){
        postJSONObject(URLUnifier.instance.unifyApiUrl("/api/offers"), {
            WibbController.instance.offers.add(Offer.fromJSON(it))
            cbSuccess(true)
        }, {
            cbSuccess(false)
        },
        offer.toJSON())
    }

    fun addReport(report: Report, cbSuccess: (Report?) -> Unit){
        postJSONObject(URLUnifier.instance.unifyApiUrl("/api/reports"), {
            cbSuccess(Report.fromJSON(it))
        }, {
            cbSuccess(null)
        },
            report.toJSON())
    }

    fun addWibbError(err: WibbError, cbSuccess: (Boolean) -> Unit){
        postJSONObject(URLUnifier.instance.unifyApiUrl("/api/errors"), {
            cbSuccess(true)
        }, {
            cbSuccess(false)
        },
            err.toJSON(),
            false)
    }

    fun addRequest(requestText: String, cbSuccess: (Boolean) -> Unit){
        val jo = JSONObject()
        jo.put("text", requestText)
        postJSONObject(URLUnifier.instance.unifyApiUrl("/api/requests"), {
            cbSuccess(true)
        }, {
            cbSuccess(false)
        },
            jo)
    }

    private fun getJSONArray(url: String, cbSuccess: (JSONArray) -> Unit, cbError: (VolleyError) -> Unit) {
        getJSONArray(url, cbSuccess, cbError, true)
    }

    private fun getJSONArray(url: String, cbSuccess: (JSONArray) -> Unit, cbError: (VolleyError) -> Unit, reportFailure: Boolean){
        assertInitialized()

        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET, URLUnifier.instance.unifyApiUrl(url), null,
            Response.Listener { response ->
                cbSuccess(response)
            },
            Response.ErrorListener {
                if(reportFailure) WibbError.fromVolleyError(it).report()
                cbError(it)
            }
        )

        requestQueue?.add(jsonArrayRequest)
    }

    private fun postJSONObject(url: String, cbSuccess: (JSONObject) -> Unit, cbError: (VolleyError) -> Unit, jsonObj: JSONObject){
        postJSONObject(url, cbSuccess, cbError, jsonObj, true)
    }

    private fun postJSONObject(url: String, cbSuccess: (JSONObject) -> Unit, cbError: (VolleyError) -> Unit, jsonObj: JSONObject, reportFailure: Boolean){
        assertInitialized()

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST, URLUnifier.instance.unifyApiUrl(url),
            jsonObj,
            Response.Listener { response ->
                cbSuccess(response)
            },
            Response.ErrorListener {
                if(reportFailure) WibbError.fromVolleyError(it).report()
                cbError(it)
            }
        )

        requestQueue?.add(jsonObjectRequest)
    }

    private fun assertInitialized(){
        if(!initialized)
            throw Exception(WibbConnection::class.qualifiedName + " must be initialized first!")
    }
}