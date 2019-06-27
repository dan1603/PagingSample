package com.kalashnyk.denys.pagingsample.repository.server.paging;

import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.DataSource;

import com.kalashnyk.denys.pagingsample.repository.database.entities.UserEntity;

import rx.subjects.ReplaySubject;

public class NetUsersDataSourceFactory extends DataSource.Factory {

    private static final String TAG = NetUsersDataSourceFactory.class.getSimpleName();
    private MutableLiveData<NetUsersPageKeyedDataSource> mNetworkStatus;
    private NetUsersPageKeyedDataSource mUsersPageKeyedDataSource;

    public NetUsersDataSourceFactory() {
        this.mNetworkStatus = new MutableLiveData<>();
        mUsersPageKeyedDataSource = new NetUsersPageKeyedDataSource();
    }


    @Override
    public DataSource create() {
        mNetworkStatus.postValue(mUsersPageKeyedDataSource);
        return mUsersPageKeyedDataSource;
    }

    public MutableLiveData<NetUsersPageKeyedDataSource> getmNetworkStatus() {
        return mNetworkStatus;
    }

    public ReplaySubject<UserEntity> getUsers() {
        return mUsersPageKeyedDataSource.getUsers();
    }

}
