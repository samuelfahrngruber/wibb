package com.spogss.wibb

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.spogss.wibb.controller.WibbController
import com.spogss.wibb.tools.err.ErrorHandler
import com.spogss.wibb.ui.OfferCardRecyclerViewAdapter
import kotlinx.android.synthetic.main.activity_main.*

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
        } catch (ex: Exception) {
            ErrorHandler.of(this).handle(ex)
        }
    }

    override fun onResume() {
        super.onResume()
        rvoa?.notifyWibbDataChanged()
        refreshNoOffersHint()
        card_invalid_hint.visibility =
            if (WibbController.offers.any { it.endDate == null }) View.VISIBLE else View.GONE
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

    private fun showSettings() {
        val intent = Intent(this, SettingsActivity::class.java)
        startActivity(intent)
    }

    private fun refreshNoOffersHint() {
        if (rvoa!!.itemCount <= 0)
            textView_noOffers.visibility = View.VISIBLE
        else
            textView_noOffers.visibility = View.GONE
    }

    fun hideInvalidHint(view: View) {
        val animationOffset =
            card_invalid_hint.height + (card_invalid_hint.layoutParams as ViewGroup.MarginLayoutParams).topMargin * 2
        linearLayout.animate()
            .translationY(-animationOffset.toFloat())
            .setDuration(300)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    super.onAnimationEnd(animation)
                    linearLayout.translationY = 0.0.toFloat()
                    card_invalid_hint.visibility = View.GONE
                }
            })
    }
}
