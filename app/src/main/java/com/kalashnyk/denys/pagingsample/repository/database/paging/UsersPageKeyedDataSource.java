package com.kalashnyk.denys.pagingsample.repository.database.paging;

import android.arch.paging.PageKeyedDataSource;
import android.support.annotation.NonNull;
import android.util.Log;

import com.kalashnyk.denys.pagingsample.repository.database.dao.UserDao;
import com.kalashnyk.denys.pagingsample.repository.database.entities.UserEntity;

import java.util.List;

public class UsersPageKeyedDataSource extends PageKeyedDataSource<String, UserEntity> {

    private final UserDao userDao;
    public UsersPageKeyedDataSource(UserDao dao) {
        userDao = dao;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<String> params, @NonNull final LoadInitialCallback<String, UserEntity> callback) {
        List<UserEntity> users = userDao.getUsers();
        if(users.size() != 0) {
            callback.onResult(users, "0", "1");
        }
    }

    @Override
    public void loadAfter(@NonNull LoadParams<String> params, final @NonNull LoadCallback<String, UserEntity> callback) {
    }

    @Override
    public void loadBefore(@NonNull LoadParams<String> params, @NonNull LoadCallback<String, UserEntity> callback) {
    }
}
