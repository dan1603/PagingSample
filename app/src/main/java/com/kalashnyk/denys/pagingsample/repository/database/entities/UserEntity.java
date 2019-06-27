package com.kalashnyk.denys.pagingsample.repository.database.entities;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "users")
public class UserEntity extends BaseObservable {

    @PrimaryKey()
    @ColumnInfo(name = "id")
    @SerializedName(value="id")
    private Integer mId;
    @ColumnInfo(name = "name")
    @SerializedName(value="name")
    private String mName;
    @ColumnInfo(name = "surname")
    @SerializedName(value="surname")
    private String mSurname;
    @ColumnInfo(name = "fathername")
    @SerializedName(value="fathername")
    private String mFathername;
    @ColumnInfo(name = "isliked")
    @SerializedName(value="isliked")
    private Boolean mLike;

    public static DiffUtil.ItemCallback<UserEntity> DIFF_CALLBACK = new DiffUtil.ItemCallback<UserEntity>() {
        @Override
        public boolean areItemsTheSame(@NonNull UserEntity oldItem, @NonNull UserEntity newItem) {
            return oldItem.getId().equals(newItem.getId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull UserEntity oldItem, @NonNull UserEntity newItem) {
            return oldItem.getId().equals(newItem.getId());
        }
    };

    @Bindable
    public Integer getId() {
        return mId;
    }

    public void setId(Integer mId) {
        this.mId = mId;
    }

    @Bindable
    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    @Bindable
    public String getSurname() {
        return mSurname;
    }

    public void setSurname(String mSurname) {
        this.mSurname = mSurname;
    }

    @Bindable
    public String getFathername() {
        return mFathername;
    }

    public void setFathername(String mFathername) {
        this.mFathername = mFathername;
    }

    @Bindable
    public Boolean getLike() {
        return mLike;
    }

    public void setLike(Boolean mLike) {
        this.mLike = mLike;
    }
}
