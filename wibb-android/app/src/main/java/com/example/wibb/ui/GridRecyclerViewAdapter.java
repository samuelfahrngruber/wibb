package com.example.wibb.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.wibb.R;
import com.example.wibb.data.GridDisplayable;
import com.example.wibb.tools.URLUnifier;

import java.util.List;

public class GridRecyclerViewAdapter<ItemType extends GridDisplayable> extends RecyclerView.Adapter<GridRecyclerViewAdapter.MyViewHolder> {

    private Context context;
    private List<ItemType> data;

    private SelectionListener selectionListener;

    public GridRecyclerViewAdapter(Context context, List<ItemType> data) {
        this.context = context;
        this.data = data;
    }

    public void setSelectionListener(SelectionListener<ItemType> selectionListener) {
        this.selectionListener = selectionListener;
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
        final GridDisplayable item = data.get(position);

        holder.itemTextv.setText(item.getText());

        Glide.with(context)
                .load(URLUnifier.Companion.getInstance().unifyImgUrl(item.getIconurl()))
                .into(holder.itemImagev);

        //holder.itemImagev.setImageResource(item.getDrawable());

        holder.cardv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectionListener.onGridItemSelected(item);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView itemTextv;
        ImageView itemImagev;

        CardView cardv;

        public MyViewHolder(View itemView) {
            super(itemView);

            itemTextv = itemView.findViewById(R.id.grid_item_text_id);
            itemImagev = itemView.findViewById(R.id.grid_item_img_id);

            cardv = itemView.findViewById(R.id.id_cardView_gridItem);
        }
    }

    public interface SelectionListener<IType extends GridDisplayable>{
        void onGridItemSelected(IType item);
    }
}
