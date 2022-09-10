package com.example.assignment_4;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;
@Dao
public interface Proximity_DAO {
    @Query("Select * from table_proximity")
    List<ModelProximity> getList();

    @Insert
    void insert(ModelProximity... myModel);

    @Query("SELECT * FROM ( SELECT * FROM table_proximity ORDER BY timestamp DESC LIMIT 10 )Var1 ORDER BY timestamp ASC")
    List<ModelProximity> getListItems();
}
