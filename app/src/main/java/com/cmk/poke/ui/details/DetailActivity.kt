package com.cmk.poke.ui.details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.VisibleForTesting
import com.cmk.poke.R
import com.cmk.poke.base.DataBindingActivity
import com.cmk.poke.databinding.ActivityDetailBinding
import com.cmk.poke.extensions.onTransformationEndContainerApplyParams
import com.cmk.poke.model.Pokemon
import com.skydoves.bundler.bundleNonNull
import com.skydoves.bundler.intentOf
import com.skydoves.transformationlayout.TransformationCompat
import com.skydoves.transformationlayout.TransformationLayout
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DetailActivity : DataBindingActivity() {

    @Inject
    lateinit var detailViewModelFactory: DetailViewModel.AssistedFactory

    private val binding: ActivityDetailBinding by binding(R.layout.activity_detail)
    private val pokemonItem: Pokemon by bundleNonNull(EXTRA_POKEMON)

    @VisibleForTesting
    val viewModel: DetailViewModel by viewModels{
        DetailViewModel.provideFactory(detailViewModelFactory,pokemonItem.name)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        onTransformationEndContainerApplyParams()
        super.onCreate(savedInstanceState)
        binding.apply {
            lifecycleOwner = this@DetailActivity
            pokemon = pokemonItem
            vm = viewModel
        }
    }

    companion object {
        @VisibleForTesting
        const val EXTRA_POKEMON = "EXTRA_POKEMON"

        fun startActivity(transformationLayout: TransformationLayout, pokemon: Pokemon) =
            transformationLayout.context.intentOf<DetailActivity> {
                putExtra(EXTRA_POKEMON to pokemon)
                TransformationCompat.startActivity(transformationLayout, intent)
            }
    }
}