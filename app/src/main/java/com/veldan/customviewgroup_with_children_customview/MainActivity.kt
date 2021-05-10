package com.veldan.customviewgroup_with_children_customview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.view.get
import com.veldan.customviewgroup_with_children_customview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        binding.customViewGroup.getViewById(R.id.default_custom_view).setOnClickListener {
//            Log.i("CustomViewGroup", "onCreate: Hello")
//        }
//
//        binding.customViewGroup[1].setOnClickListener {
//            Log.i("CustomViewGroup", "onCreate: Hello 1")
//        }
//
//        binding.customViewGroup[2].setOnClickListener {
//            Log.i("CustomViewGroup", "onCreate: Hello 2")
//        }
    }
}