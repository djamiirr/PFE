package com.example.iwannathis.ui.adapters;

import android.content.res.Resources;
import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.iwannathis.R;
import com.example.iwannathis.tools.entities.EquipmentProductEntity;
import com.example.iwannathis.tools.entities.ExpandableProductEntity;
import com.example.iwannathis.tools.webservice.Links;
import com.example.iwannathis.ui.adapters.callbacks.OnItemClickListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ExpandablesRVAdapter extends RecyclerView.Adapter<ExpandablesRVAdapter.ViewHolder> {

    private List<ExpandableProductEntity> data;
    private OnItemClickListener callback;

    public ExpandablesRVAdapter(List<ExpandableProductEntity> data) {
        this.data = data;
    }

    public void setOnItemClickListener(OnItemClickListener callback) {
        this.callback = callback;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.product_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        holder.productTV.setText(data.get(i).getLabel());
        holder.modelTV.setText(data.get(i).getBrand());
        holder.dispoTV.setText(data.get(i).dispo() + " Disponible");
        holder.dispo = data.get(i).dispo();
        holder.dispoRL.setBackground(holder.res.getDrawable(data.get(i).dispo() > 0 ? R.drawable.available_indicator : R.drawable.unavailable));
        holder.expandableProductEntity = data.get(i);

        if (!data.get(i).getImg().equals("") || data.get(i).getImg() != null) {
            Picasso.get().load(Links.IMG + data.get(i).getImg()).placeholder(R.drawable.equip_img_back).error(R.drawable.equip_img_back).into(holder.imgIV);
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgIV;
        ExpandableProductEntity expandableProductEntity;
        TextView productTV, modelTV, dispoTV;
        RelativeLayout dispoRL;
        Resources res;

        int dispo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            initViews(itemView);
            res = itemView.getResources();

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (expandableProductEntity.dispo() > 0)
                        callback.onClick(expandableProductEntity);
                }
            });
        }

        private void initViews(View view) {
            imgIV = view.findViewById(R.id.img);
            productTV = view.findViewById(R.id.product);
            modelTV = view.findViewById(R.id.model);
            dispoTV = view.findViewById(R.id.disponibility);
            dispoRL = view.findViewById(R.id.disponibility_indicator);
        }
    }
}
