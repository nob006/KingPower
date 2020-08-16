package com.interview.kingpower.viewmodel

import com.interview.kingpower.R
import com.interview.kingpower.ViewModelTest
import com.interview.kingpower.model.PhotoRes
import com.interview.kingpower.model.PhotoResItem
import com.interview.kingpower.repository.PhotoListRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.setMain
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class PhotoViewModelTest : ViewModelTest() {

    private val photoListRepository = mockk<PhotoListRepository>()
    private lateinit var viewModel: PhotoViewModel

    private val mockAlbumId = 1
    private val mockId = 2
    private val mockThumbnaiUrl = "www.mock.com"
    private val mockTitle = "title"
    private val mockUrl = "www.mock.com"

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        Dispatchers.setMain(dispatcher)

        val photoItem = PhotoResItem(mockAlbumId, mockId, mockThumbnaiUrl, mockTitle, mockUrl)
        val photoRes = PhotoRes().also {
            it.add(photoItem)
        }
        coEvery { photoListRepository.getPhotoList() } returns Response.success(photoRes)

        viewModel = PhotoViewModel(photoListRepository)
    }

    @Test
    fun testGetPhotoListThenSuccess() {

        viewModel.getPhotoList()

        coVerify { photoListRepository.getPhotoList() }
        val res = viewModel.photoRes.value
        assert(res!![0].albumId == mockAlbumId)
        assert(res!![0].id == mockId)
        assert(res!![0].thumbnailUrl == mockThumbnaiUrl)
        assert(res!![0].title == mockTitle)
        assert(res!![0].url == mockUrl)
    }

    @Test
    fun testGetPhotoListThenFailed() {
        coEvery { photoListRepository.getPhotoList() } returns Response.error(
            401, ResponseBody.create(
                MediaType.parse("error"), "error"
            )
        )

        viewModel.getPhotoList()

        val messageId = viewModel.showMessage.value
        assert(messageId == R.string.error_message)
    }

    @After
    fun tearDown() {
    }
}