package com.andre_fernando.easybakerecipes.db;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class LastJsonTable {

    @PrimaryKey
    @NonNull
    public int json_id;

    @SuppressWarnings("WeakerAccess")
    public String json;

    public LastJsonTable(String json_string){
        this.json_id= 1;
        this.json=json_string;
    }


    public LastJsonTable(){}
}
