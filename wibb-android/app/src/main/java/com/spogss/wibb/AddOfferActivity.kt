package com.spogss.wibb

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.archit.calendardaterangepicker.customviews.DateRangeCalendarView
import com.bumptech.glide.Glide
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.shuhart.stepview.StepView
import com.spogss.wibb.connection.WibbConnection
import com.spogss.wibb.controller.WibbController
import com.spogss.wibb.data.Beer
import com.spogss.wibb.data.Offer
import com.spogss.wibb.data.Store
import com.spogss.wibb.tools.FavouriteFilter
import com.spogss.wibb.tools.UIUtils
import com.spogss.wibb.tools.URLUnifier
import com.spogss.wibb.tools.err.ErrorHandler
import com.spogss.wibb.ui.GridAutofitLayoutManager
import com.spogss.wibb.ui.GridRecyclerViewAdapter
import com.triggertrap.seekarc.SeekArc
import kotlinx.android.synthetic.main.activity_add_offer.*
import kotlinx.android.synthetic.main.offer_card_shrinked.*
import kotlinx.android.synthetic.main.offer_card_shrinked.view.*
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*


class AddOfferActivity : AppCompatActivity() {

    private val offer = Offer()
    private var currentStep = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        try {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_add_offer)

            supportActionBar?.setDisplayHomeAsUpEnabled(true)

            // init step view
            val stepView = findViewById<StepView>(R.id.step_view)
            stepView.setOnStepClickListener { setStep(it) }

