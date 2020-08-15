package com.interview.kingpower.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.interview.kingpower.model.PhotoRes
import com.interview.kingpower.repository.PhotoListRepository
import kotlinx.coroutines.launch

class PhotoViewModel(
    private val photoListRepository: PhotoListRepository
) : ViewModel() {

    private var _photoRes = MutableLiveData<PhotoRes>()
    val photoRes: LiveData<PhotoRes> get() = _photoRes

    fun getPhotoList(){
        Log.d("DEV" , "getPhotoList")
        viewModelScope.launch {
            val res = photoListRepository.getPhotoList()
            if (res.isSuccessful){
                _photoRes.value = res.body()
            }
            else{
                Log.d("DEV" , "Failed")
            }
        }
    }
}