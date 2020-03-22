package com.example.wibb

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wibb.controller.WibbController
import com.example.wibb.tools.err.ErrorHandler
import com.example.wibb.ui.OfferCardRecyclerViewAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    private var rvo: RecyclerView? = null
    private var rvoa: OfferCardRecyclerViewAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        try {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main)

            // init fab
            val fab_addOffer: FloatingActionButton = findViewById(R.id.fab_addOffer)

            fab_addOffer.setOnClickListener {
                val intent = Intent(this, AddOfferActivity::class.java)
                startActivity(intent)
            }

            // init offers
            rvo = findViewById<RecyclerView>(R.id.recyclerView_offers)
            rvoa = OfferCardRecyclerViewAdapter(this, WibbController.offers)

            rvo?.layoutManager = LinearLayoutManager(this)
            rvo?.adapter = rvoa
        }
        catch (ex: Exception){
            ErrorHandler.of(this).handle(ex)
        }
    }

    override fun onResume() {
        super.onResume()
        rvoa?.notifyDataSetChanged()
        refreshNoOffersHint()
    }

    // init menu

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.menu_item_settings -> {
                showSettings()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showSettings(){
        val intent =  Intent(this, SettingsActivity::class.java)
        startActivity(intent)
    }

    private fun refreshNoOffersHint(){
        if (WibbController.offers.isEmpty())
            textView_noOffers.visibility = View.VISIBLE
        else
            textView_noOffers.visibility = View.GONE
    }

}
