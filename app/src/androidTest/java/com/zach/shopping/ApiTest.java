package com.zach.shopping;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.SmallTest;
import android.support.test.runner.AndroidJUnit4;

import com.google.gson.JsonElement;
import com.zach.shopping.data.Repository;
import com.zach.shopping.di.AppModule;
import com.zach.shopping.di.UtilsModule;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import io.reactivex.observers.TestObserver;

import static android.support.test.InstrumentationRegistry.getInstrumentation;

/**
 * Created by zac on 11-May-2019
 */

@RunWith(AndroidJUnit4.class)
@SmallTest
public class ApiTest {

    private static final String TAG = "ApiTest";

    TestComponent testComponent;
    Context appContext = InstrumentationRegistry.getTargetContext();

    @Inject
    Repository repository;

    @Before
    public void setUp() {
        testComponent = DaggerTestComponent.builder().appModule(new AppModule(getInstrumentation().getTargetContext().getApplicationContext())).utilsModule(new UtilsModule()).build();
        testComponent.inject(this);
    }

    @Test
    public void testProductFetch() {

        TestObserver<JsonElement> result = repository.fetchProduct()
                .test().assertValue(jsonElement -> jsonElement.getAsJsonObject().getAsJsonArray("products").size() > 0);
    }

}

