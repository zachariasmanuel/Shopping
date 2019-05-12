package com.zach.shopping;

import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.zach.shopping.ui.ShoppingActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * UI Test class for testing the UI flow
 * Created by zac on 12-May-2019
 */
@RunWith(AndroidJUnit4.class)
public class ShoppingUITest {

    @Rule
    public ActivityTestRule<ShoppingActivity> shoppingActivityActivityTestRule = new ActivityTestRule<>(ShoppingActivity.class);

    @Test
    public void productListRecyclerViewClickTest() throws Exception {
        Thread.sleep(5000);
        onView(withId(R.id.product_list_recycler_view))
                .perform(
                        RecyclerViewActions.actionOnItemAtPosition(4, click())
                );

        onView(withId(R.id.add_to_cart_button))
                .check(matches(isDisplayed()));
    }

    @Test
    public void addToCartClickTest() throws Exception {
        Thread.sleep(5000);
        onView(withId(R.id.product_list_recycler_view))
                .perform(
                        RecyclerViewActions.actionOnItemAtPosition(4, click())
                );
        try {
            onView(withText("ADD TO CART")).check(matches(isDisplayed()));
            onView(withText("ADD TO CART")).perform(click());
            onView(withText("GO TO CART")).check(matches(isDisplayed()));
        } catch (NoMatchingViewException e) {
            onView(withText("GO TO CART")).check(matches(isDisplayed()));
            onView(withText("GO TO CART")).perform(click());
            onView(withId(R.id.cart_list_recycler_view)).check(matches(isDisplayed()));
        }
    }

    @Test
    public void goToCartClickTest() throws Exception {
        Thread.sleep(5000);
        onView(withId(R.id.product_list_recycler_view))
                .perform(
                        RecyclerViewActions.actionOnItemAtPosition(4, click())
                );
        try {
            onView(withText("GO TO CART")).check(matches(isDisplayed()));
            onView(withText("GO TO CART")).perform(click());
            onView(withId(R.id.cart_list_recycler_view)).check(matches(isDisplayed()));
        } catch (NoMatchingViewException e) {
            onView(withText("ADD TO CART")).check(matches(isDisplayed()));
            onView(withText("ADD TO CART")).perform(click());
            onView(withText("GO TO CART")).check(matches(isDisplayed()));
        }
    }

    @Test
    public void checkOutClickTest() throws Exception {
        Thread.sleep(5000);
        onView(withId(R.id.product_list_recycler_view))
                .perform(
                        RecyclerViewActions.actionOnItemAtPosition(4, click())
                );

        onView(withText("GO TO CART")).check(matches(isDisplayed()));
        onView(withText("GO TO CART")).perform(click());
        onView(withText("CHECKOUT")).check(matches(isDisplayed()));
        onView(withText("CHECKOUT")).perform(click());
        onView(withId(R.id.my_order_recycler_view)).check(matches(isDisplayed()));
    }
}
