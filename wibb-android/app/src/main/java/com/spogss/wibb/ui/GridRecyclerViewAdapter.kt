package com.spogss.wibb.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.spogss.wibb.R
import com.spogss.wibb.data.GridDisplayable
import com.spogss.wibb.tools.URLUnifier


class GridRecyclerViewAdapter<ItemType : GridDisplayable>(private val context: Context, private val data: List<ItemType>): RecyclerView.Adapter<GridRecyclerViewAdapter.MyViewHolder>() {

    private var onItemSelected: ((ItemType) -> Unit)? = null

    fun setOnItemSelected(onItemSelected: (ItemType) -> Unit) {
        this.onItemSelected = onItemSelected
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.grid_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = data[position]
        holder.itemTextV.text = item.text

        Glide.with(context)
            .load(URLUnifier.unifyImgUrl(item.iconUrl))
            .into(holder.itemImageV)

        //holder.itemImageV.setImageResource(item.getDrawable());

        holder.cardV.setOnClickListener {
            onItemSelected?.invoke(item)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var itemTextV: TextView = itemView.findViewById(R.id.grid_item_text_id)
        var itemImageV: ImageView = itemView.findViewById(R.id.grid_item_img_id)
        var cardV: CardView = itemView.findViewById(R.id.id_cardView_gridItem)
    }
}