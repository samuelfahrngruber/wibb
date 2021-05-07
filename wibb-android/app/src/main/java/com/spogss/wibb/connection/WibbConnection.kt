package com.spogss.wibb.connection

import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.spogss.wibb.controller.WibbController
import com.spogss.wibb.data.Beer
import com.spogss.wibb.data.Offer
import com.spogss.wibb.data.Report
import com.spogss.wibb.data.Store
import com.spogss.wibb.tools.URLUnifier
import com.spogss.wibb.tools.err.WibbError
import org.json.JSONArray
import org.json.JSONObject

object WibbConnection {

    private var initialized: Boolean = false
    private var requestQueue: RequestQueue? = null

    /**
     * Has to be called in order for the connection to be properly established.
     * The context is needed for the HttpRequests sent with Volley.
     */
    fun initialize(ctx: Context) {
        if (!initialized) {
            requestQueue = Volley.newRequestQueue(ctx)

            initialized = true
        }
    }

    fun loadAll(cbSuccess: (Boolean) -> Unit) {
        // TODO implement
    }

    /**
     * Loads all available beer brands from the server
     * and stores them in [WibbController.beers]
     * @param cbSuccess The callback to be executed after the operation completes.
     *                  It receives the success status in form of a boolean.
     * @throws WibbConnectionNotInitializedException when the connection is not initialized yet.
     */
    fun loadBeers(cbSuccess: (Boolean) -> Unit) {
        val apiurl = URLUnifier.unifyApiUrl("/api/beers")
        getJSONArray(apiurl, {
            WibbController.beers.clear()
            for (i in 0 until it.length()) {
                val item = it.getJSONObject(i)
                WibbController.beers.add(Beer.fromJSON(item))
            }
            cbSuccess(true)
        }, {
            cbSuccess(false)
        })
    }

    /**
     * Loads all available stores from the server
     * and stores them in [WibbController.stores]
     * @param cbSuccess The callback to be executed after the operation completes.
     *                  It receives the success status in form of a boolean.
     * @throws WibbConnectionNotInitializedException when the connection is not initialized yet.
     */
    fun loadStores(cbSuccess: (Boolean) -> Unit) {
        getJSONArray(URLUnifier.unifyApiUrl("/api/stores"), {
            WibbController.stores.clear()
            for (i in 0 until it.length()) {
                val item = it.getJSONObject(i)
                WibbController.stores.add(Store.fromJSON(item))
            }
            cbSuccess(true)
        }, {
            cbSuccess(false)
        })
    }

    /**
     * Loads all available offers from the server
     * and stores them in [WibbController.offers]
     * @param cbSuccess The callback to be executed after the operation completes.
     *                  It receives the success status in form of a boolean.
     * @throws WibbConnectionNotInitializedException when the connection is not initialized yet.
     */
    fun loadOffers(cbSuccess: (Boolean) -> Unit) {
        getJSONArray(URLUnifier.unifyApiUrl("/api/offers"), {
            WibbController.offers.clear()
            for (i in 0 until it.length()) {
                val item = it.getJSONObject(i)
                WibbController.offers.add(Offer.fromJSON(item))
            }
            cbSuccess(true)
        }, {
            cbSuccess(false)
        })
    }

    /**
     * Sends a POST Request with the specified Offer to the server.
     * @param offer The offer to be sent.
     * @param cbSuccess The callback to be executed after the operation completes.
     *                  It receives the success status in form of a boolean.
     * @throws WibbConnectionNotInitializedException when the connection is not initialized yet.
     */
    fun addOffer(offer: Offer, cbSuccess: (Boolean) -> Unit) {
        postJSONObject(URLUnifier.unifyApiUrl("/api/offers"), {
            if (it.length() > 0)
                WibbController.offers.add(Offer.fromJSON(it))
            cbSuccess(true)
        }, {
            cbSuccess(false)
        },
            offer.toJSON()
        )
    }

