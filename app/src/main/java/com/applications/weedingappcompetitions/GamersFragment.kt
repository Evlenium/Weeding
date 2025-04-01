package com.applications.weedingappcompetitions

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.applications.weedingappcompetitions.databinding.FragmentGamersBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

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
            findNavController().navigate(
                R.id.action_gamingFragment_to_fragmentGamer,
                FragmentGamer.createArgs(it)
            )
        }
        with(binding) {
            recyclerView.adapter = playerAdapter
        }
        playerAdapter.submitList(resultPlayers)
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            showDialog()
        }
    }

    private fun showDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Завершить игру")
            .setMessage("Все несохраненные данные будут потеряны")
            .setNegativeButton("Отмена") { dialog, which ->
            }
            .setPositiveButton("Завершить") { dialog, which ->
                findNavController().popBackStack()
                val sharedPreferences =
                    requireContext().getSharedPreferences("players", Context.MODE_PRIVATE)
                sharedPreferences.edit().clear().apply()
            }
            .show()
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