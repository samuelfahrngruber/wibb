package com.example.wibb.data

import java.time.LocalDate

class Offer {
    var store: Store? = null
    var beer: Beer? = null
    var price: Int = 0
    var start: LocalDate? = null
    var end: LocalDate? = null

    val isValid: Boolean
        get() = price >= 0 && store != null && beer != null && end != null
}
