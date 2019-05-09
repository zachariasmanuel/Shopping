package com.zach.shopping.data.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by zac on 09-May-2019
 */

@Entity
public class Product {

    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "price")
    public String price;

    @ColumnInfo(name = "image_url")
    public String imageURL;

    @ColumnInfo(name = "rating")
    public String rating;

    @ColumnInfo(name = "status")
    public String status;
}
