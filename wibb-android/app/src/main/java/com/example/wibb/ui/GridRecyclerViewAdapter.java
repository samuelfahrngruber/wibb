package com.example.wibb.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.wibb.R;
import com.example.wibb.data.GridDisplayable;

import java.util.List;

public class GridRecyclerViewAdapter extends RecyclerView.Adapter<GridRecyclerViewAdapter.MyViewHolder> {

    private Context context;
    private List<GridDisplayable> data;

    public GridRecyclerViewAdapter(Context context, List<GridDisplayable> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.grid_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.itemTextv.setText(data.get(position).getText());
        holder.itemImagev.setImageResource(data.get(position).getDrawable());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView itemTextv;
        ImageView itemImagev;

        public MyViewHolder(View itemView) {
            super(itemView);

            itemTextv = itemView.findViewById(R.id.grid_item_text_id);
            itemImagev = itemView.findViewById(R.id.grid_item_img_id);
        }
    }
}
