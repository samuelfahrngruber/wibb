package com.example.wibb

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wibb.ui.GridRecyclerViewAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.shuhart.stepview.StepView
import com.triggertrap.seekarc.SeekArc
import android.widget.ImageView
import com.example.wibb.data.Brand
import com.example.wibb.data.Offer
import com.example.wibb.data.Store


class AddOfferActivity : AppCompatActivity() {

    val offer = Offer()
    var currentStep = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_offer)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // init step view

        val stepView = findViewById<StepView>(R.id.step_view)
        stepView.setOnStepClickListener { setstep(it) }

        // init store chooser

        val storenames = resources.getStringArray(R.array.store_names)
        val storeicons = resources.obtainTypedArray(R.array.store_icons)

        val stores = List(storenames.size) { Store(storenames[it], storeicons.getResourceId(it, R.drawable.ic_shopping_cart_black_24dp)) }

        val rvs = findViewById<RecyclerView>(R.id.recyclerView_stores)
        val rvsa = GridRecyclerViewAdapter(this, stores)
        rvsa.setSelectionListener {
            setOfferStore(it)
            nextstep()
        }

        rvs.layoutManager = GridLayoutManager(this, 4)
        rvs.adapter = rvsa

        // init beer chooser

        val brandnames = resources.getStringArray(R.array.beer_names)
        val brandicons = resources.obtainTypedArray(R.array.beer_icons)

        val brands = List(brandnames.size) { Brand(brandnames[it], brandicons.getResourceId(it, R.drawable.ic_local_drink_black_24dp)) }

        val rvb = findViewById<RecyclerView>(R.id.recyclerView_beers)
        val rvba = GridRecyclerViewAdapter(this, brands)
        rvba.setSelectionListener {
            setOfferBrand(it)
            nextstep()
        }

        rvb.layoutManager = GridLayoutManager(this, 4)
        rvb.adapter = rvba

        // init seekarc

        val seekArc = findViewById<SeekArc>(R.id.seekArc)
        seekArc.setOnSeekArcChangeListener(object : SeekArc.OnSeekArcChangeListener {
            override fun onProgressChanged(seekArc: SeekArc?, progress: Int, fromUser: Boolean) {
                val prog = progress + 5;
                val progressv = findViewById<TextView>(R.id.seekArcProgress)
                progressv.setText("€" + prog)
                setOfferPrice(prog)
            }

            override fun onStartTrackingTouch(seekArc: SeekArc?) {
            }

            override fun onStopTrackingTouch(seekArc: SeekArc?) {
            }
        })

        val fab = findViewById<FloatingActionButton>(R.id.fab_priceDone)
        fab.setOnClickListener { nextstep() }


    }

    fun setOfferBrand(b: Brand){
        offer.brand = b
        val v = findViewById<View>(R.id.incl_cardView_currentOffer)
        val img = v.findViewById<ImageView>(R.id.offer_card_brand_img)
        val txt = v.findViewById<TextView>(R.id.offer_card_brand_txt)
        img.setImageResource(b.icon)
        txt.text = b.text
    }

    fun setOfferPrice(p: Int){
        offer.price = p
        val v = findViewById<View>(R.id.incl_cardView_currentOffer)
        val txt = v.findViewById<TextView>(R.id.offer_card_price_txt)
        txt.text = "€" + p
    }

    fun setOfferStore(s: Store){
        offer.store = s
        val v = findViewById<View>(R.id.incl_cardView_currentOffer)
        val img = v.findViewById<ImageView>(R.id.offer_card_store_img)
        val txt = v.findViewById<TextView>(R.id.offer_card_store_txt)
        img.setImageResource(s.icon)
        txt.text = s.text
    }

    fun nextstep(){
        setstep(++currentStep)
    }

    fun setstep(step: Int){
        if(step < 0 || step > 3)
            return

        findViewById<StepView>(R.id.step_view).go(step, true)

        if(step != 0)
            findViewById<RecyclerView>(R.id.recyclerView_stores).visibility = View.GONE
        else
            findViewById<RecyclerView>(R.id.recyclerView_stores).visibility = View.VISIBLE

        if(step != 1)
            findViewById<RecyclerView>(R.id.recyclerView_beers).visibility = View.GONE
        else
            findViewById<RecyclerView>(R.id.recyclerView_beers).visibility = View.VISIBLE

        if(step != 2)
            findViewById<LinearLayout>(R.id.linearLayout_price).visibility = View.GONE
        else
            findViewById<LinearLayout>(R.id.linearLayout_price).visibility = View.VISIBLE

        currentStep = step
    }
}
