package com.interview.kingpower.repository

import com.interview.kingpower.network.PhotoApiService

class PhotoListRepository(private val photoApiService: PhotoApiService) {

    suspend fun getPhotoList() =
        photoApiService.getPhotoList()
}