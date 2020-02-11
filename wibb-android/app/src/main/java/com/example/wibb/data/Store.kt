package com.example.wibb.data

import org.json.JSONObject

class Store(var name: String, var icon: String): GridDisplayable {

    override val iconurl: String
        get() = icon

    override val text: String
        get() = name

    companion object {
        fun fromJSON(jstore: JSONObject): Store {
            return Store(
                jstore.getString("name"),
                jstore.getString("icon")
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
