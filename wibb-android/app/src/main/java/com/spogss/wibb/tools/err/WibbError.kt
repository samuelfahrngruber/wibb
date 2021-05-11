package com.spogss.wibb.tools.err

import com.android.volley.VolleyError
import com.spogss.wibb.connection.WibbConnection
import org.json.JSONObject

/**
 * An error that occurred in the wibb application.
 * Should always be handled by [ErrorHandler].
 */
class WibbError {

    var occurrenceDescription: String = ""
    var message: String = ""
    var stackTrace: String = ""
    var throwable: Throwable? = null

    companion object {

        /**
         * Creates a wibb error by just providing a throwable.
         * The message will be extracted,
         * the occurrence description will be "OCCURRENCE_DESCRIPTION_NOT_SPECIFIED".
         * @param t The throwable to create this wibb error on.
         * @return the newly created wibb error.
         */
        fun fromThrowable(t: Throwable): WibbError {
            return fromThrowable(t, "OCCURRENCE_DESCRIPTION_NOT_SPECIFIED")
        }

        /**
         * Creates a wibb error by just providing a throwable.
         * The message will be extracted,
         * @param t The throwable to create this wibb error on.
         * @param occDesc The description how the error occurred.
         * @return the newly created wibb error.
         */
        private fun fromThrowable(t: Throwable, occDesc: String): WibbError {
            val e = WibbError()
            e.occurrenceDescription = occDesc
            e.message = if (t.message == null) "ERROR_MESSAGE_NULL" else t.message!!
            e.stackTrace = t.stackTrace.joinToString { "\n> " }
            e.throwable = t
            return e
        }

        /**
         * Creates a wibb error from a Volley connection error
         * @param err The volley error to create this wibb error on.
         * @return the newly created wibb error.
         */
        fun fromVolleyError(err: VolleyError): WibbError {
            val e = WibbError()
            e.occurrenceDescription = "VOLLEY_CONNECTION_ERROR"
            e.message = if (err.message == null) "VOLLEY_ERROR_MESSAGE_NULL" else err.message!!
            e.stackTrace = err.stackTrace.joinToString { "\n> " }
            e.throwable = err.cause
            return e
        }
    }

    /**
     * Serializes this error into JSON.
     * @return The json object representing this error.
     */
    fun toJSON(): JSONObject {
        val jo = JSONObject()
        jo.put("occurrenceDescription", this.occurrenceDescription)
        jo.put("message", this.message)
        jo.put("stackTrace", this.stackTrace)
        return jo
    }

    /**
     * Reports this error.
     * @see WibbConnection.addWibbError
     */
    fun report() {
        WibbConnection.addWibbError(this) {}
    }
}