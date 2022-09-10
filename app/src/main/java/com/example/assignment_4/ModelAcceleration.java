package com.example.assignment_4;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.sql.Timestamp;

@Entity(tableName = "table_acceleration")
public class ModelAcceleration {

    @PrimaryKey
    @NonNull
    private long timestamp;

    @ColumnInfo(name = "Value_1")
    private float value1;

    @ColumnInfo(name = "Value_2")
    private float value2;

    @ColumnInfo(name = "Value_3")
    private float value3;

    public ModelAcceleration(@NonNull long timestamp, float value1, float value2, float value3) {
        this.timestamp = timestamp;
        this.value1 = value1;
        this.value2 = value2;
        this.value3 = value3;
    }


    public float getValue3() {
        return value3;
    }

    public void setValue3(float value3) {
        this.value3 = value3;
    }

    public float getValue2() {
        return value2;
    }

    public void setValue2(float value2) {
        this.value2 = value2;
    }

    public float getValue1() {
        return value1;
    }

    public void setValue1(float value1) {
        this.value1 = value1;
    }

    @NonNull
    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(@NonNull long timestamp) {
        this.timestamp = timestamp;
    }

}
