package com.example.jakewhartongithub.ui.home

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jakewhartongithub.JakeWhortonApp
import com.example.jakewhartongithub.R
import com.example.jakewhartongithub.viewmodels.ReposViewModel
import com.example.jakewhartongithub.viewmodels.ReposViewModelFactory
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.kodein.di.DIAware
import org.kodein.di.instance
/**
 * Created by Asmaa Hassan
 */
class HomeFragment : Fragment(), DIAware {
    //Dependency injection
    override val di by lazy { (requireActivity().applicationContext as JakeWhortonApp).di }
    private val repoViewModelFactory: ReposViewModelFactory by instance()
    private val repoViewModel: ReposViewModel by lazy {
        ViewModelProvider(
            this,
            repoViewModelFactory
        ).get(ReposViewModel::class.java)
    }

    //data
    private lateinit var reposAdapter: ReposAdapter

    //views
    lateinit var rootView: View

    companion object {
        fun newInstance() = HomeFragment()
    }
    private fun setupView() {
        lifecycleScope.launch {
            repoViewModel.jakeWhortonRepos.collect {
                reposAdapter.submitData(it)
            }
        }
    }

    private fun setupList() {
        reposAdapter = ReposAdapter(requireContext())

        rootView.findViewById<RecyclerView>(R.id.rvStarredRepos).apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = reposAdapter
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        rootView = inflater.inflate(R.layout.main_fragment, container, false)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupList()
        setupView()
    }

}