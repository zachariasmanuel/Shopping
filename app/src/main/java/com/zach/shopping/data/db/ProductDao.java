package com.zach.shopping.data.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import io.reactivex.Maybe;

/**
 * Created by zac on 09-May-2019
 */
@Dao
public interface ProductDao {

    @Query("SELECT * FROM cart")
    Maybe<List<Cart>> getCartItems();

    @Insert
    List<Long> addToCart(Cart... carts);

    @Delete
    void delete(Cart cart);
}
