package com.zach.shopping.data;

import com.google.gson.JsonElement;

import io.reactivex.Observable;

/**
 * Created by zac on 09-May-2019
 */
public class Repository {

    private ApiCallInterface apiCallInterface;

    public Repository(ApiCallInterface apiCallInterface) {
        this.apiCallInterface = apiCallInterface;
    }


    public Observable<JsonElement> fetchProduct() {
        return apiCallInterface.fetchProduct();
    }

}
