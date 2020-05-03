package com.example.intern_food.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.intern_food.Model.Meal;
import com.example.intern_food.R;

import java.util.List;

public class MealAdapter extends RecyclerView.Adapter<MealAdapter.MealViewHolder>{
    Context ctx;
    List<Meal> mealList;

    public MealAdapter(Context ctx, List<Meal> mealList) {
        this.ctx = ctx;
        this.mealList = mealList;
    }

    @NonNull
    @Override
    public MealAdapter.MealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(ctx).inflate(R.layout.content_recycler_food_list,parent,false);
        MealViewHolder mealViewHolder=new MealViewHolder(view);
        return mealViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MealAdapter.MealViewHolder holder, int position) {
    final Meal meal =mealList.get(position);
    holder.mealName.setText(meal.getName());
    holder.time.setText(meal.getTime());
    }

    @Override
    public int getItemCount() {
        return mealList.size();
    }

    public class MealViewHolder extends RecyclerView.ViewHolder {
        TextView mealName,time;
        public MealViewHolder(@NonNull View itemView) {
            super(itemView);
            mealName=itemView.findViewById(R.id.MealName);
            time=itemView.findViewById(R.id.time);
        }
    }
}
