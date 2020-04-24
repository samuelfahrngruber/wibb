package com.spogss.wibb.ui

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.net.Uri
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.spogss.wibb.R
import com.spogss.wibb.connection.WibbConnection
import com.spogss.wibb.controller.WibbController
import com.spogss.wibb.data.Offer
import com.spogss.wibb.data.Report
import com.spogss.wibb.data.Store
import com.spogss.wibb.tools.FavouriteFilter
import com.spogss.wibb.tools.URLUnifier
import kotlinx.android.synthetic.main.offer_card.view.*
import java.time.format.DateTimeFormatter



class OfferCardRecyclerViewAdapter(private val context: Context, private var data: List<Offer>):
    RecyclerView.Adapter<OfferCardRecyclerViewAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.offer_card, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val o = data[position]

        Glide.with(context)
            .load(URLUnifier.unifyImgUrl(o.beer!!.icon))
            .into(holder.brandImageV)
        holder.brandImageC.setBackgroundColor(Color.parseColor(o.beer!!.iconBg))
        holder.brandImageV.setBackgroundColor(Color.parseColor(o.beer!!.iconBg))
        holder.brandTextV.text = o.beer!!.name

        Glide.with(context)
            .load(URLUnifier.unifyImgUrl(o.store!!.icon))
            .into(holder.storeImageV)
        holder.storeImageV.setBackgroundColor(Color.parseColor(o.store!!.iconBg))
        holder.storeTextV.text = o.store!!.name

        val gd = GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT,
            intArrayOf(Color.parseColor(o.beer!!.iconBg), Color.parseColor(o.store!!.iconBg)))
        gd.cornerRadius = 0f

        holder.gradientImageV.background = gd


        holder.offerCardV.setCardBackgroundColor(Color.parseColor(o.store!!.iconBg)) // = gd //

        holder.priceTextV.text = "€${o.price}"


        val formatter = DateTimeFormatter.ofPattern("E, d")

        holder.dateTextV.text = String.format(context.getString(R.string.offer_card_datestr_format),
            if (o.startDate == null) "?" else o.startDate!!.format(formatter),
            if (o.endDate == null) "?" else o.endDate!!.format(formatter)
        )

        holder.menuImageB.setImageResource(R.drawable.ic_more_vert_black_24dp)

        val helper = OfferMenuHelper(o)

        holder.menuImageB.setOnClickListener(helper)
        holder.storeImageV.setOnClickListener(helper)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun notifyWibbDataChanged(){
        data = WibbController.offers.filter { FavouriteFilter(it) }
        notifyDataSetChanged()
    }

    inner class OfferMenuHelper(private val offer: Offer): View.OnClickListener, PopupMenu.OnMenuItemClickListener {

        override fun onClick(v: View) {
            if (v.id == R.id.offer_card_menu_btn) {
                val popup = PopupMenu(context, v)
                val inflater = popup.menuInflater
                inflater.inflate(R.menu.offer_menu, popup.menu)
                popup.setOnMenuItemClickListener(this)
                popup.show()
            } else if (v.id == R.id.offer_card_store_img) {
                openFindNearbyStoresActivity(offer.store)
            }
        }

        override fun onMenuItemClick(menuItem: MenuItem): Boolean {
            if (menuItem.itemId == R.id.menu_item_offer_report) {

                callDialog {
                    val r = Report(Report.RType.values()[it], "NO_MESSAGE", offer)
                    WibbConnection.addReport(r) { rep: Report? ->
                        if (rep != null)
                            Toast.makeText(context, context.getString(R.string.toast_reportSubmitted, rep.id), Toast.LENGTH_SHORT).show()
                            else Toast.makeText(context, R.string.toast_reportFailed, Toast.LENGTH_SHORT).show()
                    }
                }

                return true

            } else if (menuItem.itemId == R.id.menu_item_offer_findNearby)
                openFindNearbyStoresActivity(offer.store)

            return false
        }

    }

    private fun callDialog(onReport: (pos: Int) -> Unit) {
        AlertDialog.Builder(context)
            .setSingleChoiceItems(Report.RType.stringValues(), 0, null)
            .setTitle(R.string.alert_header_report)
            .setNegativeButton(R.string.alert_button_cancel) { dialog, _ ->
                dialog.dismiss()
            }
            .setPositiveButton(R.string.alert_button_report)
            { dialog, _ ->
                dialog.dismiss()
                val selectedPosition = (dialog as AlertDialog).listView.checkedItemPosition
                onReport(selectedPosition)
            }
            .show()
    }

    private fun openFindNearbyActivity(query: String) {
        val gmmIntentUri = Uri.parse("geo:0,0?q=$query")
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        mapIntent.setPackage("com.google.android.apps.maps")
        context.startActivity(mapIntent)
    }

    private fun openFindNearbyStoresActivity(s: Store?) {
        openFindNearbyActivity(s?.name?.replace("&", "%26") + " Supermarket")
    }

    inner class MyViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var brandImageV: ImageView = itemView.findViewById(R.id.offer_card_beer_img)
        var storeImageV: ImageView = itemView.findViewById(R.id.offer_card_store_img)
        var priceTextV: TextView = itemView.findViewById(R.id.offer_card_price_txt)
        var gradientImageV: ImageView = itemView.findViewById(R.id.offer_card_gradient)
        var offerCardV: CardView = itemView.findViewById(R.id.offer_card)
        var dateTextV: TextView = itemView.findViewById(R.id.offer_card_date_txt)
        var brandImageC: FrameLayout = itemView.findViewById(R.id.offer_card_beer_img_container)
        var menuImageB: ImageButton = itemView.findViewById(R.id.offer_card_menu_btn)
        var brandTextV: TextView = itemView.findViewById(R.id.textView_offerCard_beer)
        var storeTextV: TextView = itemView.findViewById(R.id.textView_offerCard_store)
    }

}