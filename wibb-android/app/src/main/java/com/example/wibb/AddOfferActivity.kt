package com.example.wibb

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wibb.ui.GridRecyclerViewAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.shuhart.stepview.StepView
import com.triggertrap.seekarc.SeekArc
import com.archit.calendardaterangepicker.customviews.DateRangeCalendarView
import com.bumptech.glide.Glide
import com.example.wibb.connection.WibbConnection
import com.example.wibb.controller.WibbController
import com.example.wibb.data.Beer
import com.example.wibb.data.Offer
import com.example.wibb.data.Store
import com.example.wibb.tools.URLUnifier
import kotlinx.android.synthetic.main.activity_add_offer.*
import java.util.*
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter


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
        val stores = WibbController.instance.stores

        val rvs = findViewById<RecyclerView>(R.id.recyclerView_stores)
        val rvsa = GridRecyclerViewAdapter(this, stores)
        rvsa.setSelectionListener {
            setOfferStore(it)
            nextstep()
        }

        rvs.layoutManager = GridLayoutManager(this, 4)
        rvs.adapter = rvsa

        // init beer chooser
        val beers = WibbController.instance.beers

        val rvb = findViewById<RecyclerView>(R.id.recyclerView_beers)
        val rvba = GridRecyclerViewAdapter(this, beers)
        rvba.setSelectionListener {
            setOfferBeer(it)
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
                progressv.text = "€$prog"
                setOfferPrice(prog)
            }

            override fun onStartTrackingTouch(seekArc: SeekArc?) {
            }

            override fun onStopTrackingTouch(seekArc: SeekArc?) {
            }
        })

        val fab = findViewById<FloatingActionButton>(R.id.fab_priceDone)
        fab.setOnClickListener { nextstep() }

        // init calendarpicker

        calendar_range.setSelectedDateRange(Calendar.getInstance(), null)
        calendar_range.setCalendarListener(object : DateRangeCalendarView.CalendarListener {
            override fun onFirstDateSelected(startDate: Calendar) {
                val startDateLocal = LocalDateTime.ofInstant(startDate.toInstant(), startDate.getTimeZone().toZoneId()).toLocalDate()
                setOfferstartDate(startDateLocal)
            }
            override fun onDateRangeSelected(startDate: Calendar, endDate: Calendar) {
                val startDateLocal = LocalDateTime.ofInstant(startDate.toInstant(), ZoneId.systemDefault()).toLocalDate()
                val endDateLocal = LocalDateTime.ofInstant(endDate.toInstant(), ZoneId.systemDefault()).toLocalDate()
                setOfferDates(startDateLocal, endDateLocal)
            }
        })

        // init menu
        setOfferMenu()
    }

    fun setOfferBeer(b: Beer){
        offer.beer = b
        val v = findViewById<View>(R.id.incl_cardView_currentOffer)
        val img = v.findViewById<ImageView>(R.id.offer_card_beer_img)
        val txt = v.findViewById<TextView>(R.id.offer_card_beer_txt)
        Glide.with(this)
            .load(URLUnifier.instance.unifyImgUrl(b.icon))
            .into(img)
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
        Glide.with(this)
            .load(URLUnifier.instance.unifyImgUrl(s.icon))
            .into(img)
        txt.text = s.text
    }

    fun setOfferMenu(){
        val v = findViewById<View>(R.id.incl_cardView_currentOffer)
        val btn = v.findViewById<ImageButton>(R.id.offer_card_menu_btn)
        btn.setImageResource(R.drawable.ic_done_black_24dp)

        btn.setOnClickListener {
            submitNewOffer()
        }
    }

    fun setOfferstartDate(startDate: LocalDate){
        offer.startDate = startDate
        updateOfferDates()
    }

    fun setOfferEndDate(endDate: LocalDate){
        offer.endDate = endDate
        updateOfferDates()
    }

    fun setOfferDates(startDate: LocalDate, endDate: LocalDate){
        offer.startDate = startDate
        offer.endDate = endDate
        updateOfferDates()
    }

    fun updateOfferDates(){
        val v = findViewById<View>(R.id.incl_cardView_currentOffer)
        val txt = v.findViewById<TextView>(R.id.offer_card_date_txt)
        val formatter = DateTimeFormatter.ofPattern("E, d")
        txt.text = offer.startDate?.format(formatter) + " - " + offer.endDate?.format(formatter)
    }

    fun nextstep(){
        setstep(++currentStep)
    }

    fun submitNewOffer(){
        if (offer.isValid)
            WibbConnection.instance.addOffer(offer){
                if(it /*worked*/){
                    Toast.makeText(this.applicationContext, R.string.toast_newOffer_success, Toast.LENGTH_SHORT).show()
                    finish()
                }
                else{
                    Toast.makeText(this.applicationContext, R.string.toast_newOffer_fail, Toast.LENGTH_SHORT).show()
                }
            }
        else
            Toast.makeText(this.applicationContext, R.string.toast_newOffer_invalid, Toast.LENGTH_SHORT).show()
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

        if(step != 3)
            findViewById<DateRangeCalendarView>(R.id.calendar_range).visibility = View.GONE
        else
            findViewById<DateRangeCalendarView>(R.id.calendar_range).visibility = View.VISIBLE


        currentStep = step
    }
}
