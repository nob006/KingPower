package com.interview.kingpower

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.interview.kingpower.viewmodel.ToolbarViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val toolbarViewModel by viewModel<ToolbarViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        toolbar.setTitleTextColor(Color.WHITE)
        toolbarViewModel.titleText.observe(this, Observer { title ->
            supportActionBar?.title = title
        })
    }
}