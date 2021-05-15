package com.spogss.wibb.tools

import com.spogss.wibb.data.FilterKey

object QueryStringSerializer {

    fun serialize(filter: Map<FilterKey, String>): Any? {
        if(filter.isEmpty()) {
            return ""
        }

        return "?" + filter.map { (key, value) -> "${key.camelCase}=$value" }.joinToString("&")
    }

}