package com.example.wibb

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
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
        rvoa = OfferCardRecyclerViewAdapter(this, WibbController.offers)

        rvo?.layoutManager = LinearLayoutManager(this)
        rvo?.adapter = rvoa
    }

    override fun onResume() {
        super.onResume()
        rvoa?.notifyDataSetChanged()
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
        // todo implement settings
        // Toast.makeText(applicationContext, "Settings not supported yet!", Toast.LENGTH_SHORT).show()

        val intent =  Intent(this, SettingsActivity::class.java)
        startActivity(intent)
    }

}
