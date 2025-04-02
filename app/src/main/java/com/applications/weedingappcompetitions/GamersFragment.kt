package com.applications.weedingappcompetitions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.applications.weedingappcompetitions.databinding.FragmentGamersBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class GamersFragment : Fragment() {
    private var _binding: FragmentGamersBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGamersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val localStorage = LocalStorage(requireContext())
        val resultPlayers = localStorage.getPlayerList()
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
                val localStorage = LocalStorage(requireContext())
                localStorage.clearSharedPreference()
            }
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}