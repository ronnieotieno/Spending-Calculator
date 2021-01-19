package dev.ronnie.spendingcalculator.data

import android.os.Bundle
import android.os.Parcelable
import android.view.View
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import dev.ronnie.spendingcalculator.R
import dev.ronnie.spendingcalculator.domain.Message
import dev.ronnie.spendingcalculator.presentation.ui.MainActivity
import org.hamcrest.CoreMatchers
import org.hamcrest.Matcher
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.internal.matchers.Matches
import java.util.*
import kotlin.collections.ArrayList

@RunWith(AndroidJUnit4::class)
class FragmentListTest {

    @Rule
    @JvmField
    var activityTestRule = ActivityTestRule(MainActivity::class.java)

    @Before
    fun jumpToFragment() {

        activityTestRule.activity.apply {
            val creditSmsList = ArrayList<Message>()

            creditSmsList.add(Message("0705745858", "Test Message 1", Date(), "1"))
            creditSmsList.add(Message("0707745858", "Test Message 2", Date(), "2"))
            creditSmsList.add(Message("0705745858", "Test Message 3", Date(), "3"))
            creditSmsList.add(Message("0705745858", "Test Message 4", Date(), "4"))


            // Espresso.onView(withId(R.id.fragmentPieChart)).perform(ViewActions.click())
            runOnUiThread {
                val bundle = Bundle()
                bundle.putParcelableArrayList(
                    "list",
                    creditSmsList as java.util.ArrayList<out Parcelable>
                )
                bundle.putString("type", "Credit Messages")

                findNavController(R.id.nav_host).navigate(R.id.toFragmentList, bundle)

            }

        }


    }

    @Test
    fun is_recyclerview_showing() {

        Thread.sleep(500)
        onView(withId(R.id.recyclerview))
            .check(matches(isDisplayed()))
    }


    @Test
    fun test_open_dialog_onclick() {
        onView(withId(R.id.recyclerview)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                MyViewAction.clickChildViewWithId(R.id.addTag)

            )
        );

        onView(withId(R.id.number)).check(matches(isDisplayed()))
    }


    object MyViewAction {
        fun clickChildViewWithId(id: Int): ViewAction {
            return object : ViewAction {
                override fun getConstraints(): Matcher<View>? {
                    return null
                }

                override fun getDescription(): String {
                    return "Click on a child view with specified id."
                }

                override fun perform(uiController: UiController, view: View) {
                    val v = view.findViewById<View>(id)
                    v.performClick()
                }
            }
        }
    }


}