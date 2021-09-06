package com.example.androidrecyclerview;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.androidrecyclerview.databinding.ActivityDetilBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DetilActivity extends AppCompatActivity {

    private ActivityDetilBinding binding;
//    ImageView iv;
//    TextView tvInstruction, tvName;
//    Toolbar toolbar;
    String meal, photo, instruction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_detil);
        binding = ActivityDetilBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        iv = (ImageView) findViewById(R.id.iv_image);
//        tvName = (TextView) findViewById(R.id.detail_tv_name);
//        tvInstruction = (TextView) findViewById(R.id.detail_tv_instruction);

        Intent i = getIntent();
        String id = i.getStringExtra("i_idMeal");

        load(id);

        //tampilkan tombol panah back
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void load(String id) {

        binding.progressBar.setVisibility(ProgressBar.VISIBLE);

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url = "https://www.themealdb.com/api/json/v1/1/lookup.php?i=" + id;

        JsonObjectRequest jsObjRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        //Log.d("Events: ", response.toString());
                        try {
                            JSONArray jsonArray = response.getJSONArray("meals");
                            JSONObject data = jsonArray.getJSONObject(0);

                            meal = data.getString("strMeal");
                            instruction = data.getString("strInstructions");
                            photo = data.getString("strMealThumb");

                            Glide.with(getApplicationContext())
                                    .load(photo)
                                    .into(binding.ivImage);

                            binding.tvName.setText(meal);
                            binding.tvInstruction.setText(instruction);
                            binding.scrollView.setVisibility(ScrollView.VISIBLE);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } finally {
                            binding.progressBar.setVisibility(ProgressBar.GONE);
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO Auto-generated method stub
                Log.d("Events: ", error.toString());
                Toast.makeText(getApplicationContext(), "Please check your connection", Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(jsObjRequest);
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    //kontrol panah back
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
