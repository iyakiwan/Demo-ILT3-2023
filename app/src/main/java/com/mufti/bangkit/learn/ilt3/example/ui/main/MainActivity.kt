package com.mufti.bangkit.learn.ilt3.example.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.mufti.bangkit.learn.ilt3.example.data.Result
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mufti.bangkit.learn.ilt3.example.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[MainViewModel::class.java]

        setupRecyclerView()

        observerListUser()
        getUser()
    }

    private fun setupRecyclerView() {
        adapter = UserAdapter(mutableListOf())

        binding.rvUsers.setHasFixedSize(true)
        binding.rvUsers.layoutManager = LinearLayoutManager(this)
        binding.rvUsers.adapter = adapter
    }

    private fun observerListUser() {
        viewModel.listUser.observe(this){
            when(it){
                is Result.Loading -> {
                    binding.pvUsers.isVisible = true
                }
                is Result.Success -> {
                    binding.pvUsers.isVisible = false
                    adapter.refreshData(it.data)
                }
                is Result.Error -> {
                    binding.pvUsers.isVisible = false
                    Toast.makeText(this@MainActivity, it.error, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun getUser() {
        viewModel.getListUser()
    }
}