package com.example.fuckingtests.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.fuckingtests.R
import com.example.fuckingtests.adapters.UsersAdapter
import com.example.fuckingtests.databinding.ActivityMainBinding
import com.example.fuckingtests.network.JSONPlaceHolderApi
import com.example.fuckingtests.ui.dialogs.InfoDialogFragment
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.initialization.InitializationStatus

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var viewModel: MainViewModel

    private val usersAdapter = UsersAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        MobileAds.initialize(this) {}

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        binding.usersListRv.adapter = usersAdapter

        loadAd()

        viewModel.users.observe(this) { users ->
            usersAdapter.users = users
        }

        viewModel.error.observe(this) { error ->
            InfoDialogFragment("Ошибка", error)
                .show(supportFragmentManager, null)
        }

        viewModel.loadUsers()
    }

    private fun loadAd() {
        val adRequest = AdRequest.Builder().build()
        binding.adBannerAv.loadAd(adRequest)
    }

    companion object {
        const val TAG = "MainActivityTAG"
    }
}