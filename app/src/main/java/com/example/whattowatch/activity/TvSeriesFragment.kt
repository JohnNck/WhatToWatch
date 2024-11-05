package com.example.whattowatch.activity

import com.example.whattowatch.adapters.SeriesAdapter
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.whattowatch.MyViewModel
import com.example.whattowatch.R
import com.example.whattowatch.response.SeriesResults
import com.example.whattowatch.util.Credentials
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TvSeriesFragment : Fragment(R.layout.tv_series_activity) {
    private lateinit var popularSeriesRecyclerView: RecyclerView
    private lateinit var bestSeriesRecyclerView: RecyclerView
    private lateinit var nowPlayingSeriesView: RecyclerView


    private lateinit var nowPlayingTotalPages: TextView
    private lateinit var bestSeriesTotalPages: TextView
    private lateinit var popularSeriesTotalPages: TextView


    private lateinit var previousPageButtonNowPlaying: AppCompatButton
    private lateinit var nextPageButtonNowPlaying: AppCompatButton

    private lateinit var previousPageButtonBestSeries: AppCompatButton
    private lateinit var nextPageButtonBestSeries: AppCompatButton

    private lateinit var previousPageButtonPopularSeries: AppCompatButton
    private lateinit var nextPageButtonPopularSeries: AppCompatButton



    private var nowPlayingSeriesCurrentPage = 1
    private var bestSeriesCurrentPage = 1
    private var popularSeriesCurrentPage = 1
    private val apiKey = Credentials.API_KEY
    private val myViewModel: MyViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        popularSeriesRecyclerView = view.findViewById(R.id.popularSeriesView)
        bestSeriesRecyclerView = view.findViewById(R.id.bestSeriesView)
        nowPlayingSeriesView = view.findViewById(R.id.nowPlayingSeriesView)


        nowPlayingTotalPages = view.findViewById(R.id.totalPagesNowPlaying)
        bestSeriesTotalPages = view.findViewById(R.id.totalPagesBestSeries)
        popularSeriesTotalPages = view.findViewById(R.id.totalPagesPopularSeries)


        nextPageButtonNowPlaying = view.findViewById(R.id.nextPageNowPlaying)
        previousPageButtonNowPlaying = view.findViewById(R.id.previousPageNowPlaying)
        nextPageButtonBestSeries = view.findViewById(R.id.nextPageBestSeries)
        previousPageButtonBestSeries = view.findViewById(R.id.previousPageBestSeries)
        nextPageButtonPopularSeries = view.findViewById(R.id.nextPagePopularSeries)
        previousPageButtonPopularSeries = view.findViewById(R.id.previousPagePopularSeries)


        popularSeriesRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        bestSeriesRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        nowPlayingSeriesView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        myViewModel.nowPlayingSeriesTotalPages.observe(viewLifecycleOwner) { totalPages ->
            nowPlayingTotalPages.text = "Page $nowPlayingSeriesCurrentPage/$totalPages"
            nextPageButtonNowPlaying.isEnabled = nowPlayingSeriesCurrentPage < totalPages
            previousPageButtonNowPlaying.isEnabled = nowPlayingSeriesCurrentPage > 1

        }
        myViewModel.topRatedSeriesTotalPages.observe(viewLifecycleOwner) { totalPages ->
            bestSeriesTotalPages.text = "Page $bestSeriesCurrentPage/$totalPages"
            nextPageButtonNowPlaying.isEnabled = bestSeriesCurrentPage < totalPages
            previousPageButtonNowPlaying.isEnabled = bestSeriesCurrentPage > 1

        }
        myViewModel.popularSeriesTotalPages.observe(viewLifecycleOwner) { totalPages ->
            popularSeriesTotalPages.text = "Page $popularSeriesCurrentPage/$totalPages"
            if (totalPages>500){
                popularSeriesTotalPages.text = "Page $popularSeriesCurrentPage/500"
            }
            nextPageButtonNowPlaying.isEnabled = popularSeriesCurrentPage < totalPages
            previousPageButtonNowPlaying.isEnabled = popularSeriesCurrentPage > 1

        }

        observeSeriesData()

        myViewModel.fetchPopularSeries(apiKey, popularSeriesCurrentPage)
        myViewModel.fetchTopRatedSeries(apiKey, bestSeriesCurrentPage)
        myViewModel.fetchNowPlayingSeries(apiKey, nowPlayingSeriesCurrentPage)

        nextPageButtonNowPlaying.setOnClickListener {
            if (nowPlayingSeriesCurrentPage < (myViewModel.nowPlayingSeriesTotalPages.value ?: 1)) {
                nowPlayingSeriesCurrentPage++
                myViewModel.fetchNowPlayingSeries(apiKey,nowPlayingSeriesCurrentPage)
            }
        }
        previousPageButtonNowPlaying.setOnClickListener {
            if (nowPlayingSeriesCurrentPage > 1) {
                nowPlayingSeriesCurrentPage--
                myViewModel.fetchNowPlayingSeries(apiKey,nowPlayingSeriesCurrentPage)
            }
        }

        nextPageButtonBestSeries.setOnClickListener {
            if (bestSeriesCurrentPage < (myViewModel.topRatedSeriesTotalPages.value ?: 1)) {
                bestSeriesCurrentPage++
                myViewModel.fetchTopRatedSeries(apiKey,bestSeriesCurrentPage)
            }
        }
        previousPageButtonBestSeries.setOnClickListener {
            if (bestSeriesCurrentPage > 1) {
                bestSeriesCurrentPage--
                myViewModel.fetchTopRatedSeries(apiKey,bestSeriesCurrentPage)
            }
        }

        nextPageButtonPopularSeries.setOnClickListener {
            if (popularSeriesCurrentPage < (myViewModel.popularSeriesTotalPages.value ?: 1)) {
                popularSeriesCurrentPage++
                myViewModel.fetchPopularSeries(apiKey,popularSeriesCurrentPage)
            }
        }
        previousPageButtonPopularSeries.setOnClickListener {
            if (popularSeriesCurrentPage > 1) {
                popularSeriesCurrentPage--
                myViewModel.fetchPopularSeries(apiKey,popularSeriesCurrentPage)
            }
        }
    }

    private fun observeSeriesData() {
        myViewModel.popularSeries.observe(viewLifecycleOwner) { seriesList ->
            popularSeriesRecyclerView.adapter = SeriesAdapter(seriesList) { series ->
                navigateToSeriesDetails(series)
            }
        }

        myViewModel.topRatedSeries.observe(viewLifecycleOwner) { seriesList ->
            bestSeriesRecyclerView.adapter = SeriesAdapter(seriesList) { series ->
                navigateToSeriesDetails(series)
            }
        }

        myViewModel.nowPlayingSeries.observe(viewLifecycleOwner) { seriesList ->
            nowPlayingSeriesView.adapter = SeriesAdapter(seriesList) { series ->
                navigateToSeriesDetails(series)
            }
        }
    }

    private fun navigateToSeriesDetails(series: SeriesResults) {
        val intent = Intent(requireContext(), SeriesDetailsActivity::class.java).apply {
            putExtra("id", series.seriesId)
            putExtra("name", series.name)
            putExtra("overview", series.overView)
            putExtra("poster_path", series.posterPath)
            putExtra("vote_average", series.voteAverage)
        }
        startActivity(intent)
    }
}
