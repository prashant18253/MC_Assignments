package com.example.assignment_4;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface GPS_DAO {
    @Query("Select * from table_gps")
    List<ModelGPS> getList();

    // Insert
    @Insert
    void insert(ModelGPS... myModel);

    @Query("SELECT * FROM ( SELECT * FROM table_gps ORDER BY id DESC LIMIT 3 )Var1 ORDER BY id ASC")
    List<ModelGPS> getListItems();
}
