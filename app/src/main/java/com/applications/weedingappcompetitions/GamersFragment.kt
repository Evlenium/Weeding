package com.applications.weedingappcompetitions

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.applications.weedingappcompetitions.databinding.FragmentGamersBinding

class GamersFragment : Fragment() {
    private var _binding: FragmentGamersBinding? = null
    private val binding get() = _binding!!
    private val mapPlayers by lazy(LazyThreadSafetyMode.NONE) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requireArguments().getParcelable(ARGS_PARTICIPANTS, Players::class.java)
        } else {
            requireArguments().getParcelable(ARGS_PARTICIPANTS)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGamersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val resultPlayers = mapPlayers?.get()?.toList() ?: emptyList()
        val playerAdapter = PlayerAdapterConst(players = resultPlayers) {
            Log.d("MyTag", mapPlayers.toString())

        }
        with(binding) {
            recyclerView.adapter = playerAdapter
        }
        playerAdapter.submitList(resultPlayers)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val ARGS_PARTICIPANTS = "args_participants"
        fun createArgs(players: Players) = bundleOf(ARGS_PARTICIPANTS to players)
    }
}