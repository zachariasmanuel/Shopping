package com.zach.shopping.viewmodels;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.zach.shopping.data.Repository;

import javax.inject.Inject;

/**
 * View Model Factory to create My Order View Model
 * Created by zac on 10-May-2019
 */
public class MyOrderViewModelFactory implements ViewModelProvider.Factory{

    private Repository repository;

    @Inject
    public MyOrderViewModelFactory(Repository repository) {
        this.repository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MyOrderViewModel.class)) {
            return (T) new MyOrderViewModel(repository);
        }

        throw new IllegalArgumentException("Unknown class name");
    }
}
