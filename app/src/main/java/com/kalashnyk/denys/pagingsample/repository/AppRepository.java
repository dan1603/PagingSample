package com.kalashnyk.denys.pagingsample.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.paging.PagedList;
import android.content.Context;
import android.util.Log;

import com.kalashnyk.denys.pagingsample.repository.database.AppDatabase;
import com.kalashnyk.denys.pagingsample.repository.database.entities.UserEntity;
import com.kalashnyk.denys.pagingsample.repository.server.ServerCommunicator;
import com.kalashnyk.denys.pagingsample.repository.server.paging.NetUsersDataSourceFactory;
import com.kalashnyk.denys.pagingsample.repository.database.entities.NetworkState;

import rx.schedulers.Schedulers;

public class AppRepository {

    private static AppRepository mInstance;
    final private ServerCommunicator mCommunicator;
    final private AppDatabase mDatabase;
    final private MediatorLiveData mLiveDataMerger;
    //final private MediatorLiveData mLiveDataMergerBug;

    private AppRepository(Context context) {
        NetUsersDataSourceFactory dataSourceFactory = new NetUsersDataSourceFactory();

        mCommunicator = new ServerCommunicator(dataSourceFactory, boundaryCallback);
        mDatabase = AppDatabase.getInstance(context.getApplicationContext());

        mLiveDataMerger = new MediatorLiveData<>();

        mLiveDataMerger.addSource(mCommunicator.getPagedUsers(), mLiveDataMerger::setValue);

        dataSourceFactory.getUsers().
                observeOn(Schedulers.io()).
                subscribe(user -> {
                    mDatabase.mUserDao().insertUser(user);
                });
    }

    private PagedList.BoundaryCallback<UserEntity> boundaryCallback = new PagedList.BoundaryCallback<UserEntity>() {
        @Override
        public void onZeroItemsLoaded() {
            super.onZeroItemsLoaded();
            mLiveDataMerger.addSource(mDatabase.getUsers(), value -> {
                mLiveDataMerger.setValue(value);
                mLiveDataMerger.removeSource(mDatabase.getUsers());
            });
        }
    };

    public static AppRepository getInstance(Context context){
        if(mInstance == null){
            mInstance = new AppRepository(context);
        }
        return mInstance;
    }

    public LiveData<PagedList<UserEntity>> getUsers(){
        return mLiveDataMerger;
    }

    public void updateUser(UserEntity user) {
        mDatabase.mUserDao().updateUser(user);
    }

    public LiveData<NetworkState> getNetworkState() {
        return mCommunicator.getNetworkState();
    }
}
