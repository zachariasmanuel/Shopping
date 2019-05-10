package com.zach.shopping.data;

import com.google.gson.JsonElement;
import com.zach.shopping.data.db.AppDatabase;
import com.zach.shopping.data.db.Cart;

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

    public Maybe<List<Cart>> getCartItems(){
        return appDatabase.productDao().getCartItems();
    }

    public Observable<List<Long>> addToCart(Cart cart){
        return Observable.fromCallable(() -> appDatabase.productDao().addToCart(cart));
    }
}
