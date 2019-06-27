package com.kalashnyk.denys.pagingsample.repository.server;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;

import com.kalashnyk.denys.pagingsample.repository.database.entities.UserEntity;
import com.kalashnyk.denys.pagingsample.repository.server.paging.NetUsersDataSourceFactory;
import com.kalashnyk.denys.pagingsample.repository.server.paging.NetUsersPageKeyedDataSource;
import com.kalashnyk.denys.pagingsample.repository.database.entities.NetworkState;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static com.kalashnyk.denys.pagingsample.utils.Constants.LOADING_PAGE_SIZE;
import static com.kalashnyk.denys.pagingsample.utils.Constants.NUMBERS_OF_THREADS;

public class ServerCommunicator {

    final private static String TAG = ServerCommunicator.class.getSimpleName();
    final private LiveData<PagedList<UserEntity>> usersPaged;
    final private LiveData<NetworkState> networkState;

    public ServerCommunicator(NetUsersDataSourceFactory dataSourceFactory, PagedList.BoundaryCallback<UserEntity> boundaryCallback){
        PagedList.Config pagedListConfig = (new PagedList.Config.Builder()).setEnablePlaceholders(false)
                .setInitialLoadSizeHint(LOADING_PAGE_SIZE).setPageSize(LOADING_PAGE_SIZE).build();

        networkState = Transformations.switchMap(dataSourceFactory.getmNetworkStatus(),
                (Function<NetUsersPageKeyedDataSource, LiveData<NetworkState>>)
                        NetUsersPageKeyedDataSource::getmNetworkState);

        Executor executor = Executors.newFixedThreadPool(NUMBERS_OF_THREADS);

        LivePagedListBuilder livePagedListBuilder = new LivePagedListBuilder(dataSourceFactory, pagedListConfig);

        usersPaged = livePagedListBuilder.
                setFetchExecutor(executor).
                setBoundaryCallback(boundaryCallback).
                build();

    }

    public LiveData<PagedList<UserEntity>> getPagedUsers(){
        return  usersPaged;
    }
    public LiveData<NetworkState> getNetworkState() { return networkState; }
}
