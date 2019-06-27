package com.kalashnyk.denys.pagingsample.presentation.item;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.kalashnyk.denys.pagingsample.databinding.UserItemBinding;
import com.kalashnyk.denys.pagingsample.repository.database.AppDatabase;
import com.kalashnyk.denys.pagingsample.repository.database.entities.UserEntity;
import com.kalashnyk.denys.pagingsample.R;
import com.kalashnyk.denys.pagingsample.presentation.listeners.ItemClickListener;


public class UserViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


    private UserEntity mUser;
    private ItemClickListener mItemClickListener;
    private UserItemBinding mBinding;
    private AppDatabase mDatabase;

    public UserViewHolder(UserItemBinding binding, ItemClickListener itemClickListener) {
        super(binding.getRoot());
        this.mBinding = binding;
        this.mItemClickListener = itemClickListener;
        this.mBinding.getRoot().setOnClickListener(this::onClick);
        this.mBinding.getRoot().findViewById(R.id.chk_item_like).setOnClickListener(this::onChkClick);
        this.mDatabase = AppDatabase.getInstance(mBinding.getRoot().getContext());
    }

    public void bindTo(UserEntity user) {
        this.mUser = user;
        mBinding.setUser(user);
    }

    @Override
    public void onClick(View view) {
        if (mItemClickListener != null) {
            mItemClickListener.OnItemClick(mUser);
        }
    }

    public void onChkClick(View v) {
        mUser.setLike(!mUser.getLike());
        mDatabase.mUserDao().updateUser(mUser);
    }

}
