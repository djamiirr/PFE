package com.example.iwannathis.tools.webservice;

import android.content.Context;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.iwannathis.tools.entities.Article;
import com.example.iwannathis.tools.entities.EquipReservationEntity;
import com.example.iwannathis.tools.entities.EquipmentProductEntity;
import com.example.iwannathis.tools.entities.ExpandableProductEntity;
import com.example.iwannathis.tools.webservice.callbacks.OnEquipmentsHistoryFetchListener;
import com.example.iwannathis.tools.webservice.callbacks.OnProductsFetchListener;
import com.example.iwannathis.tools.webservice.callbacks.OnReservationCompleteListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.android.volley.Request.Method.GET;
import static com.android.volley.Request.Method.POST;
import static com.android.volley.Request.Method.PUT;

public class ProductsWebService {

    private RequestQueue queue;
    private Context context;

    public ProductsWebService(Context context) {
        queue = Volley.newRequestQueue(context);
        this.context = context;
    }


    public void getProducts(final OnProductsFetchListener callback) {

        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(GET, Links.PRODUCTS, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Gson gson = new GsonBuilder().serializeNulls().create();
                    JSONArray equip = response.getJSONArray("equip");
                    JSONArray exp = response.getJSONArray("consom");

                    List<ExpandableProductEntity> expandables = new ArrayList<>();
                    List<EquipmentProductEntity> equipments = new ArrayList<>();

                    for (int i = 0; i < exp.length(); i++) {
                        JSONObject object = exp.getJSONObject(i);
                        ExpandableProductEntity productEntity = gson.fromJson(object.toString(), ExpandableProductEntity.class);
                        expandables.add(productEntity);
                    }

                    for (int i = 0; i < equip.length(); i++) {
                        JSONObject object = equip.getJSONObject(i);
                        EquipmentProductEntity productEntity = gson.fromJson(object.toString(), EquipmentProductEntity.class);
                        equipments.add(productEntity);
                    }

                    callback.onCpmlete(equipments, expandables);

                } catch (Exception e) {
                    callback.onError();
                    Log.e("json", e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onError();
            }
        });

        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy());
        queue.add(jsonArrayRequest);
    }

    public void reserveProduct(Article article, final OnReservationCompleteListener callback) {

        JSONObject object = null;
        try {
            object = new JSONObject();
            object.put("id_art", article.getId());
            object.put("id_emp", context.getSharedPreferences("gest_res", Context.MODE_PRIVATE).getInt("idEmp", -1));
            object.put("qte", article.getQte());
            object.put("date_res", article.dateRes);
            if (article instanceof EquipmentProductEntity) {
                object.put("date_ret", article.dateRet);
                object.put("status", article.status);
            }

            Log.e("json is", object.toString());
        } catch (JSONException e) {
            Log.e("json", e.getMessage());
        }

        JsonObjectRequest jor = new JsonObjectRequest(POST, article instanceof EquipmentProductEntity ? Links.EQUIPMENTS : Links.EXPANDABLES, object, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                callback.onSuccess();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onError();
            }
        });

        jor.setRetryPolicy(new DefaultRetryPolicy());
        queue.add(jor);
    }

    public void getEquipmentHistory(final OnEquipmentsHistoryFetchListener callback) {
        JsonArrayRequest jor = new JsonArrayRequest(Links.EQUIPMENTS + "/" + context.getSharedPreferences("gest_res", Context.MODE_PRIVATE)
                .getInt("idEmp", -1),
                new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.e("response", response.toString());
                List<EquipReservationEntity> reservations = new ArrayList<>();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject object = response.getJSONObject(i);
                        Gson gson = new GsonBuilder().serializeNulls().create();
                        EquipReservationEntity reservation = gson.fromJson(object.toString(), EquipReservationEntity.class);
                        reservations.add(reservation);
                    } catch (JSONException e) {
                        Log.e("json", e.getMessage());
                    }

                    callback.onSuccess(reservations);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onError();
            }
        });

        jor.setRetryPolicy(new DefaultRetryPolicy());
        queue.add(jor);
    }

    public void returnEquip(int id, String def, final OnReservationCompleteListener callback){
        try{
            JSONObject object = new JSONObject();
            object.put("def", def);
            JsonObjectRequest request = new JsonObjectRequest(PUT, Links.EQUIPMENTS + id, object, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    callback.onSuccess();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("volley", error.getMessage());
                    callback.onError();
                }
            });

            request.setRetryPolicy(new DefaultRetryPolicy());
            queue.add(request);
        }catch (Exception e){
            Log.e("catch", e.getMessage());
        }
    }
}
