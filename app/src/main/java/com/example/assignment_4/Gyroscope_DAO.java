package com.example.assignment_4;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface Gyroscope_DAO {
    @Query("Select * from table_gyroscope")
    List<ModelGryroscope> getList();

    @Insert
    void insert(ModelGryroscope... myModel);
}
