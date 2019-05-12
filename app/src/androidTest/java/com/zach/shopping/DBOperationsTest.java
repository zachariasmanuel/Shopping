package com.zach.shopping;

import android.support.test.filters.MediumTest;
import android.support.test.runner.AndroidJUnit4;

import com.zach.shopping.data.Repository;
import com.zach.shopping.data.db.Cart;
import com.zach.shopping.data.db.MyOrder;
import com.zach.shopping.di.AppModule;
import com.zach.shopping.di.UtilsModule;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Test class for testing the basic database operations
 * Created by zac on 11-May-2019
 */

@RunWith(AndroidJUnit4.class)
@MediumTest
public class DBOperationsTest {

    @Inject
    Repository repository;

    @Before
    public void setUp() {
        TestComponent testComponent = DaggerTestComponent.builder().appModule(new AppModule(getInstrumentation().getTargetContext().getApplicationContext())).utilsModule(new UtilsModule()).build();
        testComponent.inject(this);
    }

    @Test
    public void testCartAddition() {

        final CompositeDisposable disposables = new CompositeDisposable();
        Cart cart = new Cart();
        cart.name = "OnePlus 6";
        cart.uid = getRandomIntInclusive(2, 10000);
        cart.description = "Lorem";
        cart.rating = "4.5";
        cart.imageURL = "https://i.gadgets360cdn.com/products/large/1526490365_635_oneplus_6.jpg";
        cart.price = "40000";

        disposables.add(repository.addToCart(cart)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(reply -> disposables.add(repository.getCartItems()
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(data -> {
                                    if (data.size() > 0)
                                        assertTrue(true);
                                    else
                                        fail();
                                    testCartRemoval();
                                })
                        )
                ));
    }


    public void testCartRemoval() {
        final CompositeDisposable disposables = new CompositeDisposable();
        Cart cart = new Cart();
        cart.name = "OnePlus 6";
        cart.uid = getRandomIntInclusive(2, 10000);
        cart.description = "Lorem";
        cart.rating = "4.5";
        cart.imageURL = "https://i.gadgets360cdn.com/products/large/1526490365_635_oneplus_6.jpg";
        cart.price = "40000";

        disposables.add(repository.addToCart(cart)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(reply -> disposables.add(repository.removeAllFromCart()
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(success -> {
                                    disposables.add(repository.getCartItems()
                                            .subscribeOn(Schedulers.io())
                                            .observeOn(AndroidSchedulers.mainThread())
                                            .subscribe(data -> {
                                                if (data.size() == 0)
                                                    assertTrue(true);
                                                else
                                                    fail();
                                            })
                                    );
                                })
                        )
                ));
    }

    @Test
    public void testMyOrderAddition() {

        final CompositeDisposable disposables = new CompositeDisposable();
        List<MyOrder> myOrderList = new ArrayList<>();
        MyOrder order = new MyOrder();
        order.name = "OnePlus 6";
        order.productId = getRandomIntInclusive(2, 100000);
        order.description = "Lorem";
        order.rating =  "4.5";
        order.imageURL = "https://i.gadgets360cdn.com/products/large/1526490365_635_oneplus_6.jpg";
        order.price = "40000";
        myOrderList.add(order);

        disposables.add(repository.addToOrder(myOrderList)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(reply -> {
                    disposables.add(repository.getOrders()
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(data -> {
                                if (data.size() > 0)
                                    assertTrue(true);
                                else
                                    fail();
                            })
                    );
                }));
    }

    private int getRandomIntInclusive(int min, int max) {
        min = (int) Math.ceil(min);
        max = (int) Math.floor(max);
        return (int) Math.floor(Math.random() * (max - min + 1)) + min;
    }
}

