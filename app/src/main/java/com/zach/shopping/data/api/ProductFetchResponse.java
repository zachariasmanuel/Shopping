package com.zach.shopping.data.api;

import com.google.gson.JsonElement;

import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;

import static com.zach.shopping.data.api.Status.ERROR;
import static com.zach.shopping.data.api.Status.LOADING;
import static com.zach.shopping.data.api.Status.SUCCESS;

/**
 * Created by zac on 09-May-2019
 * Response for the product fetch api call
 */
public class ProductFetchResponse {

    public final Status status;

    @Nullable
    public final JsonElement data;

    @Nullable
    public final Throwable error;

    private ProductFetchResponse(Status status, @Nullable JsonElement data, @Nullable Throwable error) {
        this.status = status;
        this.data = data;
        this.error = error;
    }

    public static ProductFetchResponse loading() {
        return new ProductFetchResponse(LOADING, null, null);
    }

    public static ProductFetchResponse success(@NonNull JsonElement data) {
        return new ProductFetchResponse(SUCCESS, data, null);
    }

    public static ProductFetchResponse error(@NonNull Throwable error) {
        return new ProductFetchResponse(ERROR, null, error);
    }

}
