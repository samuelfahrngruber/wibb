package com.spogss.wibb.tools

import java.lang.Exception

object URLUnifier {

    private var baseUrl = ""
    private var initialized = false
    private var resourceTheme: String? = null

    fun initialize(serverURL: String, resourceTheme: String? = null){
        setServerUrl(serverURL)
        initialized = true
        this.resourceTheme = resourceTheme
    }

    fun setServerUrl(serverURL: String){
        baseUrl = serverURL
    }

    fun setResourceTheme(theme: String) {
        resourceTheme = theme
    }

    fun unifyApiUrl(url: String): String {
        assertInitialized()

        if(url.startsWith(baseUrl)) return url

        var resurlt = url

        if(!resurlt.startsWith("/"))
            resurlt = "/$resurlt"

        if(!resurlt.startsWith("/api/"))
            resurlt = "/api$resurlt"

        resurlt = baseUrl + resurlt

        return resurlt
    }

    fun unifyImgUrl(url: String): String {
        assertInitialized()

        if(url.startsWith(baseUrl)) return url

        var irl = url
        var resurlt = baseUrl

        if(!irl.startsWith("/"))
            irl = "/$irl"

        if(resourceTheme != null)
            irl = irl.replace("/res/", "/res/theme/$resourceTheme/")

        resurlt += irl

        return resurlt
    }

    private fun assertInitialized(){
        if(!initialized)
            throw Exception("${URLUnifier::class.qualifiedName} must be initialized first!")
    }
}