package com.spogss.wibb.tools.err

import android.content.Context
import android.util.Log
import android.widget.Toast

/**
 * Used to handle errors in the Wibb Application correctly.
 * This will report the error to the Wibb server, log the error
 * and display a toast with the exception message.
 *
 * Usage:
 *
 * try {
 *      // application code
 * }
 * catch (exception: Exception) {
 *      ErrorHandler.of(context).handle(exception)
 * }
 */
class ErrorHandler private constructor(context: Context) {
    private val context: Context = context.applicationContext

    companion object {
        /**
         * Creates an instance of the Wibb error handler with the given context.
         * @return the created instance.
         */
        fun of(context: Context): ErrorHandler {
            return ErrorHandler(context)
        }
    }

    /**
     * Handles a wibb error accordingly.
     * @param throwable A throwable that caused the error.
     * Missing information will be extracted from this throwable (e.g. message).
     */
    fun handle(throwable: Throwable) {
        handle(WibbError.fromThrowable(throwable))
    }

    /**
     * Handles a wibb error accordingly.
     * @param wibbError A error containing all necessary information.
     */
    fun handle(wibbError: WibbError) {
        if (wibbError.occurrenceDescription.isEmpty())
            wibbError.occurrenceDescription =
                "[${context.javaClass.name}]: ${wibbError.occurrenceDescription}"
        inform(wibbError.message)
        wibbError.report()
    }

    /**
     * Handles a error by only providing a message.
     * The occurrence description will be set to "NO_OCCURRENCE_DESC_HANDLER".
     * @param message The error message.
     */
    fun handle(message: String) {
        handle(message, "NO_OCCURRENCE_DESC_HANDLER")
    }

    /**
     * Handles a error by only providing a message and the occurrence description.
     * @param message The error message.
     * @param occDesc The occurrence description describing what was done that the error occurred.
     */
    fun handle(message: String, occDesc: String) {
        val e = WibbError()
        e.message = message
        e.occurrenceDescription = occDesc
        handle(e)
    }

    private fun inform(message: String) {
        Log.e("[ErrorHandler.${context.javaClass.name}]", "message")
        Toast.makeText(context, "ERROR: $message", Toast.LENGTH_SHORT).show()
    }
}