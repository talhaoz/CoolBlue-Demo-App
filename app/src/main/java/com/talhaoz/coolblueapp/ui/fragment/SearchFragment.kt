package com.talhaoz.coolblueapp.ui.fragment

import android.widget.AbsListView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.talhaoz.coolblueapp.adapter.SearchListAdapter
import com.talhaoz.coolblueapp.data.Resource
import com.talhaoz.coolblueapp.data.model.Product
import com.talhaoz.coolblueapp.databinding.FragmentSearchBinding
import com.talhaoz.coolblueapp.util.Constants.QUERY_PAGE_SIZE
import com.talhaoz.coolblueapp.util.gone
import com.talhaoz.coolblueapp.util.showToastShort
import com.talhaoz.coolblueapp.util.visible
import com.talhaoz.coolblueapp.viewmodel.FailedType
import com.talhaoz.coolblueapp.viewmodel.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>(
    FragmentSearchBinding::inflate
) {
    private val searchViewModel: SearchViewModel by viewModels()
    private lateinit var searchListAdapter: SearchListAdapter

    private var isFirstQuery = true
    private var isNewPageLoading = false
    private var isLastPage = false
    private var isScrolling = false

    private val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount

            val isNotLoadingAndNotLastPage = !isNewPageLoading && !isLastPage
            val isAtLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount
            val isNotAtBeginning = firstVisibleItemPosition >= 0
            val isTotalMoreThanVisible = totalItemCount >= QUERY_PAGE_SIZE
            val shouldPaginate = isNotLoadingAndNotLastPage && isAtLastItem
                    && isNotAtBeginning && isTotalMoreThanVisible && isScrolling

            if (shouldPaginate) {
                isNewPageLoading = true
                searchViewModel.getSearchResults()
                isScrolling = false
            }
        }

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                isScrolling = true
            }
        }
    }

    override fun onCreateFinished() {
        initRecyclerView()
        initSearchBar()
        initObservers()
    }

    private fun initObservers() {
        searchViewModel.searchLiveData.observe(this) { result ->
            when (result) {
                is Resource.Loading<*> -> {
                    hideEmptyInfoScreen()
                    hideErrorInfoScreen()
                    showProgressBar()
                }
                is Resource.Success<*> -> {
                    hideProgressBar()
                    searchListAdapter.submitList(result.data as ArrayList<Product>)
                    binding.rvProductList.visible()
                    isLastPage = searchViewModel.isLastPage()
                    if (isLastPage) {
                        binding.rvProductList.setPadding(0, 0, 0, 0)
                    }
                }
                is Resource.Failed<*> -> {
                    hideProgressBar()
                    if (result.type != FailedType.ELSE) {
                        binding.tvCloudErrorInfoText.text = result.message
                    }
                    hideEmptyInfoScreen()
                    binding.rvProductList.gone()
                    binding.containerFailedInfoScreen.visible()
                }
            }
        }
    }

    private fun initRecyclerView() {
        searchListAdapter = SearchListAdapter(onAddToShoppingCartClick)
        binding.rvProductList.apply {
            adapter = searchListAdapter
            addOnScrollListener(this@SearchFragment.scrollListener)
        }
    }

    private val onAddToShoppingCartClick =
        fun(product: Product) {
            context?.showToastShort("Product added to cart!")
        }

    private fun initSearchBar() {
        with(binding) {
            // to dismiss keyboard on start
            searchView.clearFocus()
            searchView.setOnQueryTextListener(object :
                androidx.appcompat.widget.SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    searchView.clearFocus()
                    query?.let { queryStr ->
                        searchViewModel.getSearchResults(queryStr)
                        if (!isFirstQuery) {
                            searchListAdapter.clearList()
                            rvProductList.gone()
                        }
                    }
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return true
                }
            })
        }
    }

    private fun hideProgressBar() {
        if (isNewPageLoading) {
            binding.progressBarBottom.gone()
            isNewPageLoading = false
        } else {
            binding.progressBarMain.gone()
        }
    }

    private fun showProgressBar() {
        if (isNewPageLoading) {
            binding.progressBarBottom.visible()
        } else {
            binding.progressBarMain.visible()
        }
    }

    private fun hideEmptyInfoScreen() {
        binding.containerEmptyInfoScreen.gone()
    }

    private fun hideErrorInfoScreen() {
        binding.containerFailedInfoScreen.gone()
    }
}