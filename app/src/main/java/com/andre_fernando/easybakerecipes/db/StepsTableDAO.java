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

    @Query("select * from StepsTable where recipe_id = :ID")
    List<StepsTable> getStepsWithId(int ID);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertSteps(StepsTable stepsTable);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMultipleSteps(StepsTable... stepsTable);

    @Query("delete from StepsTable where recipe_id = :ID")
    void removeStepsWithId(int ID);

    @Query("delete from StepsTable")
    void deleteAll();

}
