package com.zach.shopping.viewmodels;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.zach.shopping.data.Repository;

import javax.inject.Inject;

/**
 * Created by zac on 09-May-2019
 */
public class ViewModelFactory implements ViewModelProvider.Factory {

    private Repository repository;

    @Inject
    public ViewModelFactory(Repository repository) {
        this.repository = repository;
    }


    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ShoppingViewModel.class)) {
            return (T) new ShoppingViewModel(repository);
        }

        throw new IllegalArgumentException("Unknown class name");
    }
}
