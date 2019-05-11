package com.zach.shopping.data.api;

import com.google.gson.JsonElement;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by zac on 09-May-2019
 * Interface class for Retrofit services
 */
public interface ApiCallInterface {

    @GET(Urls.FETCH_PRODUCTS)
    Observable<JsonElement> fetchProduct();
}
