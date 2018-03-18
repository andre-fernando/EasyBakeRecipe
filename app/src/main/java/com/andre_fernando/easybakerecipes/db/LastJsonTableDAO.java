package com.andre_fernando.easybakerecipes.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

@Dao
public interface LastJsonTableDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertJson(LastJsonTable json);

    @Query("select json from LastJsonTable where json_id = 1")
    String getJsonString();

// --Commented out by Inspection START (6/3/18 4:34 PM):
//    @Query("delete from LastJsonTable where json_id= 1")
//    void removeJsonString();
// --Commented out by Inspection STOP (6/3/18 4:34 PM)
}
