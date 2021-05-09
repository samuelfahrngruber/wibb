package com.spogss.wibb.ui

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.spogss.wibb.R
import com.spogss.wibb.controller.WibbController
import com.spogss.wibb.data.GridDisplayable
import com.spogss.wibb.tools.URLUnifier

class FavouriteItemAdapter<ItemType : GridDisplayable>(
    private val context: Context,
    private val data: List<ItemType>
) : RecyclerView.Adapter<FavouriteItemAdapter.MyViewHolder>() {

    private var onItemSelected: ((ItemType) -> Unit)? = null
    private var sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    fun setOnItemSelected(onItemSelected: (ItemType) -> Unit) {
        this.onItemSelected = onItemSelected
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.favourite_item, parent, false)

        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var itemTextV: TextView = itemView.findViewById(R.id.fav_item_txt)
        var itemImageV: ImageView = itemView.findViewById(R.id.fav_card_item_img)
        var itemImageC: FrameLayout = itemView.findViewById(R.id.fav_card_item_img_container)
        var itemImageG: ImageView = itemView.findViewById(R.id.fav_card_gradient)
        var favChb: CheckBox = itemView.findViewById(R.id.fav_card_checkbox)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = data[position]
        holder.itemTextV.text = item.text

        Glide.with(context)
            .load(URLUnifier.unifyImgUrl(item.iconUrl))
            .into(holder.itemImageV)

        val col = Color.parseColor(item.iconBgCol)
        holder.itemImageC.setBackgroundColor(col)

        val gd =
            GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, intArrayOf(col, Color.WHITE))
        gd.cornerRadius = 0f
        holder.itemImageG.background = gd

        holder.favChb.isChecked = WibbController.favourites.contains(item)
        holder.favChb.setOnCheckedChangeListener { _, state ->
            if (state)
                WibbController.favourites.add(item)
            else
                WibbController.favourites.remove(item)

            with(sharedPreferences.edit()) {
                putBoolean(item.text, state)
                apply()
            }
        }
    }
}