package com.zach.shopping.data;

import com.google.gson.JsonElement;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by zac on 09-May-2019
 */
public interface ApiCallInterface {

    @GET(Urls.FETCH_PRODUCTS)
    Observable<JsonElement> fetchProduct();
}
