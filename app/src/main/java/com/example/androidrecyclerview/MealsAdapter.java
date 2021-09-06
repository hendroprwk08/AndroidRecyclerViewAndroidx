package com.example.androidrecyclerview;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.androidrecyclerview.databinding.ItemRvBinding;

import java.util.List;

class MealsAdapter extends RecyclerView.Adapter<MealsAdapter.GridViewHolder> {
    private List<Meal> meals;
    private Context context;

    private ItemRvBinding itemRvBinding;

    public MealsAdapter(Context context, List<Meal> meals) {
        this.meals = meals;
        this.context = context;
    }

    @NonNull
    @Override
    public GridViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        itemRvBinding = ItemRvBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new GridViewHolder(itemRvBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull GridViewHolder holder, int position) {
        final String
                id = meals.get(position).getIdMeal(),
                meal = meals.get(position).getStrMeal(),
                photo = meals.get(position).getStrMealThumb();

        itemRvBinding.tvMeal.setText(meal);

        Glide.with(context)
                .load(photo)
                .centerCrop()
                .placeholder(R.drawable.ic_image_search_grey_24)
                .into(itemRvBinding.imgMeal);

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
        return meals.size();
    }

    public class GridViewHolder extends RecyclerView.ViewHolder {
        ItemRvBinding itemRvBinding;

        public GridViewHolder(@NonNull ItemRvBinding binding) {
            super(binding.getRoot());
            itemRvBinding = binding;
        }
    }
}
