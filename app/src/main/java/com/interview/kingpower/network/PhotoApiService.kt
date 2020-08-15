package com.interview.kingpower.network

import com.interview.kingpower.model.PhotoRes
import retrofit2.Response
import retrofit2.http.GET

interface PhotoApiService {
    @GET("albums/1/photos")
    suspend fun getPhotoList(): Response<PhotoRes>
}
