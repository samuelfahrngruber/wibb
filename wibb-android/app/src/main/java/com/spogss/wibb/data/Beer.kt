package com.spogss.wibb.data

import com.spogss.wibb.data.constants.Constants
import org.json.JSONObject

class Beer(var name: String, var icon: String, var iconBg: String) : GridDisplayable {

    override val iconUrl: String
        get() = icon

    override val text: String
        get() = name

    override val iconBgCol: String
        get() = iconBg

    companion object {
        fun fromJSON(jBeer: JSONObject): Beer {
            val meta =
                if (jBeer.has("meta")) jBeer.getJSONObject("meta")
                else Constants.DEFAULT_META_OBJ
            return Beer(
                jBeer.getString("name"),
                jBeer.getString("icon"),
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
