package com.example.wibb.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.wibb.R;
import com.example.wibb.data.Offer;
import com.example.wibb.tools.URLUnifier;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class OfferCardRecyclerViewAdapter extends RecyclerView.Adapter<OfferCardRecyclerViewAdapter.MyViewHolder> {

    private Context context;
    private List<Offer> data;

    public OfferCardRecyclerViewAdapter(Context context, List<Offer> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.offer_card, parent, false);
        return new OfferCardRecyclerViewAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Offer o = data.get(position);

        Glide.with(context)
                .load(URLUnifier.INSTANCE.unifyImgUrl(o.getBeer().getIcon()))
                .into(holder.brandImagev);

        Glide.with(context)
                .load(URLUnifier.INSTANCE.unifyImgUrl(o.getStore().getIcon()))
                .into(holder.storeImagev);

//        holder.brandImagev.setImageResource(o.getBeer().getDrawable());
//        holder.storeImagev.setImageResource(o.getStore().getDrawable());

        holder.priceTextv.setText("€" + o.getPrice());

        holder.brandTextv.setText((o.getBeer().getText()));
        holder.storeTextv.setText((o.getStore().getText()));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("E, d");

        holder.dateTextv.setText((o.getStartDate() == null ? "" : o.getStartDate().format(formatter)) + " - " + (o.getStartDate() == null ? "" : o.getEndDate().format(formatter)));

        holder.menuImageb.setImageResource(R.drawable.ic_more_vert_black_24dp);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView brandImagev;
        ImageView storeImagev;

        TextView priceTextv;
        TextView dateTextv;
        TextView brandTextv;
        TextView storeTextv;

        ImageButton menuImageb;

        public MyViewHolder(View itemView) {
            super(itemView);

            storeImagev = itemView.findViewById(R.id.offer_card_store_img);
            brandImagev = itemView.findViewById(R.id.offer_card_beer_img);

            priceTextv = itemView.findViewById(R.id.offer_card_price_txt);
            dateTextv = itemView.findViewById(R.id.offer_card_date_txt);
            brandTextv = itemView.findViewById(R.id.offer_card_beer_txt);
            storeTextv = itemView.findViewById(R.id.offer_card_store_txt);

            menuImageb = itemView.findViewById(R.id.offer_card_menu_btn);
        }
    }
}
