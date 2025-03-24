package com.applications.weedingappcompetitions

import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.core.text.isDigitsOnly
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.applications.weedingappcompetitions.databinding.FragmentStartBinding

class StartFragment : Fragment() {
    private var _binding: FragmentStartBinding? = null
    private val binding get() = _binding!!

    private var inputText: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val editText = binding.numWeight
        val keyboard = binding.myKeyboard
        editText.setRawInputType(InputType.TYPE_CLASS_TEXT)
        editText.setTextIsSelectable(true)
        val ic = editText.onCreateInputConnection(EditorInfo())
        keyboard.setInputConnection(ic)
        keyboard.mButtonEnter?.setOnClickListener {
            applyNumParticipants()
        }
        with(binding) {
            buttonApply.setOnClickListener {
                applyNumParticipants()
            }
            editText.addTextChangedListener(
                beforeTextChanged = { s, start, count, after -> },
                onTextChanged = { s, start, before, count -> },
                afterTextChanged = { s ->
                    inputText = s.toString()
                })
        }
    }

    private fun applyNumParticipants() {
        if (inputText.isNotEmpty() && inputText.isDigitsOnly()) {
            findNavController().navigate(
                R.id.action_startFragment_to_competitionFragment,
                CompetitionFragment.createArgs(inputText.toInt())
            )
        } else {
            Toast.makeText(requireContext(), "Введите количество участников", Toast.LENGTH_SHORT)
                .show()
        }
    }
}