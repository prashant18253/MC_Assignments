package com.example.assignment_4;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;
@Dao
public interface Orientation_DAO {
    @Query("Select * from table_orientation")
    List<ModelOrientation> getList();

    @Insert
    void insert(ModelOrientation... myModel);
}
