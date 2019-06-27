package com.kalashnyk.denys.pagingsample.presentation.listeners;



import com.kalashnyk.denys.pagingsample.repository.database.entities.UserEntity;


public interface ItemClickListener {
    void OnItemClick(UserEntity user);
}
