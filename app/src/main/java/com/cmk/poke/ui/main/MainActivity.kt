package com.cmk.poke.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.VisibleForTesting
import com.cmk.poke.R
import com.cmk.poke.base.DataBindingActivity
import com.cmk.poke.databinding.ActivityMainBinding
import com.cmk.poke.ui.adapter.PokemonAdapter
import com.skydoves.transformationlayout.onTransformationStartContainer
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : DataBindingActivity() {

    @VisibleForTesting val viewModel : MainViewModel by viewModels()
    private val binding: ActivityMainBinding by binding(R.layout.activity_main)

    override fun onCreate(savedInstanceState: Bundle?) {
        onTransformationStartContainer()
        super.onCreate(savedInstanceState)
        binding.apply {
            lifecycleOwner = this@MainActivity
            adapter = PokemonAdapter()
            vm = viewModel
        }
    }
}