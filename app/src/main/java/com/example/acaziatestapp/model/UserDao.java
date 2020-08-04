package com.example.acaziatestapp.model;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE )
    void insert(User user);

    @Query("SELECT * FROM tbl_user")
    LiveData<List<User>> getAll();
}
