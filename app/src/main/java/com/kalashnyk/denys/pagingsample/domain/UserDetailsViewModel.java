package com.kalashnyk.denys.pagingsample.domain;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.kalashnyk.denys.pagingsample.repository.database.entities.UserEntity;

public class UserDetailsViewModel extends ViewModel {
    final private MutableLiveData user;

    public UserDetailsViewModel() {
        user = new MutableLiveData<UserEntity>();
    }

    public MutableLiveData<UserEntity> getUser() {
        return user;
    }
}