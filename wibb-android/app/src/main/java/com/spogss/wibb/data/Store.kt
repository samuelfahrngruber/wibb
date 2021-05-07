package com.spogss.wibb.data

import com.spogss.wibb.data.constants.Constants.DEFAULT_META_OBJ
import org.json.JSONObject

class Store(var name: String, var icon: String, var iconBg: String) : GridDisplayable {

    override val iconUrl: String
        get() = icon

    override val text: String
        get() = name

    override val iconBgCol: String
        get() = iconBg

    companion object {
        fun fromJSON(jStore: JSONObject): Store {
            val meta =
                if (jStore.has("meta")) jStore.getJSONObject("meta")
                else DEFAULT_META_OBJ
            return Store(
                jStore.getString("name"),
                jStore.getString("icon"),
                meta.getString("iconBg")
            )
        }
    }

    fun toJSON(): JSONObject {
        val jo = JSONObject()
        val meta = JSONObject()
        meta.put("iconBg", iconBg)
        jo.put("name", this.name)
        jo.put("icon", this.icon)
        jo.put("meta", meta)
        return jo
    }
}
