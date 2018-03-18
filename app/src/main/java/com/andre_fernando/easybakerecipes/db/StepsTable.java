package com.andre_fernando.easybakerecipes.db;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.database.Cursor;

import com.andre_fernando.easybakerecipes.data_objects.Recipe;
import com.andre_fernando.easybakerecipes.data_objects.Steps;

import java.util.ArrayList;

@SuppressWarnings("CanBeFinal")
@Entity
public class StepsTable {
    @PrimaryKey(autoGenerate = true)
    public int key;

    public int recipe_id;

    public int id;

    public String short_description;

    public String description;

    public String video_url;

    public String thumbnail_url;


    public StepsTable(int recipe_id, int id, String short_description, String description, String video_url, String thumbnail_url) {
        this.recipe_id = recipe_id;
        this.id = id;
        this.short_description = short_description;
        this.description = description;
        this.video_url = video_url;
        this.thumbnail_url = thumbnail_url;
    }

    @Ignore
    public static ArrayList<StepsTable> fromStepsCursor(Cursor c){
        ArrayList<StepsTable> toReturn = new ArrayList<>();
        if (c != null){
            while (c.moveToNext()){
                toReturn.add(new StepsTable(
                        c.getInt(1),
                        c.getInt(2),
                        c.getString(3),
                        c.getString(4),
                        c.getString(5),
                        c.getString(6)
                ));
            }
            c.close();
        }
        return toReturn;
    }

    @Ignore
    private static ArrayList<StepsTable> fromRecipeObj(Recipe recipe){
        int Recipe_Id = recipe.getId();
        ArrayList<Steps> StepsObj = new ArrayList<>();
        ArrayList<StepsTable> toReturn = new ArrayList<>();
        StepsObj.addAll(recipe.getSteps());
        for (Steps s: StepsObj){
            toReturn.add(new StepsTable
                            (Recipe_Id,
                            s.getId(),
                            s.getShort_description(),
                            s.getDescription(),
                            s.getVideoString(),
                            s.getThumbnailUrl()));
        }
        return toReturn;
    }

    @Ignore
    public static StepsTable[] fromRecipeObjArray(ArrayList<Recipe> recipes){
        ArrayList<StepsTable> stepsTables = new ArrayList<>();
        for (Recipe recipe : recipes){
            stepsTables.addAll(fromRecipeObj(recipe));
        }
        StepsTable[] toReturn = new StepsTable[stepsTables.size()];
        for (int x=0;x<stepsTables.size();x++){
            toReturn[x]=stepsTables.get(x);
        }
        return toReturn;
    }
}
