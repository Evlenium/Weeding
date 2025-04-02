package com.applications.weedingappcompetitions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.applications.weedingappcompetitions.databinding.FragmentCompetitionBinding

class CompetitionFragment : Fragment() {

    private var _binding: FragmentCompetitionBinding? = null
    private val binding: FragmentCompetitionBinding
        get() = _binding!!

    private val numParticipants by lazy(LazyThreadSafetyMode.NONE) {
        requireArguments().getInt(ARGS_NUM_PARTICIPANTS)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCompetitionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val playerList =
            MutableList(numParticipants) { index ->
                Player(
                    id = index,
                    name = "",
                    vinNum = 0,
                    lastNum = -1
                )
            }
        val playerAdapter = PlayerAdapter(playerList)
        with(binding) {
            recyclerView.adapter = playerAdapter
            buttonApply.setOnClickListener {
                if (!checkEmptyFields(playerList)) {
                    createVinNum(playerList)
                    findNavController().navigate(
                        R.id.action_competitionFragment_to_gamingFragment,
                    )
                } else {
                    showMessage()
                }
            }
        }
        playerAdapter.submitList(playerList)
    }

    private fun createVinNum(listPlayer: MutableList<Player>) {
        val localStorage = LocalStorage(requireContext())
        listPlayer.map {
            it.vinNum = (0..13).random()
        }
        localStorage.savePlayerList(listPlayer)
    }

    private fun checkEmptyFields(list: MutableList<Player>) =
        list.any {
            it.name.isEmpty()
        }

    private fun showMessage() {
        Toast.makeText(requireContext(), "Введите имена всех участников", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val ARGS_NUM_PARTICIPANTS = "num_participants"
        fun createArgs(num: Int) = bundleOf(ARGS_NUM_PARTICIPANTS to num)
    }
}