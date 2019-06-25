package com.example.iwannathis.ui.adapters;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
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
import com.example.iwannathis.tools.webservice.Links;
import com.example.iwannathis.ui.adapters.callbacks.OnItemClickListener;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class EquipmentsRVAdapter extends RecyclerView.Adapter<EquipmentsRVAdapter.ViewHolder> {

    private List<EquipmentProductEntity> data;
    private OnItemClickListener callback;

    public EquipmentsRVAdapter(List<EquipmentProductEntity> data) {
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
        holder.modelTV.setText(data.get(i).getBrand() + " " + data.get(i).getModel());
        holder.productTV.setText(data.get(i).getLabel());
        holder.dispoTV.setText(data.get(i).dispo() + " Disponible");
        holder.dispo = data.get(i).dispo();
        holder.dispoRL.setBackground(holder.res.getDrawable(data.get(i).dispo() > 0 ? R.drawable.available_indicator : R.drawable.unavailable));
        holder.equipmentProductEntity = data.get(i);
        if (!data.get(i).getImg().equals("") || data.get(i).getImg() != null) {
            Picasso.get().load(Links.IMG + data.get(i).getImg()).placeholder(R.drawable.equip_img_back).error(R.drawable.equip_img_back).into(holder.imgIV);
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        EquipmentProductEntity equipmentProductEntity;
        CircleImageView imgIV;
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
                    if (equipmentProductEntity.dispo() > 0)
                        callback.onClick(equipmentProductEntity);
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
