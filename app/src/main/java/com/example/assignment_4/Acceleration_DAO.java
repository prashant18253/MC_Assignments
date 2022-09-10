package com.example.assignment_4;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface Acceleration_DAO {
    @Query("Select * from table_acceleration")
    List<ModelAcceleration> getList();

    // Insert
    @Insert
    void insert(ModelAcceleration... myModel);

    @Query("SELECT * FROM ( SELECT * FROM table_acceleration ORDER BY timestamp DESC LIMIT 10 )Var1 ORDER BY timestamp ASC")
    List<ModelAcceleration> getListItems();


}
