package com.example.sublet.model;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.example.sublet.MyApplication;

@Database(entities = {Post.class}, version = 20)
abstract class AppLocalDbRepository extends RoomDatabase {
    public abstract PostDao postDao();
}
public class AppLocalDb{
    static public AppLocalDbRepository db = Room.databaseBuilder(MyApplication.getContext(),
                    AppLocalDbRepository.class, "dbFileName.db").fallbackToDestructiveMigration().build();
}