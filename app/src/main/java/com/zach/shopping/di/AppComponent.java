package com.zach.shopping.di;

import com.zach.shopping.ui.CartFragment;
import com.zach.shopping.ui.MyOrderFragment;
import com.zach.shopping.ui.ProductDetailsFragment;
import com.zach.shopping.ui.ProductListFragment;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Component class for Dagger - Dependency Injection
 * Created by zac on 09-May-2019
 */

@Component(modules = {AppModule.class, UtilsModule.class})
@Singleton
public interface AppComponent {
    void doInjection(ProductListFragment productListFragment);
    void doInjection(CartFragment cartFragment);
    void doInjection(ProductDetailsFragment productDetailsFragment);
    void doInjection(MyOrderFragment myOrderFragment);
}
