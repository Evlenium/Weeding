package com.applications.weedingappcompetitions

import android.os.Build
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.applications.weedingappcompetitions.databinding.FragmentGamerBinding

class FragmentGamer : Fragment() {
    private var _binding: FragmentGamerBinding? = null
    private val binding: FragmentGamerBinding
        get() = _binding!!

    private val player by lazy(LazyThreadSafetyMode.NONE) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requireArguments().getParcelable(ARGS_PLAYER, Player::class.java)
        } else {
            requireArguments().getParcelable(ARGS_PLAYER)
        }
    }
    private var inputString = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGamerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            editTextNumWeight.apply {
                setRawInputType(InputType.TYPE_CLASS_TEXT)
                setTextIsSelectable(true)
                isFocusable = false
            }
            val ic = editTextNumWeight.onCreateInputConnection(EditorInfo())
            keyboardWeight.setInputConnection(ic)
            textViewName.text = player?.name ?: ""
            textViewRightAnswer.text = player?.vinNum.toString()
            editTextNumWeight.addTextChangedListener(
                beforeTextChanged = { s, start, count, after -> },
                onTextChanged = { s, start, before, count -> },
                afterTextChanged = { s -> inputString = s.toString() })
            buttonApply.setOnClickListener {
                checkWinner()
            }
            keyboardWeight.mButtonEnter?.setOnClickListener { checkWinner() }
        }
    }

    private fun checkWinner() {
        if (inputString.isEmpty()) {
            Toast.makeText(requireContext(), "Введите вес", Toast.LENGTH_SHORT).show()
        } else {
            if (player?.vinNum!! > inputString.toInt()) {
                binding.textViewColdHot.text = "Холодно"
                binding.textViewColdHot.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.blue
                    )
                )
            } else if (player?.vinNum!! < inputString.toInt()) {
                binding.textViewColdHot.text = "Тепло"
                binding.textViewColdHot.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.red
                    )
                )
            } else {
                binding.textViewColdHot.text = "Победитель!"
                binding.textViewColdHot.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.green
                    )
                )
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val ARGS_PLAYER = "player"
        fun createArgs(player: Player) = bundleOf(ARGS_PLAYER to player)
    }
}