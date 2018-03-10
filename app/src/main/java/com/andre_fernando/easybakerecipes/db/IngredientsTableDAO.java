package com.andre_fernando.easybakerecipes.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface IngredientsTableDAO {

    @Query("select * from IngredientsTable")
    List<IngredientsTable> getAllIngredients();

    @Query("select * from IngredientsTable")
    Cursor cp_getAllIngredients();

    @Query("select * from IngredientsTable where recipe_id = :ID")
    List<IngredientsTable> getIngredientsWithId(int ID);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertIngredient(IngredientsTable ingredientsTable);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMultipleIngredients(IngredientsTable... ingredientsTable);

    @Query("delete from IngredientsTable where recipe_id = :ID")
    void removeIngredientsWithId(int ID);

    @Query("delete from IngredientsTable")
    void deleteAll();
}
