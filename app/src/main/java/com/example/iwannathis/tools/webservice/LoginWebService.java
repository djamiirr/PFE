package com.example.iwannathis.tools.webservice;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.iwannathis.tools.webservice.callbacks.OnLoginListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.PipedOutputStream;

import static com.android.volley.Request.Method.POST;

public class LoginWebService {

    private RequestQueue queue;
    private Context context;

    public LoginWebService(Context context){
        this.queue = Volley.newRequestQueue(context);
        this.context = context;
    }

    public void login(String mail, String passwd, final OnLoginListener callback){
        JSONObject obj = null;

        try{
            obj = new JSONObject();
            obj.put("mail", mail);
            obj.put("passwd", passwd);
        }catch (Exception e){
            Log.e("making json error", e.getMessage());
        }

        JsonObjectRequest jor = new JsonObjectRequest(POST, Links.LOGIN, obj, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                SharedPreferences.Editor editor = context.getSharedPreferences("gest_res", Context.MODE_PRIVATE).edit();

                try {
                    editor.putInt("idEmp", Integer.parseInt(response.getString("id_emp")));
                    editor.apply();
                } catch (JSONException e) {
                    Log.e("json error", e.getMessage());
                }
                callback.onSuccess();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error.networkResponse != null) {
                    if (error.networkResponse.statusCode == 406)
                        callback.onError(false);
                }else
                    callback.onError(true);
            }
        });

        jor.setRetryPolicy(new DefaultRetryPolicy());
        queue.add(jor);


    }

}
