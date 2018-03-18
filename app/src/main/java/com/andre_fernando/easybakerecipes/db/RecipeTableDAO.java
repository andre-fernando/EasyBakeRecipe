package com.andre_fernando.easybakerecipes.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.database.Cursor;

import java.util.List;

@Dao
public interface RecipeTableDAO {

    @Query("select * from RecipeTable")
    List<RecipeTable> getAllRecipes();

    @Query("select * from RecipeTable")
    Cursor cp_getAllRecipes();

// --Commented out by Inspection START (6/3/18 4:37 PM):
//    @Query("select * from RecipeTable where id = :ID")
//    RecipeTable getRecipeWithId(int ID);
// --Commented out by Inspection STOP (6/3/18 4:37 PM)

// --Commented out by Inspection START (6/3/18 4:37 PM):
//    @Query("delete from RecipeTable")
//    void deleteAll();
// --Commented out by Inspection STOP (6/3/18 4:37 PM)

// --Commented out by Inspection START (6/3/18 4:37 PM):
//    @Query("delete from RecipeTable where id = :ID")
//    void deleteRecipeWithId(int ID);
// --Commented out by Inspection STOP (6/3/18 4:37 PM)

// --Commented out by Inspection START (6/3/18 4:37 PM):
//    @Insert (onConflict = OnConflictStrategy.REPLACE)
//    void insertRecipe(RecipeTable recipe);
// --Commented out by Inspection STOP (6/3/18 4:37 PM)

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    void insertMultipleRecipe(RecipeTable... recipes);
}