    /**
     * Sends a POST Request with the specified Report to the server.
     * Reports are used to report incorrect offers.
     * @param report The report to be sent.
     * @param cbSuccess The callback to be executed after the operation completes.
     *                  It receives the report in form of [Report] or null if it was not successful.
     * @throws WibbConnectionNotInitializedException when the connection is not initialized yet.
     */
    fun addReport(report: Report, cbSuccess: (Report?) -> Unit) {
        postJSONObject(URLUnifier.unifyApiUrl("/api/reports"), {
            cbSuccess(Report.fromJSON(it))
        }, {
            cbSuccess(null)
        },
            report.toJSON()
        )
    }

    /**
     * Reports an error to the server.
     * @param error The offer to be sent.
     * @param cbSuccess The callback to be executed after the operation completes.
     *                  It receives the success status in form of a boolean.
     * @throws WibbConnectionNotInitializedException when the connection is not initialized yet.
     */
    fun addWibbError(error: WibbError, cbSuccess: (Boolean) -> Unit){
        postJSONObject(URLUnifier.unifyApiUrl("/api/errors"), {
            cbSuccess(true)
        }, {
            cbSuccess(false)
        },
            error.toJSON(),
            false
        )
    }

    /**
     * Sends a request text to the server used for suggesting features or reporting bugs.
     * @param requestText The text to be sent to the server.
     * @param cbSuccess The callback to be executed after the operation completes.
     *                  It receives the success status in form of a boolean.
     * @throws WibbConnectionNotInitializedException when the connection is not initialized yet.
     */
    fun addRequest(requestText: String, cbSuccess: (Boolean) -> Unit) {
        val jo = JSONObject()
        jo.put("text", requestText)
        postJSONObject(URLUnifier.unifyApiUrl("/api/requests"), {
            cbSuccess(true)
        }, {
            cbSuccess(false)
        },
            jo
        )
    }

    private fun getJSONArray(
        url: String,
        cbSuccess: (JSONArray) -> Unit,
        cbError: (VolleyError) -> Unit
    ) {
        WibbConnection.getJSONArray(url, cbSuccess, cbError, true)
    }

    private fun getJSONArray(
        url: String,
        cbSuccess: (JSONArray) -> Unit,
        cbError: (VolleyError) -> Unit,
        reportFailure: Boolean
    ) {
        assertInitialized()

        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET, URLUnifier.unifyApiUrl(url), null,
            Response.Listener { response ->
                cbSuccess(response)
            },
            Response.ErrorListener { error ->
                if (reportFailure) WibbError.fromVolleyError(error).report()
                cbError(error)
            }
        )

        requestQueue?.add(jsonArrayRequest)
    }

    private fun postJSONObject(
        url: String,
        cbSuccess: (JSONObject) -> Unit,
        cbError: (VolleyError) -> Unit,
        jsonObj: JSONObject
    ) {
        postJSONObject(url, cbSuccess, cbError, jsonObj, true)
    }

    private fun postJSONObject(
        url: String,
        cbSuccess: (JSONObject) -> Unit,
        cbError: (VolleyError) -> Unit,
        jsonObj: JSONObject,
        reportFailure: Boolean
    ) {
        assertInitialized()

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST, URLUnifier.unifyApiUrl(url),
            jsonObj,
            Response.Listener { response ->
                cbSuccess(response)
            },
            Response.ErrorListener { error ->
                if (reportFailure) WibbError.fromVolleyError(error).report()
                cbError(error)
            }
        )

        requestQueue?.add(jsonObjectRequest)
    }

    private fun assertInitialized() {
        if (!initialized) {
            val connectionNotInitializedException =
                WibbConnectionNotInitializedException(WibbConnection::class.qualifiedName + " must be initialized first!")
            WibbError.fromThrowable(connectionNotInitializedException).report()
            throw connectionNotInitializedException
        }
    }
}