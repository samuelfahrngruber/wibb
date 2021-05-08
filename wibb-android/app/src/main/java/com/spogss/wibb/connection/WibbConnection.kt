package com.spogss.wibb.connection

import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
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
import org.json.JSONObject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

object WibbConnection {

    private var initialized: Boolean = false
    private lateinit var requestQueue: RequestQueue

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

    /**
     * Gets all available beer brands from the server
     * @throws WibbConnectionNotInitializedException when the connection is not initialized yet.
     */
    suspend fun getBeers(): List<Beer> {
        return getList(URLUnifier.unifyApiUrl("/api/beers")) { jsonObject ->
            Beer.fromJSON(
                jsonObject
            )
        }
    }


    /**
     * Gets all available stores from the server
     * @throws WibbConnectionNotInitializedException when the connection is not initialized yet.
     */
    suspend fun getStores(): List<Store> {
        return getList(URLUnifier.unifyApiUrl("/api/stores")) { jsonObject ->
            Store.fromJSON(
                jsonObject
            )
        }
    }

    /**
     * Gets all available offers from the server
     * @throws WibbConnectionNotInitializedException when the connection is not initialized yet.
     */
    suspend fun getOffers(): List<Offer> {
        return getList(URLUnifier.unifyApiUrl("/api/offers")) { jsonObject ->
            Offer.fromJSON(
                jsonObject
            )
        }
    }

    private suspend fun <T> getList(apiUrl: String, parser: (JSONObject) -> T) =
        suspendCoroutine<List<T>> { continuation ->
            assertInitialized()

            val request = JsonArrayRequest(URLUnifier.unifyApiUrl(apiUrl),
                { jsonArray ->
                    val list = List(
                        jsonArray.length()
                    ) { index -> jsonArray.getJSONObject(index) }.map { item ->
                        parser(item)
                    }

                    continuation.resume(list)
                },
                { error -> continuation.resumeWithException(error) })
            requestQueue.add(request)
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
            { response ->
                cbSuccess(response)
            },
            { error ->
                if (reportFailure) WibbError.fromVolleyError(error).report()
                cbError(error)
            }
        )

        requestQueue.add(jsonObjectRequest)
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