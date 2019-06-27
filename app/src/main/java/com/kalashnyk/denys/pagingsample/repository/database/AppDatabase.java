package com.kalashnyk.denys.pagingsample.repository.database;

import android.arch.lifecycle.LiveData;
import android.arch.paging.DataSource;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.kalashnyk.denys.pagingsample.repository.database.dao.UserDao;
import com.kalashnyk.denys.pagingsample.repository.database.entities.UserEntity;
import com.kalashnyk.denys.pagingsample.repository.database.paging.UsersDataSourceFactory;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static com.kalashnyk.denys.pagingsample.utils.Constants.DATA_BASE_NAME;
import static com.kalashnyk.denys.pagingsample.utils.Constants.NUMBERS_OF_THREADS;

@Database(entities = {UserEntity.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase mInstance;
    public abstract UserDao mUserDao();
    private static final Object mLock = new Object();
    private LiveData<PagedList<UserEntity>> mUsersPaged;

    public static AppDatabase getInstance(Context context) {
        synchronized (mLock) {
            if (mInstance == null) {
                mInstance = Room.databaseBuilder(context.getApplicationContext(),
                        AppDatabase.class, DATA_BASE_NAME)
                        .allowMainThreadQueries()
                        .build();
                mInstance.init();
            }
            return mInstance;
        }
    }

    private void init() {
        PagedList.Config pagedListConfig = (new PagedList.Config.Builder()).setEnablePlaceholders(false)
                .setInitialLoadSizeHint(Integer.MAX_VALUE).setPageSize(Integer.MAX_VALUE).build();
        Executor executor = Executors.newFixedThreadPool(NUMBERS_OF_THREADS);
        UsersDataSourceFactory dataSourceFactory = new UsersDataSourceFactory(mUserDao());
        LivePagedListBuilder livePagedListBuilder = new LivePagedListBuilder(dataSourceFactory, pagedListConfig);
        mUsersPaged = livePagedListBuilder.setFetchExecutor(executor).build();
    }

    public LiveData<PagedList<UserEntity>> getUsers() {
        return mUsersPaged;
    }

//    public LiveData<PagedList<UserEntity>> getBugUsers() {
//        PagedList.Config pagedListConfig = (new PagedList.Config.Builder()).setEnablePlaceholders(false)
//                .setInitialLoadSizeHint(Integer.MAX_VALUE).setPageSize(Integer.MAX_VALUE).build();
//        Executor executor = Executors.newFixedThreadPool(NUMBERS_OF_THREADS);
//        DataSource.Factory<Integer, UserEntity> datasource = mUserDao().getUsersBug();
//        LivePagedListBuilder livePagedListBuilder = new LivePagedListBuilder(datasource, pagedListConfig);
//        return livePagedListBuilder.setFetchExecutor(executor).build();
//    }
}
