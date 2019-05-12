package com.zach.shopping;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.SmallTest;
import android.support.test.runner.AndroidJUnit4;

import com.zach.shopping.data.Repository;
import com.zach.shopping.di.AppModule;
import com.zach.shopping.di.UtilsModule;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

/**
 * Created by zac on 11-May-2019
 */

@RunWith(AndroidJUnit4.class)
@SmallTest
public class ApiTest {

    @Inject
    Repository repository;

    @Before
    public void setUp() {
        Context appContext = InstrumentationRegistry.getTargetContext();
        TestComponent testComponent = DaggerTestComponent.builder().appModule(new AppModule(appContext)).utilsModule(new UtilsModule()).build();
        testComponent.inject(this);
    }

    @Test
    public void testProductFetch() {

        repository.fetchProduct()
                .test().assertValue(jsonElement -> jsonElement.getAsJsonObject().getAsJsonArray("products").size() > 0);
    }

}

