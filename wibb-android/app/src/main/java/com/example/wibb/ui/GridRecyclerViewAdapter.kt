package com.example.wibb.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.wibb.R
import com.example.wibb.data.GridDisplayable
import com.example.wibb.tools.URLUnifier.Companion.instance


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
        holder.itemTextv.text = item.text

        Glide.with(context)
            .load(instance.unifyImgUrl(item.iconurl))
            .into(holder.itemImagev)

        //holder.itemImagev.setImageResource(item.getDrawable());

        holder.cardv.setOnClickListener {
            onItemSelected?.invoke(item)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var itemTextv: TextView = itemView.findViewById(R.id.grid_item_text_id)
        var itemImagev: ImageView = itemView.findViewById(R.id.grid_item_img_id)
        var cardv: CardView = itemView.findViewById(R.id.id_cardView_gridItem)
    }
}