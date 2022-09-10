package com.example.assignment_4;

import android.content.Context;
import android.view.Display;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {ModelGPS.class, ModelAcceleration.class, ModelAccelerometer.class, ModelLight.class, ModelGryroscope.class, ModelProximity.class, ModelOrientation.class},version = 1)

public abstract class MyDatabase extends RoomDatabase {

    public abstract Proximity_DAO proximity_dao();
    public abstract Orientation_DAO orientation_dao();
    public abstract Accelerometer_DAO accelerometer_dao();
    public abstract Gyroscope_DAO gyroscope_dao();
    public abstract Acceleration_DAO acceleration_dao();
    public abstract Light_DAO light_dao();
    public abstract GPS_DAO gps_dao();

    public static MyDatabase myDatabase;

    public static MyDatabase getInstance(Context context){
        if(myDatabase == null)
            myDatabase = Room.databaseBuilder(context.getApplicationContext(), MyDatabase.class, "my_database").fallbackToDestructiveMigration().allowMainThreadQueries().build();

        return myDatabase;
    }
}
