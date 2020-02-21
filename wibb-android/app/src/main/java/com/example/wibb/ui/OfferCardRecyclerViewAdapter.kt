package com.example.wibb.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.wibb.R
import com.example.wibb.connection.WibbConnection
import com.example.wibb.data.Offer
import com.example.wibb.data.Report
import com.example.wibb.data.Store
import com.example.wibb.tools.URLUnifier
import java.time.format.DateTimeFormatter

class OfferCardRecyclerViewAdapter(private val context: Context, private val data: List<Offer>): RecyclerView.Adapter<OfferCardRecyclerViewAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.offer_card, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val o = data[position]

        Glide.with(context)
            .load(URLUnifier.instance.unifyImgUrl(o.beer!!.icon))
            .into(holder.brandImagev)

        Glide.with(context)
            .load(URLUnifier.instance.unifyImgUrl(o.store!!.icon))
            .into(holder.storeImagev)

        //holder.brandImagev.setImageResource(o.getBeer().getDrawable());
        //holder.storeImagev.setImageResource(o.getStore().getDrawable());

        holder.priceTextv.text = "€${o.price}"
        holder.brandTextv.text = o.beer!!.text
        holder.storeTextv.text = o.store!!.text

        val formatter = DateTimeFormatter.ofPattern("E, d")

        holder.dateTextv.text = String.format("%s - %s",
            if (o.startDate == null) "" else o.startDate!!.format(formatter),
            if (o.endDate == null) "" else o.endDate!!.format(formatter)
        )

        holder.menuImageb.setImageResource(R.drawable.ic_more_vert_black_24dp)

        val helper = OfferMenuHelper(o)

        holder.menuImageb.setOnClickListener(helper)
        holder.storeCardv.setOnClickListener(helper)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class OfferMenuHelper(private val offer: Offer): View.OnClickListener, PopupMenu.OnMenuItemClickListener {

        override fun onClick(v: View) {
            if (v.id == R.id.offer_card_menu_btn) {
                val popup = PopupMenu(context, v)
                val inflater = popup.menuInflater
                inflater.inflate(R.menu.offer_menu, popup.menu)
                popup.setOnMenuItemClickListener(this)
                popup.show()
            } else if (v.id == R.id.cardView_offer_card_store) {
                openFindNearbyStoresActivity(offer.store)
            }
        }

        override fun onMenuItemClick(menuItem: MenuItem): Boolean {
            if (menuItem.itemId == R.id.menu_item_offer_report) {
                val r = Report(Report.RType.FAKE, "FAKED_BEER_OFFER", offer)

                WibbConnection.instance.addReport(r) { rep: Report? ->
                    if (rep != null)
                        Toast.makeText(context, context.getString(R.string.toast_reportSubmitted, rep.id), Toast.LENGTH_SHORT).show()
                    else Toast.makeText(context, R.string.toast_reportFailed, Toast.LENGTH_SHORT).show()
                }
                return true

            } else if (menuItem.itemId == R.id.menu_item_offer_findNearby)
                openFindNearbyStoresActivity(offer.store)

            return false
        }

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
        var brandImagev: ImageView = itemView.findViewById(R.id.offer_card_beer_img)
        var storeImagev: ImageView = itemView.findViewById(R.id.offer_card_store_img)
        var priceTextv: TextView = itemView.findViewById(R.id.offer_card_price_txt)
        var dateTextv: TextView = itemView.findViewById(R.id.offer_card_date_txt)
        var brandTextv: TextView = itemView.findViewById(R.id.offer_card_beer_txt)
        var storeTextv: TextView = itemView.findViewById(R.id.offer_card_store_txt)
        var menuImageb: ImageButton = itemView.findViewById(R.id.offer_card_menu_btn)
        var storeCardv: CardView = itemView.findViewById(R.id.cardView_offer_card_store)
    }

}