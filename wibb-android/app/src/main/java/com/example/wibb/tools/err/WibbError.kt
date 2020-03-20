package com.example.wibb.tools.err

import com.android.volley.VolleyError
import com.example.wibb.connection.WibbConnection
import org.json.JSONObject

class WibbError {
    var occurrenceDescription: String = ""
    var message: String = ""
    var stackTrace: String = ""
    var throwable: Throwable? = null

    companion object {
        fun fromThrowable(t: Throwable): WibbError {
            return Companion.fromThrowable(t, "OCCURRENCE_DESCRIPTION_NOT_SPECIFIED")
        }

        fun fromThrowable(t: Throwable, occDesc: String): WibbError{
            val e = WibbError()
            e.occurrenceDescription = occDesc
            e.message = if(t.message == null) "ERROR_MESSAGE_NULL" else t.message!!
            e.stackTrace = if(t.stackTrace == null) "ERROR_STACKTRACE_NULL" else t.stackTrace.joinToString { "\n> " }
            e.throwable = t
            return e
        }

        fun fromVolleyError(err: VolleyError): WibbError{
            val e = WibbError()
            e.occurrenceDescription = "VOLLEY_CONNECTION_ERROR"
            e.message = if(err.message == null) "VOLLEY_ERROR_MESSAGE_NULL" else err.message!!
            e.stackTrace = if(err.stackTrace == null) "VOLLEY_ERROR_STACKTRACE_NULL" else err.stackTrace.joinToString { "\n> " }
            e.throwable = err.cause
            return e
        }
    }

    fun toJSON(): JSONObject{
        val jo = JSONObject()
        jo.put("occurrenceDescription", this.occurrenceDescription)
        jo.put("message", this.message)
        jo.put("stackTrace", this.stackTrace)
        return jo
    }

    fun report(){
        WibbConnection.addWibbError(this){}
    }
}