package com.andre_fernando.easybakerecipes.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {RecipeTable.class, IngredientsTable.class, StepsTable.class, LastJsonTable.class},version = 1,exportSchema = false)
public abstract class RecipeDB extends RoomDatabase{
    private static final String DATABASE_NAME = "Recipe.db";

    private static RecipeDB DB;

    public abstract RecipeTableDAO recipeDAO();
    public abstract IngredientsTableDAO ingredientsDAO();
    public abstract StepsTableDAO stepsDAO();
    public abstract LastJsonTableDAO jsonDAO();

    public static RecipeDB createDB(Context context){
        if (DB==null) {
            DB = Room.databaseBuilder(context.getApplicationContext(), RecipeDB.class , DATABASE_NAME).build();
        }
        return DB;
    }
}
