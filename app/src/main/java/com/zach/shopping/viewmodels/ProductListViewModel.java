package com.zach.shopping.viewmodels;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.zach.shopping.data.api.ProductFetchResponse;
import com.zach.shopping.data.Repository;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * View Model class for Product Details view
 * Created by zac on 09-May-2019
 */
public class ProductListViewModel extends ViewModel {

    private Repository repository;
    private final CompositeDisposable disposables = new CompositeDisposable();
    private final MutableLiveData<ProductFetchResponse> responseLiveData = new MutableLiveData<>();

    ProductListViewModel(Repository repository) {
        this.repository = repository;
    }

    public MutableLiveData<ProductFetchResponse> productFetchResponse() {
        return responseLiveData;
    }

    public void fetchProductsFromApi() {

        disposables.add(repository.fetchProduct()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe((d) -> responseLiveData.setValue(ProductFetchResponse.loading()))
                .subscribe(
                        result -> responseLiveData.setValue(ProductFetchResponse.success(result)),
                        throwable -> responseLiveData.setValue(ProductFetchResponse.error(throwable))
                ));

    }

    @Override
    protected void onCleared() {
        disposables.clear();
    }
}
