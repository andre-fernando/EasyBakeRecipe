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

    @Query("select * from RecipeTable where id = :ID")
    RecipeTable getRecipeWithId(int ID);

    @Query("delete from RecipeTable")
    void deleteAll();

    @Query("delete from RecipeTable where id = :ID")
    void deleteRecipeWithId(int ID);

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    void insertRecipe(RecipeTable recipe);

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    void insertMultipleRecipe(RecipeTable... recipes);
}
