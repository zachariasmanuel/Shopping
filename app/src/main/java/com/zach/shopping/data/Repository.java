package com.zach.shopping.data;

import com.google.gson.JsonElement;
import com.zach.shopping.data.db.AppDatabase;
import com.zach.shopping.data.db.Product;

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

    public Maybe<List<Product>> fetchProductsFromDB(){
        return appDatabase.productDao().getAll();
    }

    public Observable<List<Long>> addToCart(){
        System.out.println("Reached here");
        Product product = new Product();
        product.name = "Moto";
        product.price = "100USD";
        product.imageURL = "www.google.com";
        product.rating = "3";
        product.status = "cart";
        return Observable.fromCallable(() -> appDatabase.productDao().insertAll(product));

    }
}
