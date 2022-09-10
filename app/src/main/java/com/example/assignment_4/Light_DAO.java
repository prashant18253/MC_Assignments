package com.example.assignment_4;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;
@Dao
public interface Light_DAO {
    @Query("Select * from table_light")
    List<ModelLight> getList();

    @Insert
    void insert(ModelLight... myModel);
}
