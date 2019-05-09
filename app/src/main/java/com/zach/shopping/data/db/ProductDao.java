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

    @Query("SELECT * FROM product")
    Maybe<List<Product>> getAll();

    @Insert
    List<Long> insertAll(Product... products);

    @Delete
    void delete(Product product);
}
