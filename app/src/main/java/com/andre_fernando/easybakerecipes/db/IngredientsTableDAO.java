package com.andre_fernando.easybakerecipes.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.database.Cursor;

import java.util.List;

@Dao
public interface IngredientsTableDAO {

    @Query("select * from IngredientsTable")
    List<IngredientsTable> getAllIngredients();

    @Query("select * from IngredientsTable")
    Cursor cp_getAllIngredients();

// --Commented out by Inspection START (6/3/18 4:33 PM):
//    @Query("select * from IngredientsTable where recipe_id = :ID")
//    List<IngredientsTable> getIngredientsWithId(int ID);
// --Commented out by Inspection STOP (6/3/18 4:33 PM)

// --Commented out by Inspection START (6/3/18 4:33 PM):
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    void insertIngredient(IngredientsTable ingredientsTable);
// --Commented out by Inspection STOP (6/3/18 4:33 PM)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMultipleIngredients(IngredientsTable... ingredientsTable);

// --Commented out by Inspection START (6/3/18 4:33 PM):
//    @Query("delete from IngredientsTable where recipe_id = :ID")
//    void removeIngredientsWithId(int ID);
// --Commented out by Inspection STOP (6/3/18 4:33 PM)

// --Commented out by Inspection START (6/3/18 4:33 PM):
//    @Query("delete from IngredientsTable")
//    void deleteAll();
// --Commented out by Inspection STOP (6/3/18 4:33 PM)
}
