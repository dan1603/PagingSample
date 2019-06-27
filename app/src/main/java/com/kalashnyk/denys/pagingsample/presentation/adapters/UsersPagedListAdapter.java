package com.kalashnyk.denys.pagingsample.presentation.adapters;

import android.arch.paging.PagedListAdapter;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kalashnyk.denys.pagingsample.R;
import com.kalashnyk.denys.pagingsample.databinding.UserItemBinding;
import com.kalashnyk.denys.pagingsample.repository.database.entities.UserEntity;
import com.kalashnyk.denys.pagingsample.repository.database.entities.NetworkState;
import com.kalashnyk.denys.pagingsample.presentation.listeners.ItemClickListener;
import com.kalashnyk.denys.pagingsample.presentation.item.UserViewHolder;
import com.kalashnyk.denys.pagingsample.presentation.item.NetworkStateItemViewHolder;

public class UsersPagedListAdapter extends PagedListAdapter<UserEntity, RecyclerView.ViewHolder> {

    private NetworkState networkState;
    private ItemClickListener itemClickListener;

    public UsersPagedListAdapter(ItemClickListener itemClickListener) {
        super(UserEntity.DIFF_CALLBACK);
        this.itemClickListener = itemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        if (viewType == R.layout.user_item) {
            UserItemBinding itemBinding = UserItemBinding.inflate(layoutInflater, parent, false);
            UserViewHolder viewHolder = new UserViewHolder(itemBinding, itemClickListener);
            return viewHolder;
        }
        else if (viewType == R.layout.network_state_item) {
            View view = layoutInflater.inflate(R.layout.network_state_item, parent, false);
            return new NetworkStateItemViewHolder(view);
        }
        else {
            throw new IllegalArgumentException("unknown view type");
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case R.layout.user_item:
                ((UserViewHolder) holder).bindTo(getItem(position));
                break;
            case R.layout.network_state_item:
                ((NetworkStateItemViewHolder) holder).bindView(networkState);
                break;
        }
    }

    private boolean hasExtraRow() {
        if (networkState != null && networkState != NetworkState.LOADED) {
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (hasExtraRow() && position == getItemCount() - 1) {
            return R.layout.network_state_item;
        }
        else {
            return R.layout.user_item;
        }
    }

    public void setNetworkState(NetworkState newNetworkState) {
        NetworkState previousState = this.networkState;
        boolean previousExtraRow = hasExtraRow();
        this.networkState = newNetworkState;
        boolean newExtraRow = hasExtraRow();
        if (previousExtraRow != newExtraRow) {
            if (previousExtraRow) {
                notifyItemRemoved(getItemCount());
            }
            else {
                notifyItemInserted(getItemCount());
            }
        }
        else if (newExtraRow && previousState != newNetworkState) {
            notifyItemChanged(getItemCount() - 1);
        }
    }
}
