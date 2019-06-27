package com.kalashnyk.denys.pagingsample.domain;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.paging.PagedList;
import android.support.annotation.NonNull;

import com.kalashnyk.denys.pagingsample.repository.AppRepository;
import com.kalashnyk.denys.pagingsample.repository.database.entities.UserEntity;
import com.kalashnyk.denys.pagingsample.repository.database.entities.NetworkState;

public class UsersListViewModel extends AndroidViewModel {
    private AppRepository mRepository;

    public UsersListViewModel(@NonNull Application application) {
        super(application);
        mRepository = AppRepository.getInstance(application);
    }
    public LiveData<PagedList<UserEntity>> getUsers() {
        return mRepository.getUsers();
    }

    public LiveData<PagedList<UserEntity>> getBugUsers() { return mRepository.getUsers(); }

    public void updateUserLike(UserEntity user) {
        user.setLike(!user.getLike());
        mRepository.updateUser(user);
    }

    public LiveData<NetworkState> getNetworkState() {
        return mRepository.getNetworkState();
    }

}
