package com.andre_fernando.easybakerecipes.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.database.Cursor;

import java.util.List;

@Dao
public interface StepsTableDAO {

    @Query("select * from StepsTable")
    List<StepsTable> getAllSteps();

    @Query("select * from StepsTable")
    Cursor cp_getAllSteps();

// --Commented out by Inspection START (6/3/18 4:38 PM):
//    @Query("select * from StepsTable where recipe_id = :ID")
//    List<StepsTable> getStepsWithId(int ID);
// --Commented out by Inspection STOP (6/3/18 4:38 PM)

// --Commented out by Inspection START (6/3/18 4:38 PM):
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    void insertSteps(StepsTable stepsTable);
// --Commented out by Inspection STOP (6/3/18 4:38 PM)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMultipleSteps(StepsTable... stepsTable);

// --Commented out by Inspection START (6/3/18 4:38 PM):
//    @Query("delete from StepsTable where recipe_id = :ID")
//    void removeStepsWithId(int ID);
// --Commented out by Inspection STOP (6/3/18 4:38 PM)

// --Commented out by Inspection START (6/3/18 4:38 PM):
//    @Query("delete from StepsTable")
//    void deleteAll();
// --Commented out by Inspection STOP (6/3/18 4:38 PM)

}
