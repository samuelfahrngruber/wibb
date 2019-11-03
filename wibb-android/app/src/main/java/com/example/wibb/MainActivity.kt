package com.example.wibb

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wibb.data.Beer
import com.example.wibb.data.Offer
import com.example.wibb.data.Store
import com.example.wibb.ui.OfferCardRecyclerViewAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.time.LocalDate
import kotlin.random.Random

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

        val beerNames = resources.getStringArray(R.array.beer_names)
        val beerIcons = resources.obtainTypedArray(R.array.beer_icons)

        val beer = List(beerNames.size) { Beer(beerNames[it], beerIcons.getResourceId(it, R.drawable.ic_local_drink_black_24dp)) }

        val storenames = resources.getStringArray(R.array.store_names)
        val storeicons = resources.obtainTypedArray(R.array.store_icons)

        val stores = List(storenames.size) { Store(storenames[it], storeicons.getResourceId(it, R.drawable.ic_shopping_cart_black_24dp)) }

        val offers = List(7){
            Offer()
        }

        for(o in offers){
            o.beer = beer.random()
            o.store = stores.random()
            o.end = LocalDate.now()
            o.start = LocalDate.now()
            o.price = Random.nextInt(5, 30)
        }

        val rvo = findViewById<RecyclerView>(R.id.recyclerView_offers)
        val rvoa = OfferCardRecyclerViewAdapter(this, offers)

        rvo.layoutManager = LinearLayoutManager(this)
        rvo.adapter = rvoa
    }


}
