package com.andre_fernando.easybakerecipes.components.adapters;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.andre_fernando.easybakerecipes.R;
import com.andre_fernando.easybakerecipes.data_objects.Ingredients;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Ingredients_Adapter extends RecyclerView.Adapter<Ingredients_Adapter.ViewHolder> {
    private ArrayList<Ingredients> ingredients_list;

    public Ingredients_Adapter(ArrayList<Ingredients> ingredients_list) {
        this.ingredients_list = ingredients_list;
    }

    public void RefreshData(ArrayList<Ingredients> ingredients_list){
        this.ingredients_list = ingredients_list;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.ingredients_adapter_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder vh, int position) {
        vh.name.setText(ingredients_list.get(position).getIngredient());
        vh.quantity.setText(ingredients_list.get(position).getQuantityWithMeasure());
    }

    @Override
    public int getItemCount() {
        return ingredients_list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_ingredient_name)
        TextView name;

        @BindView(R.id.tv_ingredient_quantity)
        TextView quantity;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

}
