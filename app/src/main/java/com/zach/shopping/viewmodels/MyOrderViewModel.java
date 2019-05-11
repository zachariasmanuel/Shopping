package com.zach.shopping.viewmodels;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.zach.shopping.data.Repository;
import com.zach.shopping.data.db.MyOrder;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * View Model class for My Order View
 * Created by zac on 10-May-2019
 */
public class MyOrderViewModel extends ViewModel {

    private Repository repository;
    private final CompositeDisposable disposables = new CompositeDisposable();
    private final MutableLiveData<List<MyOrder>> orderItemLiveData = new MutableLiveData<>();

    MyOrderViewModel(Repository repository) {
        this.repository = repository;
    }
    public MutableLiveData<List<MyOrder>> getOrderItemsResponse() {
        return orderItemLiveData;
    }

    public void getOrders() {
        disposables.add(repository.getOrders()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(orderItemLiveData::setValue)
        );
    }

}
