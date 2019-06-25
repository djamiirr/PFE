package com.example.iwannathis.ui.adapters;

import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.iwannathis.R;
import com.example.iwannathis.tools.entities.EquipReservationEntity;
import com.example.iwannathis.tools.entities.EquipmentProductEntity;
import com.example.iwannathis.tools.webservice.Links;
import com.example.iwannathis.tools.webservice.callbacks.OnReservationClickListener;
import com.example.iwannathis.tools.webservice.callbacks.OnReservationCompleteListener;
import com.example.iwannathis.ui.adapters.callbacks.OnItemClickListener;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class EquipHistRVAdapter extends RecyclerView.Adapter<EquipHistRVAdapter.ViewHolder> {

    private List<EquipReservationEntity> data;
    private OnReservationClickListener callback;

    public EquipHistRVAdapter(List<EquipReservationEntity> data) {
        this.data = data;
    }

    public void setOnItemClickListener(OnReservationClickListener callback) {
        this.callback = callback;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.reservation_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        holder.modelTV.setText(data.get(i).getBrand() + " " + data.get(i).getModel());
        holder.productTV.setText(data.get(i).getLabel());
        holder.equipmentProductEntity = data.get(i);
        if (!data.get(i).getImg().equals("") || data.get(i).getImg() != null) {
            Picasso.get().load(Links.IMG + data.get(i).getImg()).placeholder(R.drawable.equip_img_back).error(R.drawable.equip_img_back).into(holder.imgIV);
        }
        holder.id = data.get(i).getIdRes();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        EquipReservationEntity equipmentProductEntity;
        int id;
        CircleImageView imgIV;
        TextView productTV, modelTV;
        Resources res;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            initViews(itemView);
            res = itemView.getResources();
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        callback.onClick(equipmentProductEntity);
                }
            });
        }

        private void initViews(View view) {
            imgIV = view.findViewById(R.id.img);
            productTV = view.findViewById(R.id.product);
            modelTV = view.findViewById(R.id.model);
        }
    }

}
