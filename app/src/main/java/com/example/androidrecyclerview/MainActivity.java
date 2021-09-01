package com.example.androidrecyclerview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.androidrecyclerview.databinding.ActivityMainBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<Dessert> desserts;

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                load();

                new Handler().postDelayed(new Runnable() {
                    @Override public void run() {
                        binding.swipeRefreshLayout.setRefreshing(false);
                    }
                }, 1000); // Delay in millis
            }
        });

        binding.swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        load();
    }

    private void showRecyclerGrid(){
        if(this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            binding.recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        }else{
            binding.recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        }

        DessertAdapter mAdapter = new DessertAdapter(this, desserts);
        binding.recyclerView.setAdapter(mAdapter);
        binding.recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    public void load(){
        binding.progressBar.setVisibility(ProgressBar.VISIBLE);

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url = "https://www.themealdb.com/api/json/v1/1/filter.php?c=Dessert";

        //Ambil JSON dari internet
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        String id, meal, photo;
                        desserts = new ArrayList<>();

                        try {
                            //ambil objek meals
                            JSONArray jsonArray = response.getJSONArray("meals");
                            desserts.clear();

                            //masukkan kedalam Arraylist
                            if (jsonArray.length() != 0) {
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject data = jsonArray.getJSONObject(i);

                                    id = data.getString("idMeal").trim();
                                    meal = data.getString("strMeal").trim();
                                    photo = data.getString("strMealThumb").trim();

                                    //masukkan kedalam Arraylist
                                    desserts.add(new Dessert(id, meal, photo ));
                                }

                                showRecyclerGrid();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        binding.progressBar.setVisibility(ProgressBar.GONE);
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                binding.progressBar.setVisibility(ProgressBar.GONE);
                Toast.makeText(MainActivity.this, "Connection problem!", Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(jsObjRequest);
    }
}
