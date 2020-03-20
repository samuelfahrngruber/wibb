package com.example.wibb.data

import org.json.JSONObject

class Report(t: RType, i: String, o: Offer) {
    var type: RType = t
    var info: String = i
    var offer: Offer = o

    var id: String? = null

    enum class RType {
        FAKE, INCORRECT_DATES, DISPLAY_PROBLEM, OTHER
    }

    fun toJSON(): JSONObject {
        val jo = JSONObject()
        jo.put("type", this.type.name)
        jo.put("info", this.info)
        jo.put("offer", this.offer.toJSON())
        return jo
    }

    companion object {
        fun fromJSON(jReport: JSONObject): Report {
            val t = RType.valueOf(jReport.getString("type"))
            val i = jReport.getString("info")
            val o = Offer.fromJSON(jReport.getJSONObject("offer"))
            val r = Report(t, i, o)
            r.id = jReport.getString("_id")
            return r
        }
    }
}