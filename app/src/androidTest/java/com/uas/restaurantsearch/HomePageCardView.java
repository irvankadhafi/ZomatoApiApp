package com.uas.restaurantsearch;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class HomePageCardView {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void homePageCardView() throws InterruptedException {
        Thread.sleep(5000);
        //select the second recycler view
        int i = 1;
        //then click on the first item of it
                int itemPosition = 0;
        onView(result(withId(R.id.restaurant_list), i)).perform(RecyclerViewActions.actionOnItemAtPosition(itemPosition, click()));
        Thread.sleep(5000);
    }

    public static <T> Matcher<T> result(final Matcher<T> matcher, final int i) {
        return new BaseMatcher<T>() {
            private int resultIndex = -1;
            @Override
            public boolean matches(final Object item) {
                if (matcher.matches(item)) {
                    resultIndex++;
                    return resultIndex == i;
                }
                return false;
            }

            @Override
            public void describeTo(final Description description) {
            }
        };
    }
}
