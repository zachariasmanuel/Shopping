package com.zach.shopping.data.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

/**
 * Abstract Database class for Room DB
 * Created by zac on 09-May-2019
 */
@Database(entities = {Cart.class, MyOrder.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ProductDao productDao();
}

