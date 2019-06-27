package com.kalashnyk.denys.pagingsample.repository.database.dao;


import android.arch.paging.DataSource;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.kalashnyk.denys.pagingsample.repository.database.entities.UserEntity;

import java.util.List;

@Dao
public interface UserDao {

    @Query("SELECT * FROM users")
    List<UserEntity> getUsers();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUser(UserEntity user);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateUser(UserEntity user);

    @Query("SELECT * FROM users")
    DataSource.Factory<Integer, UserEntity> getUsersBug();

}
