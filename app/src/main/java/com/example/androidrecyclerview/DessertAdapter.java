package com.example.androidrecyclerview;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.androidrecyclerview.databinding.ItemLayoutBinding;

import java.util.List;

class DessertAdapter extends RecyclerView.Adapter<DessertAdapter.GridViewHolder> {
    private List<Dessert> desserts;
    private Context context;

    private ItemLayoutBinding itemLayoutBinding;

    public DessertAdapter(Context context, List<Dessert> desserts) {
        this.desserts = desserts;
        this.context = context;
    }

    @NonNull
    @Override
    public GridViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        itemLayoutBinding = ItemLayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new GridViewHolder(itemLayoutBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull GridViewHolder holder, int position) {
        final String
                id = desserts.get(position).getIdMeal(),
                meal = desserts.get(position).getStrMeal(),
                photo = desserts.get(position).getStrMealThumb();

        itemLayoutBinding.tvMeal.setText(meal);

        Glide.with(context)
                .load(photo)
                .centerCrop()
                .placeholder(R.drawable.ic_image_search_grey_24)
                .into(itemLayoutBinding.imgMeal);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, DetilActivity.class);
                i.putExtra("i_idMeal", id);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        });

        holder.setIsRecyclable(false); //agar data yang ditampilkan stabil
    }

    @Override
    public int getItemCount() {
        return desserts.size();
    }

    public class GridViewHolder extends RecyclerView.ViewHolder {
        ItemLayoutBinding itemLayoutBinding;

        public GridViewHolder(@NonNull ItemLayoutBinding binding) {
            super(binding.getRoot());
            itemLayoutBinding = binding;
        }
    }
}
