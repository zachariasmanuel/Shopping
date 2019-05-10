package com.zach.shopping.data;

import com.google.gson.JsonElement;
import com.zach.shopping.data.db.AppDatabase;
import com.zach.shopping.data.db.Cart;
import com.zach.shopping.data.db.MyOrder;

import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.Observable;

/**
 * Created by zac on 09-May-2019
 */
public class Repository {

    private ApiCallInterface apiCallInterface;
    private AppDatabase appDatabase;

    public Repository(ApiCallInterface apiCallInterface, AppDatabase appDatabase) {
        this.apiCallInterface = apiCallInterface;
        this.appDatabase = appDatabase;
    }

    public Observable<JsonElement> fetchProduct() {
        return apiCallInterface.fetchProduct();
    }

    public Maybe<List<Cart>> getCartItems() {
        return appDatabase.productDao().getCartItems();
    }

    public Observable<List<Long>> addToCart(Cart cart) {
        return Observable.fromCallable(() -> appDatabase.productDao().addToCart(cart));
    }

    public Observable<Integer> removeFromCart(Cart product) {
        return Observable.fromCallable(() -> appDatabase.productDao().removeFromCart(product));
    }

    public Observable<Boolean> removeAllFromCart() {
        return Observable.fromCallable(() -> {
            appDatabase.productDao().removeAllFromCart();
            return true;
        });
    }

    public Observable<List<Long>> addToOrder(List<MyOrder> myOrders) {
        return Observable.fromCallable(() -> appDatabase.productDao().addToOrder(myOrders));
    }

    public Maybe<List<MyOrder>> getOrders() {
        return appDatabase.productDao().getOrders();
    }
}
