package com.nomadconsultants.urbandictionarychallenge

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DisplayDefinitionsFragmentTest {
    @Test
    fun testEventFragment() {
        ActivityScenario.launch(MainActivity::class.java)
        onView(withId(R.id.sortByButton)).check(matches(withText(R.string.sort_by_down)))
    }
}