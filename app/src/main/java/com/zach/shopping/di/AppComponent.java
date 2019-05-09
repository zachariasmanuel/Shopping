package com.zach.shopping.di;

import com.zach.shopping.CartFragment;
import com.zach.shopping.ProductListFragment;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by zac on 09-May-2019
 */

@Component(modules = {AppModule.class, UtilsModule.class})
@Singleton
public interface AppComponent {
    void doInjection(ProductListFragment productListFragment);
    void doInjection(CartFragment cartFragment);
}
