package com.example.assignment_4;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface Accelerometer_DAO {
    @Query("Select * from table_accelerometer")
    List<ModelAccelerometer> getList();

    @Insert
    void insert(ModelAccelerometer... myModel);

    @Query("SELECT * FROM ( SELECT * FROM table_accelerometer ORDER BY timestamp DESC LIMIT 10 )Var1 ORDER BY timestamp ASC")
    List<ModelAccelerometer> getListItems();
}