            // init store chooser
            val columnWidth = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                85f,
                this.resources.displayMetrics
            ).toInt()

            val stores = WibbController.stores.filter { FavouriteFilter(it) }

            val rvs = findViewById<RecyclerView>(R.id.recyclerView_stores)
            val rvsa = GridRecyclerViewAdapter(this, stores)
            rvsa.setOnItemSelected {
                setOfferStore(it)
                nextstep()
            }

            rvs.layoutManager = GridAutofitLayoutManager(this, columnWidth)
            rvs.adapter = rvsa

            // init beer chooser
            val beers = WibbController.beers.filter { FavouriteFilter(it) }

            val rvb = findViewById<RecyclerView>(R.id.recyclerView_beers)
            val rvba = GridRecyclerViewAdapter(this, beers)
            rvba.setOnItemSelected {
                setOfferBeer(it)
                nextstep()
            }

            rvb.layoutManager = GridAutofitLayoutManager(this, columnWidth)
            rvb.adapter = rvba

            // init seekarc
            val seekArc = findViewById<SeekArc>(R.id.seekArc)
            seekArc.setOnSeekArcChangeListener(object : SeekArc.OnSeekArcChangeListener {
                override fun onProgressChanged(
                    seekArc: SeekArc?,
                    progress: Int,
                    fromUser: Boolean
                ) {
                    val prog = progress + 5
                    val progressv = findViewById<TextView>(R.id.seekArcProgress)
                    progressv.text = "~€$prog"
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
            val v = findViewById<View>(R.id.incl_cardView_currentOffer)
            val txt = v.findViewById<TextView>(R.id.offer_card_date_txt)
            txt.text = ""

            calendar_range.setSelectedDateRange(Calendar.getInstance(), null)
            calendar_range.setCalendarListener(object : DateRangeCalendarView.CalendarListener {
                override fun onFirstDateSelected(startDate: Calendar) {
                    val startDateLocal = LocalDateTime.ofInstant(
                        startDate.toInstant(),
                        startDate.getTimeZone().toZoneId()
                    ).toLocalDate()
                    setOfferStartDate(startDateLocal)
                }

                override fun onDateRangeSelected(startDate: Calendar, endDate: Calendar) {
                    val startDateLocal =
                        LocalDateTime.ofInstant(startDate.toInstant(), ZoneId.systemDefault())
                            .toLocalDate()
                    val endDateLocal =
                        LocalDateTime.ofInstant(endDate.toInstant(), ZoneId.systemDefault())
                            .toLocalDate()
                    setOfferDates(startDateLocal, endDateLocal)
                }
            })
        } catch (ex: Exception) {
            ErrorHandler.of(this).handle(ex)
        }
    }

    private fun setOfferBeer(b: Beer) {
        offer.beer = b
        textView_offerCard_beer.text = b.name
        val col = Color.parseColor(b.iconBg)
        textView_offerCard_beer.setBackgroundColor(col)
        offer_card_beer_img.setBackgroundColor(col)
        textView_offerCard_beer.setTextColor(UIUtils.getForegroundColorFor(col, this))
        Glide.with(this)
            .load(URLUnifier.unifyImgUrl(b.icon))
            .into(offer_card_beer_img)
        refreshGradient()
        onNewOfferInputChanged()
    }

    private fun setOfferPrice(p: Int) {
        offer.price = p
        val v = findViewById<View>(R.id.incl_cardView_currentOffer)
        val txt = v.findViewById<TextView>(R.id.offer_card_price_txt)
        txt.text = "€$p"
        onNewOfferInputChanged()
    }

    private fun setOfferStore(s: Store) {
        offer.store = s
        textView_offerCard_store.text = s.name
        val col = Color.parseColor(s.iconBg)
        offer_card.setCardBackgroundColor(col)
        offer_card_store_img.setBackgroundColor(col)
        val fgcol = UIUtils.getForegroundColorFor(col, this)
        textView_offerCard_store.setTextColor(fgcol)
        offer_card_date_txt.setTextColor(fgcol)
        offer_card_price_txt.setTextColor(fgcol)
        Glide.with(this)
            .load(URLUnifier.unifyImgUrl(s.icon))
            .into(offer_card_store_img)
        refreshGradient()
        onNewOfferInputChanged()
    }

    private fun refreshGradient() {
        val col1 =
            if (offer.beer != null) Color.parseColor(offer.beer!!.iconBg) else Color.TRANSPARENT
        val col2 =
            if (offer.store != null) Color.parseColor(offer.store!!.iconBg) else Color.TRANSPARENT
        val gd = GradientDrawable(
            GradientDrawable.Orientation.LEFT_RIGHT,
            intArrayOf(col1, col2)
        )
        gd.cornerRadius = 0f

        incl_cardView_currentOffer.offer_card_gradient.background = gd
    }

    private fun setOfferStartDate(startDate: LocalDate) {
        offer.startDate = startDate
        updateOfferDates()
        onNewOfferInputChanged()
    }

    fun setOfferEndDate(endDate: LocalDate) {
        offer.endDate = endDate
        updateOfferDates()
        onNewOfferInputChanged()
    }

    private fun setOfferDates(startDate: LocalDate, endDate: LocalDate) {
        if (startDate.isBefore(endDate)) {
            offer.startDate = startDate
            offer.endDate = endDate
        } else {
            offer.endDate = startDate
            offer.startDate = endDate
        }
        updateOfferDates()
        onNewOfferInputChanged()
    }

    private fun updateOfferDates() {
        val v = findViewById<View>(R.id.incl_cardView_currentOffer)
        val txt = v.findViewById<TextView>(R.id.offer_card_date_txt)
        val formatter = DateTimeFormatter.ofPattern("d.")
        txt.text = "${offer.startDate?.format(formatter)} - ${offer.endDate?.format(formatter)}"
    }

    private fun nextstep() {
        setStep(++currentStep)
    }

    fun submitNewOffer(v: View) {
        if (offer.isValid)
            WibbConnection.addOffer(offer) {
                if (it /*worked*/) {
                    Toast.makeText(
                        this.applicationContext,
                        R.string.toast_newOffer_success,
                        Toast.LENGTH_SHORT
                    ).show()
                    finish()
                } else {
                    Toast.makeText(
                        this.applicationContext,
                        R.string.toast_newOffer_fail,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        else
            Toast.makeText(
                this.applicationContext,
                R.string.toast_newOffer_invalid,
                Toast.LENGTH_SHORT
            ).show()
    }

    private fun setStep(step: Int) {
        if (step < 0 || step > 3)
            return

        findViewById<StepView>(R.id.step_view).go(step, true)

        if (step != 0)
            findViewById<RecyclerView>(R.id.recyclerView_stores).visibility = View.GONE
        else
            findViewById<RecyclerView>(R.id.recyclerView_stores).visibility = View.VISIBLE

        if (step != 1)
            findViewById<RecyclerView>(R.id.recyclerView_beers).visibility = View.GONE
        else
            findViewById<RecyclerView>(R.id.recyclerView_beers).visibility = View.VISIBLE

        if (step != 2)
            findViewById<LinearLayout>(R.id.linearLayout_price).visibility = View.GONE
        else
            findViewById<LinearLayout>(R.id.linearLayout_price).visibility = View.VISIBLE

        if (step != 3)
            findViewById<DateRangeCalendarView>(R.id.calendar_range).visibility = View.GONE
        else
            findViewById<DateRangeCalendarView>(R.id.calendar_range).visibility = View.VISIBLE

        currentStep = step
    }

    private fun onNewOfferInputChanged() {
        setFabSubmitVisible(offer.isValid)
    }

    private fun setFabSubmitVisible(visible: Boolean) {
        fab_submitOffer.animate().translationY(
            if (visible) 0f else ll_footer_add_offer.height.toFloat()
        ).setInterpolator(LinearInterpolator()).start()
    }
}
