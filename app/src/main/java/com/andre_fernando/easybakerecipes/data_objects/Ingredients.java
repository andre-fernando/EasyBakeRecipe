package com.andre_fernando.easybakerecipes.data_objects;


import android.os.Parcel;
import android.os.Parcelable;

import com.andre_fernando.easybakerecipes.db.IngredientsTable;

import java.util.ArrayList;

public class Ingredients implements Parcelable {
    @SuppressWarnings("CanBeFinal")
    private double quantity;
    @SuppressWarnings("CanBeFinal")
    private String measure;
    @SuppressWarnings("CanBeFinal")
    private String ingredient;

    public Ingredients(double quantity, String measure, String ingredient) {
        this.quantity = quantity;
        this.measure = measure;
        this.ingredient = ingredient;
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

    public String getMeasure() {
        return measure;
    }

    public String getIngredient() {
        return ingredient;
    }

    public String getQuantityWithMeasure(){return String.format("%s %s",quantity,measure);}

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

    private Ingredients(Parcel in) {
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
