package com.andre_fernando.easybakerecipes.db;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class LastJsonTable {

    @PrimaryKey
    @NonNull
    public int json_id;

    public String json;

    public LastJsonTable(@NonNull int ID, String json_string){
        this.json_id=ID;
        this.json=json_string;
    }


    public LastJsonTable(){}
}
