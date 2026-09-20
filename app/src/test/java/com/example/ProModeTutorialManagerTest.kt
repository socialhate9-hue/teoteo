package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.home.ProModeTutorialManager
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ProModeTutorialManagerTest {

    private lateinit var context: Context

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
        ProModeTutorialManager.resetTutorial(context)
    }

    @Test
    fun shouldShowTutorial_initiallyTrue() {
        assertTrue(ProModeTutorialManager.shouldShowTutorial(context))
    }

    @Test
    fun markTutorialSeen_persistsAndUpdatesState() {
        ProModeTutorialManager.markTutorialSeen(context)
        assertFalse(ProModeTutorialManager.shouldShowTutorial(context))
    }

    @Test
    fun triggerTutorial_activatesTutorial() {
        ProModeTutorialManager.triggerTutorial()
        assertTrue(ProModeTutorialManager.isTutorialActive)
    }
}
