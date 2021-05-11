package com.spogss.wibb.tools

object URLUnifier {

    private var baseUrl = ""
    private var initialized = false
    private var resourceTheme: String? = null

    fun initialize(serverURL: String, resourceTheme: String? = null) {
        setServerUrl(serverURL)
        initialized = true
        this.resourceTheme = resourceTheme
    }

    private fun setServerUrl(serverURL: String) {
        baseUrl = serverURL
    }

    fun setResourceTheme(theme: String) {
        resourceTheme = theme
    }

    fun unifyApiUrl(url: String): String {
        assertInitialized()

        if (url.startsWith(baseUrl)) return url

        var result = url

        if (!result.startsWith("/"))
            result = "/$result"

        if (!result.startsWith("/api/"))
            result = "/api$result"

        result = baseUrl + result

        return result
    }

    fun unifyImgUrl(url: String): String {
        assertInitialized()

        if (url.startsWith(baseUrl)) return url

        var irl = url
        var result = baseUrl

        if (!irl.startsWith("/"))
            irl = "/$irl"

        if (resourceTheme != null)
            irl = irl.replace("/res/", "/res/theme/$resourceTheme/")

        result += irl

        return result
    }

    private fun assertInitialized() {
        if (!initialized)
            throw Exception("${URLUnifier::class.qualifiedName} must be initialized first!")
    }
}