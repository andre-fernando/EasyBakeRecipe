package com.andre_fernando.easybakerecipes.components;


import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.andre_fernando.easybakerecipes.R;
import com.andre_fernando.easybakerecipes.data_objects.Ingredients;
import com.andre_fernando.easybakerecipes.data_objects.Recipe;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * This class is passes the inner RemoteViewFactory to the
 * widgets list view.
 */
public class Widget_Service extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new Remote_Ingredients_Adapter(this.getApplicationContext());
    }

    public class Remote_Ingredients_Adapter implements RemoteViewsService.RemoteViewsFactory {

        private final Context context;
        private ArrayList<Recipe> recipe_list;
        private ArrayList<Ingredients> ingredients;


        Remote_Ingredients_Adapter(Context context) {
            this.context = context;

            if (recipe_list == null){
                try {
                    recipe_list = new RecipeFromContentProvider().execute().get();
                    ingredients = recipe_list.get(RecipeAPI.getLastSeenRecipeNo()).getIngredients();
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void onCreate() {
        }

        @Override
        public void onDataSetChanged() {
            ingredients = recipe_list.get(RecipeAPI.getLastSeenRecipeNo()).getIngredients();
        }

        @Override
        public void onDestroy() {

        }

        @Override
        public int getCount() {
            return ingredients.size();
        }

        @Override
        public RemoteViews getViewAt(int i) {
            RemoteViews row = new RemoteViews(context.getPackageName(), R.layout.ingredients_adapter_layout);
            Ingredients x = ingredients.get(i);
            row.setTextViewText(R.id.tv_ingredient_name,x.getIngredient());
            row.setTextViewText(R.id.tv_ingredient_quantity,x.getQuantityWithMeasure());
            return row;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }
    }
}
