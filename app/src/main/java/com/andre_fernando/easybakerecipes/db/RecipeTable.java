package com.andre_fernando.easybakerecipes.db;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.database.Cursor;
import android.support.annotation.NonNull;

import com.andre_fernando.easybakerecipes.data_objects.Recipe;

import java.util.ArrayList;
import java.util.List;

@Entity
public class RecipeTable {

    @PrimaryKey
    @NonNull
    public int id;

    public String name;

    public double servings;

    public String image;


    public RecipeTable(@NonNull int id, String name, double servings, String image) {
        this.id = id;
        this.name = name;
        this.servings = servings;
        this.image = image;
    }


    @Ignore
    public static RecipeTable fromRecipeObj(Recipe recipe){
        String NAME, IMAGE;
        int ID;
        double SERVINGS;
        ID = recipe.getId();
        NAME = recipe.getName();
        SERVINGS = recipe.getServings();
        IMAGE=recipe.getImage();
        return new RecipeTable(ID,NAME,SERVINGS,IMAGE);
    }

    @Ignore
    public static ArrayList<RecipeTable> fromRecipeCursor(Cursor c){
        ArrayList<RecipeTable> toReturn = new ArrayList<>();
        if (c != null){
            while (c.moveToNext()){
                toReturn.add(new RecipeTable(
                        c.getInt(0),
                        c.getString(1),
                        c.getDouble(2),
                        c.getString(3)
                ));
            }
            c.close();
        }
        return toReturn;
    }

    @Ignore
    public static RecipeTable[] fromRecipArray(ArrayList<Recipe> recipes){
        RecipeTable[] toReturn = new RecipeTable[recipes.size()];
        for (int x = 0;x<recipes.size();x++){
            toReturn[x]=fromRecipeObj(recipes.get(x));
        }
        return toReturn;
    }


}
