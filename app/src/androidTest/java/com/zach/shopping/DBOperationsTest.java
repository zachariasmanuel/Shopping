package com.zach.shopping;

import android.support.test.filters.MediumTest;
import android.support.test.runner.AndroidJUnit4;

import com.zach.shopping.data.Repository;
import com.zach.shopping.data.db.Cart;
import com.zach.shopping.di.AppModule;
import com.zach.shopping.di.UtilsModule;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Created by zac on 11-May-2019
 */

@RunWith(AndroidJUnit4.class)
@MediumTest
public class DBOperationsTest {

    private static final String TAG = "DBOperationsTest";

    TestComponent testComponent;

    @Inject
    Repository repository;

    @Before
    public void setUp() {
        testComponent = DaggerTestComponent.builder().appModule(new AppModule(getInstrumentation().getTargetContext().getApplicationContext())).utilsModule(new UtilsModule()).build();
        testComponent.inject(this);
    }

    @Test
    public void testCartAddition() {

        final CompositeDisposable disposables = new CompositeDisposable();
        Cart cart = new Cart();
        cart.name = "Iphone";
        cart.uid = getRandomIntInclusive(2, 100000);
        cart.description = "Lorem";
        cart.rating = "4";
        cart.imageURL = "www.google.com";
        cart.price = "10000";

        disposables.add(repository.addToCart(cart)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(

                ));

        disposables.add(repository.getCartItems()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(data -> {
                    if (data.size() > 0)
                        assertTrue(true);
                    else
                        fail();
                })
        );
    }

    @Test
    public void testMyOrderAddition() {

        final CompositeDisposable disposables = new CompositeDisposable();
        Cart cart = new Cart();
        cart.name = "Iphone";
        cart.uid = getRandomIntInclusive(2, 100000);
        cart.description = "Lorem";
        cart.rating = "4";
        cart.imageURL = "www.google.com";
        cart.price = "10000";

        disposables.add(repository.addToCart(cart)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(

                ));

        disposables.add(repository.getCartItems()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(data -> {
                    if (data.size() > 0)
                        assertTrue(true);
                    else
                        fail();
                })
        );
    }

    private int getRandomIntInclusive(int min, int max) {
        min = (int) Math.ceil(min);
        max = (int) Math.floor(max);
        return (int) Math.floor(Math.random() * (max - min + 1)) + min;
    }
}

