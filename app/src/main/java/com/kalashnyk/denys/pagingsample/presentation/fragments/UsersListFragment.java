package com.kalashnyk.denys.pagingsample.presentation.fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.paging.PagedList;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.kalashnyk.denys.pagingsample.R;
import com.kalashnyk.denys.pagingsample.repository.database.entities.UserEntity;
import com.kalashnyk.denys.pagingsample.presentation.adapters.UsersPagedListAdapter;
import com.kalashnyk.denys.pagingsample.presentation.listeners.ItemClickListener;
import com.kalashnyk.denys.pagingsample.domain.UsersListViewModel;

import java.util.List;

public class UsersListFragment extends Fragment implements ItemClickListener {

    protected UsersListViewModel mViewModel;
    //private UserDetailsViewModel mDetailsViewModel;
    UsersPagedListAdapter mPageListAdapter;
    protected RecyclerView mRecyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_users, container, false);
        mRecyclerView = view.findViewById(R.id.rv_users);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mViewModel = ViewModelProviders.of(getActivity()).get(UsersListViewModel.class);
        observersRegisters();
        return view;
    }

    private void observersRegisters() {
        mPageListAdapter = new UsersPagedListAdapter(this);
        Observer<PagedList<UserEntity>> observer = userEntities -> mPageListAdapter.submitList(userEntities);
        mViewModel.getUsers().observeForever(observer);
        mViewModel.getNetworkState().observeForever(networkState -> mPageListAdapter.setNetworkState(networkState));
        mRecyclerView.setAdapter(mPageListAdapter);
    }

    @Override
    public void OnItemClick(UserEntity user) {
        mViewModel.updateUserLike(user);
        mPageListAdapter.notifyItemChanged(user.getId()-1);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_refresh:
                mViewModel.getUsers().removeObservers(this);
                observersRegisters();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mViewModel.getUsers().removeObservers(this);
    }
}