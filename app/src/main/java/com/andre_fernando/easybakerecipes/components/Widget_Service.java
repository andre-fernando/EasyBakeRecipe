package com.andre_fernando.easybakerecipes.components;


import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.andre_fernando.easybakerecipes.R;
import com.andre_fernando.easybakerecipes.data_objects.Ingredients;

import java.util.ArrayList;

public class Widget_Service extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new Remote_Ingredients_Adapter(this.getApplicationContext(), intent);
    }

    public class Remote_Ingredients_Adapter implements RemoteViewsService.RemoteViewsFactory {

        private final Context context;
        private ArrayList<String> name,quantity;
        private Intent intent;


        public Remote_Ingredients_Adapter(Context context, Intent intent) {
            this.context = context;
            this.name = intent.getStringArrayListExtra("name");
            this.quantity = intent.getStringArrayListExtra("quantity");
            this.intent = intent;
        }

        @Override
        public void onCreate() {
        }

        @Override
        public void onDataSetChanged() {
            this.name = this.intent.getStringArrayListExtra("name");
            this.quantity = this.intent.getStringArrayListExtra("quantity");
        }

        @Override
        public void onDestroy() {

        }

        @Override
        public int getCount() {
            return name.size();
        }

        @Override
        public RemoteViews getViewAt(int i) {
            RemoteViews row = new RemoteViews(context.getPackageName(), R.layout.ingredients_adapter_layout);

            row.setTextViewText(R.id.tv_ingredient_name,name.get(i));
            row.setTextViewText(R.id.tv_ingredient_quantity,quantity.get(i));
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
