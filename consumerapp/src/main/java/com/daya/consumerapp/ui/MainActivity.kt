package com.daya.consumerapp.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.daya.consumerapp.R
import com.daya.consumerapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val mainViewModel by viewModels<MainViewModel>()
    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val userAdapter = UserProfileAdapter()

        binding.apply {
            rvFav.adapter = userAdapter
            rvFav.layoutManager = LinearLayoutManager(this@MainActivity)
            rvFav.setHasFixedSize(true)
            rvFav.addItemDecoration(
                DividerItemDecoration(
                    this@MainActivity,
                    DividerItemDecoration.VERTICAL
                )
            )
        }

        mainViewModel.getAllFav.observe(this){
            userAdapter.submitList(it)
        }
    }
}