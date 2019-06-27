package com.kalashnyk.denys.pagingsample.presentation.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kalashnyk.denys.pagingsample.R;
import com.kalashnyk.denys.pagingsample.databinding.FragmentDetailsBinding;
import com.kalashnyk.denys.pagingsample.domain.UserDetailsViewModel;


public class UserDetailsFragment extends Fragment {

    private UserDetailsViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentDetailsBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_details, container, false);
        viewModel = ViewModelProviders.of(getActivity()).get(UserDetailsViewModel.class);
        View view = binding.getRoot();
        viewModel.getUser().observeForever(userEntity -> binding.setUser(userEntity));
        return view;
    }

}
