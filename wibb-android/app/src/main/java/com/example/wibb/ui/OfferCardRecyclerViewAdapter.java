package com.example.wibb.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.wibb.R;
import com.example.wibb.data.Offer;

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

        holder.brandImagev.setImageResource(o.getBrand().getDrawable());
        holder.storeImagev.setImageResource(o.getStore().getDrawable());

        holder.priceTextv.setText("€" + o.getPrice());

        holder.brandTextv.setText((o.getBrand().getText()));
        holder.storeTextv.setText((o.getStore().getText()));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("E, d");

        holder.dateTextv.setText(o.getStart().format(formatter) + " - " + o.getEnd().format(formatter));
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

        public MyViewHolder(View itemView) {
            super(itemView);

            storeImagev = itemView.findViewById(R.id.offer_card_store_img);
            brandImagev = itemView.findViewById(R.id.offer_card_brand_img);

            priceTextv = itemView.findViewById(R.id.offer_card_price_txt);
            dateTextv = itemView.findViewById(R.id.offer_card_date_txt);
            brandTextv = itemView.findViewById(R.id.offer_card_brand_txt);
            storeTextv = itemView.findViewById(R.id.offer_card_store_txt);

        }
    }
}
