package com.example.assignment_5;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Model.class}, version = 2)
public abstract class MyDatabase extends RoomDatabase {
    public abstract MYDao dao();
    public static MyDatabase myDatabase;

    public static MyDatabase getInstance(Context context){
        if(myDatabase == null)
            myDatabase = Room.databaseBuilder(context.getApplicationContext(), MyDatabase.class, "my_database").fallbackToDestructiveMigration().allowMainThreadQueries().build();

        return myDatabase;
    }

}
