package com.example.wibb

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wibb.data.Brand
import com.example.wibb.data.Offer
import com.example.wibb.data.Store
import com.example.wibb.ui.OfferCardRecyclerViewAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.time.LocalDate

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // init fab

        val fab_addOffer: FloatingActionButton = findViewById(R.id.fab_addOffer)

        fab_addOffer.setOnClickListener {
            val intent =  Intent(this, AddOfferActivity::class.java)
            startActivity(intent)
        }

        // init offers

        val brandnames = resources.getStringArray(R.array.beer_names)
        val brandicons = resources.obtainTypedArray(R.array.beer_icons)

        val brands = List(brandnames.size) { Brand(brandnames[it], brandicons.getResourceId(it, R.drawable.ic_local_drink_black_24dp)) }

        val storenames = resources.getStringArray(R.array.store_names)
        val storeicons = resources.obtainTypedArray(R.array.store_icons)

        val stores = List(storenames.size) { Store(storenames[it], storeicons.getResourceId(it, R.drawable.ic_shopping_cart_black_24dp)) }

        val o = Offer()
        o.brand = brands[3]
        o.store = stores[3]
        o.end = LocalDate.now()
        o.start = LocalDate.now()
        o.price = 15

        val offers = listOf(o)

        val rvo = findViewById<RecyclerView>(R.id.recyclerView_offers)
        val rvoa = OfferCardRecyclerViewAdapter(this, offers)

        rvo.layoutManager = LinearLayoutManager(this)
        rvo.adapter = rvoa
    }


}
