package com.example.wibb.data

class Brand(var name: String, var icon: Int): GridDisplayable {

    override val drawable: Int
        get() = icon

    override val text: String
        get() = name
}
