package com.example.wibb.data

import org.json.JSONObject

class Beer(var name: String, var icon: String): GridDisplayable {

    override val iconUrl: String
        get() = icon

    override val text: String
        get() = name

    companion object {
        fun fromJSON(jBeer: JSONObject): Beer {
            return Beer(
                jBeer.getString("name"),
                jBeer.getString("icon")
            )
        }
    }

    fun toJSON(): JSONObject{
        val jo = JSONObject()
        jo.put("name", this.name)
        jo.put("icon", this.icon)
        return jo
    }
}
