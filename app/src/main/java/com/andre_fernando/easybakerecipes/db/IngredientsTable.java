package com.andre_fernando.easybakerecipes.db;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.database.Cursor;
import android.support.annotation.NonNull;

import com.andre_fernando.easybakerecipes.data_objects.Ingredients;
import com.andre_fernando.easybakerecipes.data_objects.Recipe;

import java.util.ArrayList;
import java.util.List;

@Entity
public class IngredientsTable {
    @PrimaryKey(autoGenerate = true)
    public int key;

    @NonNull
    public int recipe_id;

    public double quantity;

    public String measure;

    public String ingredient;

    public IngredientsTable(@NonNull int recipe_id, double quantity, String measure, String ingredient) {
        this.recipe_id = recipe_id;
        this.quantity = quantity;
        this.measure = measure;
        this.ingredient = ingredient;
    }

    @Ignore
    public static ArrayList<IngredientsTable> fromIngredientsCursor(Cursor c){
        ArrayList<IngredientsTable> toReturn = new ArrayList<>();
        if (c != null) {
            while (c.moveToNext()) {
                toReturn.add(new IngredientsTable(
                        c.getInt(1),
                        c.getDouble(2),
                        c.getString(3),
                        c.getString(4)
                ));
            }
            c.close();
        }
        return toReturn;
    }

    @Ignore
    public static ArrayList<IngredientsTable> fromRecipeObj(Recipe recipe){
        int Recipe_Id = recipe.getId();
        ArrayList<Ingredients> ingredients = new ArrayList<>();
        ArrayList<IngredientsTable> toReturn = new ArrayList<>();
        ingredients.addAll(recipe.getIngredients());
        for (Ingredients i : ingredients){
            toReturn.add(new IngredientsTable
                            (Recipe_Id,
                            i.getQuantity(),
                            i.getMeasure(),
                            i.getIngredient()));
        }
        return toReturn;
    }

    @Ignore
    public static IngredientsTable[] fromRecipeObjArray(ArrayList<Recipe> recipes){
        ArrayList<IngredientsTable> ingredients = new ArrayList<>();
        for (Recipe recipe: recipes){
            ingredients.addAll(fromRecipeObj(recipe));
        }
        IngredientsTable[] toReturn = new IngredientsTable[ingredients.size()];
        for (int x=0;x<ingredients.size();x++){
            toReturn[x]=ingredients.get(x);
        }
        return toReturn;
    }


}
