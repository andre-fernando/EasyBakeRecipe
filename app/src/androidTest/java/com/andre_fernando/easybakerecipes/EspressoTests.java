package com.andre_fernando.easybakerecipes;


import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.andre_fernando.easybakerecipes.activities.OverviewActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class EspressoTests {

    @Rule
    public ActivityTestRule<OverviewActivity> activityTestRule =
            new ActivityTestRule<>(OverviewActivity.class);
    
    @Test
    public void check_label_ingredients(){
        onView(withText(R.string.label_ingredients)).check(matches(isDisplayed()));
    }

    @Test
    public void check_label_steps(){
        onView(withText(R.string.label_Steps)).check(matches(isDisplayed()));
    }
}
