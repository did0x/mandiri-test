package co.saputra.mandiritest.arch.ui

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import co.saputra.mandiritest.R
import co.saputra.mandiritest.arch.ui.adapter.PlayingNowMovieAdapter
import co.saputra.mandiritest.arch.ui.adapter.TopRatedMovieAdapter
import co.saputra.mandiritest.arch.vm.MainViewModel
import co.saputra.mandiritest.base.BaseFragment
import co.saputra.mandiritest.databinding.FragmentDiscoverBinding
import com.putrash.common.Constant
import com.putrash.data.model.Movie
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class DiscoverFragment : BaseFragment<FragmentDiscoverBinding, MainViewModel>(
    FragmentDiscoverBinding::inflate
) {
    override val viewModel: MainViewModel by sharedViewModel()
    private val adapterTopMovie by lazy {
        TopRatedMovieAdapter(layoutInflater, requireContext(), ::onItemClick)
    }
    private val adapterLatestMovie by lazy {
        PlayingNowMovieAdapter(layoutInflater, requireContext(), ::onItemClick)
    }

    override fun initView(view: View, savedInstaceState: Bundle?) {
        binding.apply {
            rvMovieTop.adapter = adapterTopMovie
            rvMoviePlayingNow.adapter = adapterLatestMovie
            tvPlayingNowMore.setOnClickListener {
                val bundle = bundleOf(Constant.KEY_TYPE to Constant.TYPE_PLAYING_NOW)
                findNavController()
                    .navigate(R.id.action_discoverFragment_to_movieListFragment, bundle)
            }
            tvTopRatedMore.setOnClickListener {
                val bundle = bundleOf(Constant.KEY_TYPE to Constant.TYPE_TOP_RATED)
                findNavController()
                    .navigate(R.id.action_discoverFragment_to_movieListFragment, bundle)
            }
        }
        viewModel.getMovieGenre()
        viewModel.getPlayingNowMovies()
        viewModel.getTopRatedMovies()
    }

    override fun observeLiveData() {
        super.observeLiveData()
        viewModel.moviesPlayingNow.observe(viewLifecycleOwner) {
            adapterLatestMovie.submitList(it)
        }
        viewModel.moviesTopRated.observe(viewLifecycleOwner) {
            adapterTopMovie.submitList(it)
        }
    }

    private fun onItemClick(movie: Movie) {
        val bundle = bundleOf(Constant.KEY_MOVIE to movie.id)
        findNavController()
            .navigate(R.id.action_discoverFragment_to_detailFragment, bundle)
    }
}