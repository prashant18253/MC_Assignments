package com.example.assignment_4;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.sql.Timestamp;

@Entity(tableName = "table_proximity")
public class ModelProximity {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private long timestamp;

    @ColumnInfo(name = "Value_1")
    private float value1;

    public ModelProximity(@NonNull long timestamp, float value1) {
        this.timestamp = timestamp;
        this.value1 = value1;
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
