package com.zach.shopping.viewmodels;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.zach.shopping.data.Repository;
import com.zach.shopping.data.db.Cart;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * View Model class for Product Details view
 * Created by zac on 10-May-2019
 */
public class ProductDetailsViewModel extends ViewModel {

    private Repository repository;
    private final CompositeDisposable disposables = new CompositeDisposable();
    private final MutableLiveData<List<Cart>> cartItemsLiveData = new MutableLiveData<>();

    ProductDetailsViewModel(Repository repository) {
        this.repository = repository;
    }

    public MutableLiveData<List<Cart>> cartItemsResponse() {
        return cartItemsLiveData;
    }


    public void getCartProducts() {
        disposables.add(repository.getCartItems()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(cartItemsLiveData::setValue)
        );
    }

    public void addToCart(Cart cart) {
        disposables.add(repository.addToCart(cart)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        productsIds -> System.out.println(productsIds.size())
                ));
    }

}