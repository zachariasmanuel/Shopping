package com.zach.shopping;

import android.app.Application;
import android.content.Context;

import com.zach.shopping.di.AppComponent;
import com.zach.shopping.di.AppModule;
import com.zach.shopping.di.DaggerAppComponent;
import com.zach.shopping.di.UtilsModule;

/**
 * Created by zac on 09-May-2019
 */
public class MyApplication extends Application {
    AppComponent appComponent;
    Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        appComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).utilsModule(new UtilsModule()).build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
    }
}
