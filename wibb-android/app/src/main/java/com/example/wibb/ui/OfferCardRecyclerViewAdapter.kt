package com.example.wibb.ui

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
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
            .load(URLUnifier.unifyImgUrl(o.beer!!.icon))
            .into(holder.brandImageV)

        Glide.with(context)
            .load(URLUnifier.unifyImgUrl(o.store!!.icon))
            .into(holder.storeImageV)

        //holder.brandImageV.setImageResource(o.getBeer().getDrawable());
        //holder.storeImageV.setImageResource(o.getStore().getDrawable());

        holder.priceTextV.text = "€${o.price}"
        holder.brandTextV.text = o.beer!!.text
        holder.storeTextV.text = o.store!!.text

        val formatter = DateTimeFormatter.ofPattern("E, d")

        holder.dateTextV.text = String.format("%s - %s",
            if (o.startDate == null) "" else o.startDate!!.format(formatter),
            if (o.endDate == null) "" else o.endDate!!.format(formatter)
        )

        holder.menuImageB.setImageResource(R.drawable.ic_more_vert_black_24dp)

        val helper = OfferMenuHelper(o)

        holder.menuImageB.setOnClickListener(helper)
        holder.storeCardV.setOnClickListener(helper)
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
        var dateTextV: TextView = itemView.findViewById(R.id.offer_card_date_txt)
        var brandTextV: TextView = itemView.findViewById(R.id.offer_card_beer_txt)
        var storeTextV: TextView = itemView.findViewById(R.id.offer_card_store_txt)
        var menuImageB: ImageButton = itemView.findViewById(R.id.offer_card_menu_btn)
        var storeCardV: CardView = itemView.findViewById(R.id.cardView_offer_card_store)
    }

}