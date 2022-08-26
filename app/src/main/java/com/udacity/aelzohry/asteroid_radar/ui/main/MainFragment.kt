package com.udacity.aelzohry.asteroid_radar.ui.main

import android.os.Bundle
import android.view.*
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.udacity.aelzohry.asteroid_radar.R
import com.udacity.aelzohry.asteroid_radar.data.database.AsteroidDatabase
import com.udacity.aelzohry.asteroid_radar.databinding.FragmentMainBinding
import com.udacity.aelzohry.asteroid_radar.repository.AsteroidRepository

class MainFragment : Fragment() {

    private lateinit var factory: MainViewModelFactory
    private val viewModel: MainViewModel by viewModels { factory }
    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentMainBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val database = AsteroidDatabase.getDatabase(requireContext())
        val repo = AsteroidRepository(database)
        factory = MainViewModelFactory(repo)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        val adapter = MainAdapter(MainAdapter.ClickListener {
            // Go To Details Screen
            val destination = MainFragmentDirections.actionShowDetail(it)
            findNavController().navigate(destination)
        })
        binding.asteroidRecycler.adapter = adapter

        // setup menu
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.main_overflow_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                viewModel.showFilter = when (menuItem.itemId) {
                    R.id.show_today_menu -> ShowAsteroidsFilter.TODAY
                    R.id.show_week_menu -> ShowAsteroidsFilter.WEEK
                    else -> ShowAsteroidsFilter.ALL
                }
                return true
            }
        }, viewLifecycleOwner)
    }
}