package com.andre_fernando.easybakerecipes.data_objects;


import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.andre_fernando.easybakerecipes.db.IngredientsTable;

import java.util.ArrayList;

public class Ingredients implements Parcelable {
    private double quantity;
    private String measure;
    private String ingredient;

    public Ingredients(double quantity, String measure, String ingredient) {
        this.quantity = quantity;
        this.measure = measure;
        this.ingredient = ingredient;
    }

    public static String ConvertToString(ArrayList<Ingredients> i){
        String toReturn = "";
        for (Ingredients x : i){
            toReturn = toReturn + String.format("%s %s/n",x.getIngredient(),x.getQuantityWithMeasure());
        }
        return toReturn;
    }

    public static ArrayList<Ingredients> getIngredientsWithId(Cursor cursor, int recipe_id){
        ArrayList<Ingredients> toReturn = new ArrayList<>();
        while (cursor.moveToNext()){
            int tempid;
            double tempquantity;
            String tempmeasure, tempingredient;

            cursor.moveToFirst();
            tempid = cursor.getInt(1);
            if (recipe_id == tempid){
                tempquantity=cursor.getDouble(2);
                tempmeasure=cursor.getString(3);
                tempingredient=cursor.getString(4);
                toReturn.add(new Ingredients(tempquantity,tempmeasure,tempingredient));
            }
        }
        return toReturn;
    }

    public static ArrayList<Ingredients> fromIngredientsTable
            (int Recipe_Id, ArrayList<IngredientsTable> ingredientsTables){
        ArrayList<Ingredients> toReturn = new ArrayList<>();
        for (IngredientsTable i: ingredientsTables){
            if (i.recipe_id == Recipe_Id){
                toReturn.add(new Ingredients(i.quantity,i.measure,i.ingredient));
            }
        }
        return toReturn;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getIngredient() {
        return ingredient;
    }

    public String getQuantityWithMeasure(){return String.format("%s %s",quantity,measure);}

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(this.quantity);
        dest.writeString(this.measure);
        dest.writeString(this.ingredient);
    }

    protected Ingredients(Parcel in) {
        this.quantity = in.readDouble();
        this.measure = in.readString();
        this.ingredient = in.readString();
    }

    public static final Parcelable.Creator<Ingredients> CREATOR = new Parcelable.Creator<Ingredients>() {
        @Override
        public Ingredients createFromParcel(Parcel source) {
            return new Ingredients(source);
        }

        @Override
        public Ingredients[] newArray(int size) {
            return new Ingredients[size];
        }
    };
}
