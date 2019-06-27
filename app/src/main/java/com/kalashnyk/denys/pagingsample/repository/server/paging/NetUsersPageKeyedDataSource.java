package com.kalashnyk.denys.pagingsample.repository.server.paging;

import android.annotation.TargetApi;
import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.PageKeyedDataSource;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.kalashnyk.denys.pagingsample.repository.database.entities.UserEntity;
import com.kalashnyk.denys.pagingsample.repository.server.api.ApiService;
import com.kalashnyk.denys.pagingsample.repository.server.api.ApiServiceClient;
import com.kalashnyk.denys.pagingsample.repository.database.entities.NetworkState;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.subjects.ReplaySubject;

import static com.kalashnyk.denys.pagingsample.utils.Constants.ORDER_TYPE;

public class NetUsersPageKeyedDataSource extends PageKeyedDataSource<String, UserEntity> {

    private static final String TAG = NetUsersPageKeyedDataSource.class.getSimpleName();
    private final ApiService mApi;
    private final MutableLiveData mNetworkState;
    private final ReplaySubject<UserEntity> mUsersObservable;

    NetUsersPageKeyedDataSource() {
        mApi = ApiServiceClient.getClient();
        mNetworkState = new MutableLiveData();
        mUsersObservable = ReplaySubject.create();
    }

    public MutableLiveData getmNetworkState() {
        return mNetworkState;
    }

    public ReplaySubject<UserEntity> getUsers() {
        return mUsersObservable;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<String> params, @NonNull final LoadInitialCallback<String, UserEntity> callback) {
        Log.i(TAG, "Loading Initial Rang, Count " + params.requestedLoadSize);

        mNetworkState.postValue(NetworkState.LOADING);
        Call<ArrayList<UserEntity>> callBack = mApi.getUsers(ORDER_TYPE, 1);
        callBack.enqueue(new Callback<ArrayList<UserEntity>>() {
            @TargetApi(Build.VERSION_CODES.N)
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<ArrayList<UserEntity>> call, Response<ArrayList<UserEntity>> response) {
                if (response.isSuccessful()) {
                    callback.onResult(response.body(), Integer.toString(1), Integer.toString(2));
                    mNetworkState.postValue(NetworkState.LOADED);
                    response.body().forEach(mUsersObservable::onNext);
                } else {
                    Log.e("API CALL", response.message());
                    mNetworkState.postValue(new NetworkState(NetworkState.Status.FAILED, response.message()));
                }
            }

            @Override
            public void onFailure(Call<ArrayList<UserEntity>> call, Throwable t) {
                String errorMessage;
                if (t.getMessage() == null) {
                    errorMessage = "unknown error";
                } else {
                    errorMessage = t.getMessage();
                }
                mNetworkState.postValue(new NetworkState(NetworkState.Status.FAILED, errorMessage));
                callback.onResult(new ArrayList<>(), Integer.toString(1), Integer.toString(2));
            }
        });
    }

    @Override
    public void loadAfter(@NonNull LoadParams<String> params, final @NonNull LoadCallback<String, UserEntity> callback) {
        Log.i(TAG, "Loading page " + params.key );
        mNetworkState.postValue(NetworkState.LOADING);
        final AtomicInteger page = new AtomicInteger(0);
        try {
            page.set(Integer.parseInt(params.key));
        }catch (NumberFormatException e){
            e.printStackTrace();
        }
        Call<ArrayList<UserEntity>> callBack = mApi.getUsers(ORDER_TYPE, page.get());
        callBack.enqueue(new Callback<ArrayList<UserEntity>>() {
            @Override
            public void onResponse(Call<ArrayList<UserEntity>> call, Response<ArrayList<UserEntity>> response) {
                if (response.isSuccessful()) {
                    callback.onResult(response.body(),Integer.toString(page.get()+1));
                    mNetworkState.postValue(NetworkState.LOADED);
                    response.body().forEach(mUsersObservable::onNext);
                } else {
                    mNetworkState.postValue(new NetworkState(NetworkState.Status.FAILED, response.message()));
                    Log.e("API CALL", response.message());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<UserEntity>> call, Throwable t) {
                String errorMessage;
                if (t.getMessage() == null) {
                    errorMessage = "unknown error";
                } else {
                    errorMessage = t.getMessage();
                }
                mNetworkState.postValue(new NetworkState(NetworkState.Status.FAILED, errorMessage));
                callback.onResult(new ArrayList<>(),Integer.toString(page.get()));
            }
        });
    }


    @Override
    public void loadBefore(@NonNull LoadParams<String> params, @NonNull LoadCallback<String, UserEntity> callback) {

    }
}
