package com.fitpeo.fitpeoassignment.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.fitpeo.fitpeoassignment.adapter.RecyclerViewAdapter
import com.fitpeo.fitpeoassignment.databinding.ActivityMainBinding
import com.fitpeo.fitpeoassignment.service.Resource
import com.fitpeo.fitpeoassignment.viewmodel.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: RecyclerViewAdapter
    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.visibility = View.INVISIBLE
        binding.progressBar.visibility = View.VISIBLE

        initViewModel()

    }

    private fun initViewModel(){

        viewModel.getDetails()

        viewModel.liveDataList.observe(this) { it ->
            when (it.status) {
                Resource.Status.SUCCESS -> {

                    it.data.let {
                        binding.progressBar.visibility = View.INVISIBLE

                        if (it != null) {
                            if (it.isEmpty()) {
                                binding.recyclerView.visibility = View.INVISIBLE
                            } else {
                                binding.recyclerView.visibility = View.VISIBLE
                                adapter = RecyclerViewAdapter(it)
                                binding.recyclerView.adapter = adapter
                                adapter.notifyDataSetChanged()
                            }
                        } else {
                            binding.recyclerView.visibility = View.INVISIBLE
                        }
                    }
                }
                Resource.Status.ERROR -> {
                    Toast.makeText(applicationContext, it.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}