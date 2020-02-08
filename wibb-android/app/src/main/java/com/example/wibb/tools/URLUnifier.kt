package com.example.wibb.tools

object URLUnifier {
    private var baseUrl = "http://00fc2dfb.ngrok.io"

    fun unifyApiUrl(url: String): String {
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
}