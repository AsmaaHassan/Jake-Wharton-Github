package com.example.jakewhartongithub.ui.home

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jakewhartongithub.R
import com.example.jakewhartongithub.viewmodels.ReposViewModel
import com.example.jakewhartongithub.viewmodels.ReposViewModelFactory
import kotlinx.coroutines.flow.collectLatest
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.support.kodein
import org.kodein.di.generic.instance

/**
 * Created by Asmaa Hassan
 */
class HomeFragment : Fragment(), KodeinAware {
    //Dependency injection
    override val kodein: Kodein by kodein()
    private val repoViewModelFactory: ReposViewModelFactory by instance()
    private val repoViewModel: ReposViewModel by lazy {
        ViewModelProvider(
            this,
            repoViewModelFactory
        ).get(ReposViewModel::class.java)
    }

    //views
    lateinit var rootView: View

    //adapters
    private lateinit var reposAdapter: ReposAdapter

    companion object {
        fun newInstance() = HomeFragment()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        rootView = inflater.inflate(R.layout.home_fragment, container, false)
        return rootView
    }

    @ExperimentalPagingApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        intiRecyclerView()

        lifecycleScope.launchWhenStarted {
            repoViewModel.jakeWhortonRepos.collectLatest { response ->
                rootView.findViewById<RecyclerView>(R.id.rvJakeRepos).isVisible = true
                reposAdapter.submitData(response)
            }
        }

    }

    private fun initAdapter() {
        reposAdapter = ReposAdapter(requireContext())

        reposAdapter.addLoadStateListener { loadState ->
            // Show loading spinner during initial load or refresh.
            rootView.findViewById<ProgressBar>(R.id.progressBar).isVisible =
                loadState.refresh is LoadState.Loading

            // Toast on any error, regardless of whether it came from RemoteMediator or PagingSource
            val errorState = loadState.source.append as? LoadState.Error
                ?: loadState.source.prepend as? LoadState.Error
                ?: loadState.append as? LoadState.Error
                ?: loadState.prepend as? LoadState.Error
            errorState?.let {
                Toast.makeText(
                    requireContext(),
                    "\uD83D\uDE28 Wooops ${it.error}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }


    private fun intiRecyclerView() {
        initAdapter()
        rootView.findViewById<RecyclerView>(R.id.rvJakeRepos).apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = reposAdapter
        }

    }

}