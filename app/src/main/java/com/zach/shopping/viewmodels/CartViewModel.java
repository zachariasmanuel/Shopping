package com.zach.shopping.viewmodels;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.zach.shopping.data.Repository;
import com.zach.shopping.data.db.Cart;
import com.zach.shopping.data.db.MyOrder;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * View Model class for Cart View
 * Created by zac on 10-May-2019
 */
public class CartViewModel extends ViewModel {

    private static final String TAG = "CartViewModel";
    private Repository repository;
    private final CompositeDisposable disposables = new CompositeDisposable();
    private final MutableLiveData<List<Cart>> cartItemsLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> deleteSuccessLiveData = new MutableLiveData<>();

    CartViewModel(Repository repository) {
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

    public void moveAllToMyOrder(List<Cart> cartData) {

        List<MyOrder> myOrders = new ArrayList<>();

        disposables.add(repository.removeAllFromCart()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        success -> Log.d(TAG, "Removed from cart - " + success)
                ));

        for (Cart cart : cartData) {
            MyOrder myOrder = new MyOrder();
            myOrder.productId = cart.uid;
            myOrder.name = cart.name;
            myOrder.price = cart.price;
            myOrder.imageURL = cart.imageURL;
            myOrder.description = cart.description;
            myOrders.add(myOrder);
        }

        disposables.add(repository.addToOrder(myOrders)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        count -> Log.d(TAG, "Added to Order - " + count)
                ));
    }

}

