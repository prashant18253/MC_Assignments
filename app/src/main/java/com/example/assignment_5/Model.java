package com.example.assignment_5;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "my_table")
public class Model {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;

    @ColumnInfo(name = "Room")
    private int room;

    @ColumnInfo(name = "Value_1")
    private int value1;

    @ColumnInfo(name = "Value_2")
    private int value2;

    @ColumnInfo(name = "Value_3")
    private int value3;

    @ColumnInfo(name = "Value_4")
    private int value4;
//
//    @ColumnInfo(name = "Value_5")
//    private int value5;
//
//    @ColumnInfo(name = "Value_6")
//    private int value6;

    public int getValue1() {
        return value1;
    }

    public void setValue1(int value1) {
        this.value1 = value1;
    }

    public int getValue2() {
        return value2;
    }

    public void setValue2(int value2) {
        this.value2 = value2;
    }

    public int getValue3() {
        return value3;
    }

    public void setValue3(int value3) {
        this.value3 = value3;
    }

    public int getValue4() {
        return value4;
    }

    public void setValue4(int value4) {
        this.value4 = value4;
    }

    public int getRoom() {
        return room;
    }

    public void setRoom(int room) {
        this.room = room;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

//    public int getValue5() {
//        return value5;
//    }
//
//    public void setValue5(int value5) {
//        this.value5 = value5;
//    }
//
//    public int getValue6() {
//        return value6;
//    }
//
//    public void setValue6(int value6) {
//        this.value6 = value6;
//    }

//    public int getX() {
//        return x;
//    }
//
//    public void setX(int x) {
//        this.x = x;
//    }
//
//    public int getY() {
//        return y;
//    }
//
//    public void setY(int y) {
//        this.y = y;
//    }
}
