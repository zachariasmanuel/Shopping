package com.zach.shopping.data.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Room entity class - MyOrder table
 * Created by zac on 10-May-2019
 */
@Entity
public class MyOrder {

    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "product_id")
    public int productId;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "price")
    public String price;

    @ColumnInfo(name = "image_url")
    public String imageURL;

    @ColumnInfo(name = "rating")
    public String rating;

    @ColumnInfo(name = "description")
    public String description;
}
