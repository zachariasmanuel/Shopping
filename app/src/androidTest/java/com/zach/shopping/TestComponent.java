package com.zach.shopping;

import com.zach.shopping.di.AppComponent;
import com.zach.shopping.di.AppModule;
import com.zach.shopping.di.UtilsModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Test component class for Dagger dependency injection
 * Created by zac on 11-May-2019
 */
@Singleton
@Component(modules = {AppModule.class, UtilsModule.class})
public interface TestComponent extends AppComponent {
    void inject(ApiTest test);
    void inject(DBOperationsTest test);
}
