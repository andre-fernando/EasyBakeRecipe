package com.andre_fernando.easybakerecipes.components.adapters;


import android.content.Context;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.andre_fernando.easybakerecipes.R;
import com.andre_fernando.easybakerecipes.data_objects.Recipe;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

// Recipe List adapter
public class Recipe_List_Adapter extends RecyclerView.Adapter<Recipe_List_Adapter.ViewHolder> {
    private final ArrayList<Recipe> recipe_list;
    private final Context context;

    public Recipe_List_Adapter(ArrayList<Recipe> recipe_list, FragmentActivity context) {
        this.recipe_list = recipe_list;
        this.context = context;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view =inflater.inflate(R.layout.recipe_list_adapter_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder view, int position) {
        view.tv_recipe_list_image.setText(recipe_list.get(position).getName());
        Glide.with(context)
                .load(Uri.parse(recipe_list.get(position).getImage()))
                .apply(new RequestOptions()
                .placeholder(R.drawable.ic_image_placeholder)
                .error(R.drawable.ic_image_placeholder))
                .into(view.iv_recipe_list_image);
    }

    @Override
    public int getItemCount() {
        return recipe_list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.iv_recipe_list_image)
        ImageView iv_recipe_list_image;

        @BindView(R.id.tv_recipe_list_name)
        TextView tv_recipe_list_image;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
