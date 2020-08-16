package com.interview.kingpower.screen.photolist

import android.os.Build
import android.os.Looper.getMainLooper
import android.view.View
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import com.interview.kingpower.R
import com.interview.kingpower.model.PhotoRes
import com.interview.kingpower.model.PhotoResItem
import com.interview.kingpower.viewmodel.PhotoViewModel
import io.mockk.*
import io.mockk.impl.annotations.MockK
import org.hamcrest.Matcher
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.loadKoinModules
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows.shadowOf
import org.robolectric.annotation.Config
import org.robolectric.annotation.LooperMode


@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.P])
@LooperMode(LooperMode.Mode.PAUSED)
class PhotoListFragmentTest {

    @MockK
    private lateinit var photoViewModel: PhotoViewModel

    @MockK
    private lateinit var navController: NavController

    private val mockAlbumId = 1
    private val mockId = 2
    private val mockThumbnaiUrl = "www.mock.com"
    private val mockTitle = "title"
    private val mockUrl = "www.mock.com"

    private val photoItem = PhotoResItem(mockAlbumId, mockId, mockThumbnaiUrl, mockTitle, mockUrl)
    private val photoRes = PhotoRes().also {
        it.add(photoItem)
    }

    private val photoResponse = MutableLiveData<PhotoRes>()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        loadKoinModules(module { single(override = true) { photoViewModel } })

        coEvery { photoViewModel.getPhotoList() } just Runs
        photoResponse.value = photoRes
        every { photoViewModel.photoRes } returns photoResponse
        every { photoViewModel.showMessage } returns MutableLiveData()
        every { navController.navigate(any<NavDirections>()) } returns Unit
    }

    @Test
    fun testStartPhotoListFragmentThenShowRecycleView() {
        shadowOf(getMainLooper()).idle()
        launchFragmentInContainer<PhotoListFragment>().onFragment {
            Navigation.setViewNavController(it.requireView(), navController)
            onView(withId(R.id.recycleView)).check(matches(isDisplayed()))
            onView(withId(R.id.imgThumbnail))
                .check(matches(isEnabled()))
            onView(withId(R.id.tvTitle))
                .check(matches(isEnabled()))
        }
    }

    @Test
    fun testStartPhotoListFragmentWhenClickItemThenNavigateToFullScreenFragment() {
        shadowOf(getMainLooper()).idle()
        launchFragmentInContainer<PhotoListFragment>().onFragment {
            Navigation.setViewNavController(it.requireView(), navController)
            onView(withId(R.id.recycleView)).check(matches(isDisplayed()))
            onView(withId(R.id.recycleView)).perform(
                actionOnItemAtPosition<PhotoListAdapter.ViewHolder>(
                    0,
                    clickChildViewWithId(R.id.imgThumbnail)
                )
            )
            verify { navController.navigate(PhotoListFragmentDirections.navigateFullscreen(photoItem)) }
        }
    }

    private fun clickChildViewWithId(id: Int): ViewAction? {
        return object : ViewAction {
            override fun getDescription(): String {
                return "Click item"
            }

            override fun getConstraints(): Matcher<View?>? {
                return null
            }

            override fun perform(uiController: UiController?, view: View?) {
                val v: View = view!!.findViewById(id)
                v.performClick()
            }
        }
    }

    @After
    fun tearDown() {
        stopKoin()
    }
}
