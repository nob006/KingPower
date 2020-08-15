package com.interview.kingpower.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ToolbarViewModel: ViewModel() {
    var titleText = MutableLiveData<String>()
}