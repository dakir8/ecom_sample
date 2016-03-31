package sample.daniel.ecomsample.app;

import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.SmallTest;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.Assert.assertTrue;

/**
 * Created by Daniel on 16/3/24.
 */
@RunWith(AndroidJUnit4.class)
@SmallTest
public class SimpleTest {
    private String mStringToBetyped;

    @Rule
    public ActivityTestRule<ProductListActivity> mActivityRule = new ActivityTestRule<>(
            ProductListActivity.class);

    @Before
    public void initValidString() {
        // Specify a valid string.
        mStringToBetyped = "Hello World!";
    }

    @Test
    public void changeText_sameActivity() {
        assertTrue(true);
    }
}
