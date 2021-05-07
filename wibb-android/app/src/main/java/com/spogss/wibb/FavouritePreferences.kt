package com.spogss.wibb

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.spogss.wibb.controller.WibbController
import com.spogss.wibb.data.GridDisplayable
import com.spogss.wibb.ui.FavouriteItemAdapter


open class FavouritePreferences : AppCompatActivity {
    private val type: FavouriteType

    constructor(type: FavouriteType) : super() {
        this.type = type
    }

    constructor(type: FavouriteType, contentLayoutId: Int) : super(contentLayoutId) {
        this.type = type
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favourite_preferences)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val values: List<GridDisplayable> =
            if (this.type == FavouriteType.STORE) WibbController.stores
            else WibbController.beers

        val rvs = findViewById<RecyclerView>(R.id.recyclerView_favourites)
        val rvsa = FavouriteItemAdapter(this, values)
        rvs?.layoutManager = LinearLayoutManager(this)
        rvs.adapter = rvsa
    }

    enum class FavouriteType {
        BEER,
        STORE
    }
}


class FavouriteBeerPreferences : FavouritePreferences {
    constructor() : super(FavouriteType.BEER)
    constructor(contentLayoutId: Int) : super(FavouriteType.BEER, contentLayoutId)
}


class FavouriteStorePreferences : FavouritePreferences {
    constructor() : super(FavouriteType.STORE)
    constructor(contentLayoutId: Int) : super(FavouriteType.STORE, contentLayoutId)
}