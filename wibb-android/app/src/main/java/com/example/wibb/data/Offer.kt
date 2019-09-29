package com.example.wibb.data

import java.time.LocalDate

class Offer {
    var store: Store? = null
    var brand: Brand? = null
    var price: Int = 0
    var start: LocalDate? = null
    var end: LocalDate? = null

    val isValid: Boolean
        get() = price >= 0 && store != null && brand != null && end != null
}
