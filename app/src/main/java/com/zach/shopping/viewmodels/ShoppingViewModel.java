package com.zach.shopping.viewmodels;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.zach.shopping.data.ApiResponse;
import com.zach.shopping.data.Repository;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zac on 09-May-2019
 */
public class ShoppingViewModel extends ViewModel {

    private Repository repository;
    private final CompositeDisposable disposables = new CompositeDisposable();
    private final CompositeDisposable newdisposables = new CompositeDisposable();
    private final MutableLiveData<ApiResponse> responseLiveData = new MutableLiveData<>();


    public ShoppingViewModel(Repository repository) {
        this.repository = repository;
    }

    public MutableLiveData<ApiResponse> productFetchResponse() {
        return responseLiveData;
    }


    public void fetchProduct() {

        disposables.add(repository.fetchProduct()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe((d) -> responseLiveData.setValue(ApiResponse.loading()))
                .subscribe(
                        result -> responseLiveData.setValue(ApiResponse.success(result)),
                        throwable -> responseLiveData.setValue(ApiResponse.error(throwable))
                ));

    }

    public void fetchProductFromDB() {

        disposables.add(repository.fetchProductsFromDB()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                //.doOnSubscribe((d) -> responseLiveData.setValue(ApiResponse.loading()))
                .subscribe(
                        products -> System.out.println("Size - "+products.get(0).name)
                ));
    }

    public void addToCart() {

        disposables.add(repository.addToCart()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                //.doOnSubscribe((d) -> responseLiveData.setValue(ApiResponse.loading()))
                .subscribe(
                        products -> System.out.println(products.size())
                ));
    }

    @Override
    protected void onCleared() {
        disposables.clear();
    }
}
