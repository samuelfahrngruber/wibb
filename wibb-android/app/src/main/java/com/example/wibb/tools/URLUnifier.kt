package com.example.wibb.tools

import android.content.Context
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import com.example.wibb.connection.WibbConnection
import java.lang.Exception

class URLUnifier private constructor() {
    private var baseUrl = ""

    private var initialized = false

    companion object {
        val instance = URLUnifier()
    }

    fun initialize(serverURL: String){
        setServerUrl(serverURL)
        initialized = true
    }

    fun setServerUrl(serverURL: String){
        baseUrl = serverURL
    }

    fun unifyApiUrl(url: String): String {
        assertInitialized()

        if(url.startsWith(baseUrl)) return url

        var resurlt = url

        if(!resurlt.startsWith("/"))
            resurlt = "/" + resurlt

        if(!resurlt.startsWith("/api/"))
            resurlt = "/api" + resurlt

        resurlt = baseUrl + resurlt

        return resurlt
    }

    fun unifyImgUrl(url: String): String {
        assertInitialized()

        if(url.startsWith(baseUrl)) return url

        var irl = url
        var resurlt = baseUrl

        if(!irl.startsWith("/"))
            irl = "/" + irl

//        if(!irl.startsWith("/res/"))
//            resurlt += "/res"
//
//        if(!irl.startsWith("/img/"))
//            resurlt += "/img"

        resurlt += irl

        return resurlt
    }

    private fun assertInitialized(){
        if(!initialized)
            throw Exception(URLUnifier::class.qualifiedName + " must be initialized first!")
    }
}