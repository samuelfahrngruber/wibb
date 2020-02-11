package com.example.wibb

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wibb.controller.WibbController
import com.example.wibb.data.Offer
import com.example.wibb.ui.OfferCardRecyclerViewAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.time.LocalDate
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private var rvo: RecyclerView? = null
    private var rvoa: OfferCardRecyclerViewAdapter? = null

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
        rvo = findViewById<RecyclerView>(R.id.recyclerView_offers)
        rvoa = OfferCardRecyclerViewAdapter(this, WibbController.instance.offers)

        rvo?.layoutManager = LinearLayoutManager(this)
        rvo?.adapter = rvoa
    }

    override fun onResume() {
        super.onResume()
        rvoa?.notifyDataSetChanged()
    }
}
