package com.zach.shopping.viewmodels;

import android.arch.lifecycle.ViewModel;

import com.zach.shopping.data.Repository;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by zac on 10-May-2019
 */
public class CartViewModel extends ViewModel {

    private Repository repository;
    private final CompositeDisposable disposables = new CompositeDisposable();

    public CartViewModel(Repository repository) {
        this.repository = repository;
    }

//    public void getCartProducts(){
//        disposables.add(repository.fetchProductsFromDB()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(
//                        products -> System.out.println("Name - "+products.get(0).name)
//                ));
//    }

}
