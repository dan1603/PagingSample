package com.kalashnyk.denys.pagingsample.repository.database.paging;

import android.arch.paging.DataSource;

import com.kalashnyk.denys.pagingsample.repository.database.dao.UserDao;

public class UsersDataSourceFactory extends DataSource.Factory {

    private UsersPageKeyedDataSource usersPageKeyedDataSource;
    public UsersDataSourceFactory(UserDao dao) {
        usersPageKeyedDataSource = new UsersPageKeyedDataSource(dao);
    }

    @Override
    public DataSource create() {
        return usersPageKeyedDataSource;
    }

}
