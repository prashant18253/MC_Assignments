package com.example.assignment_5;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MYDao{
    @Query("Select * from my_table")
    List<Model> getList();

    @Insert
    void insert(Model... myModel);
}
