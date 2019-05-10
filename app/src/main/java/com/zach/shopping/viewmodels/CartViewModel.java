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
 * Created by zac on 10-May-2019
 */
public class CartViewModel extends ViewModel {

    private Repository repository;
    private final CompositeDisposable disposables = new CompositeDisposable();
    private final MutableLiveData<List<Cart>> cartItemsLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> deleteSuccessLiveData = new MutableLiveData<>();

    public CartViewModel(Repository repository) {
        this.repository = repository;
    }

    public MutableLiveData<List<Cart>> getCartItemsResponse() {
        return cartItemsLiveData;
    }

    public MutableLiveData<Boolean> getDeleteSuccessResponse() {
        return deleteSuccessLiveData;
    }

    public void getCartProducts() {
        disposables.add(repository.getCartItems()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(cartItemsLiveData::setValue)
        );
    }

    public void removeItemFromCart(Cart product) {

        disposables.add(repository.removeFromCart(product)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        deletedRow -> {
                            if (deletedRow > 0)
                                deleteSuccessLiveData.setValue(true);
                            else
                                deleteSuccessLiveData.setValue(false);
                        }
                ));
    }
}
