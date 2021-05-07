package com.spogss.wibb.data

import org.json.JSONObject
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class Offer {
    var store: Store? = null
    var beer: Beer? = null
    var price: Int = -1
    var startDate: LocalDate? = null
    var endDate: LocalDate? = null

    val isValid: Boolean
        get() = price >= 0 && store != null && beer != null

    companion object {
        fun fromJSON(jOffer: JSONObject): Offer {
            val o = Offer()
            o.beer = Beer.fromJSON(jOffer.getJSONObject("beer"))
            o.store = Store.fromJSON(jOffer.getJSONObject("store"))
            if (jOffer.has("startDate") && !jOffer.isNull("startDate")) {
                val zonedStart = ZonedDateTime.parse(
                    jOffer.getString("startDate"),
                    DateTimeFormatter.ISO_DATE_TIME
                )
                o.startDate =
                    if (jOffer.isNull("startDate")) null else zonedStart.withZoneSameInstant(ZoneId.systemDefault())
                        .toLocalDate()
            }
            if (jOffer.has("endDate") && !jOffer.isNull("endDate")) {
                val zonedEnd = ZonedDateTime.parse(
                    jOffer.getString("endDate"),
                    DateTimeFormatter.ISO_DATE_TIME
                )
                o.endDate =
                    if (jOffer.isNull("endDate")) null else zonedEnd.withZoneSameInstant(ZoneId.systemDefault())
                        .toLocalDate()
            }
            o.price = jOffer.getInt("price")
            return o
        }
    }

    fun toJSON(): JSONObject {
        val jo = JSONObject()
        jo.put("beer", this.beer?.toJSON())
        jo.put("store", this.store?.toJSON())
        jo.put(
            "startDate",
            this.startDate?.atStartOfDay(ZoneId.systemDefault())?.toOffsetDateTime()
                ?.format(DateTimeFormatter.ISO_DATE_TIME)
        )
        jo.put(
            "endDate",
            this.endDate?.atTime(LocalTime.MAX)?.atZone(ZoneId.systemDefault())?.toOffsetDateTime()
                ?.format(DateTimeFormatter.ISO_DATE_TIME)
        )
        jo.put("price", this.price)
        return jo
    }
}
