package com.spogss.wibb.data

import org.json.JSONObject

class Store(var name: String, var icon: String): GridDisplayable {

    override val iconUrl: String
        get() = icon

    override val text: String
        get() = name

    companion object {
        fun fromJSON(jStore: JSONObject): Store {
            return Store(
                jStore.getString("name"),
                jStore.getString("icon")
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
