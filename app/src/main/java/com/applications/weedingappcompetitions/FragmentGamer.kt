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
import kotlin.math.abs

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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGamerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var inputString = ""
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
                checkWinner(inputString, player!!)
            }
            keyboardWeight.mButtonEnter?.setOnClickListener { checkWinner(inputString, player!!) }
        }
    }

    private fun checkWinner(inputString: String, gamer: Player) {
        if (inputString.isEmpty()) {
            Toast.makeText(requireContext(), "Введите вес", Toast.LENGTH_SHORT).show()
        } else {
            val input: Int = inputString.toInt()
            if (gamer.vinNum == input) {
                bindingTextView("Победитель!", R.color.green)
                return
            }
            if (gamer.lastNum == -1) {
                if (abs(player?.vinNum!! - input) <= 3) {
                    bindingTextView("Тепло", R.color.red)
                } else {
                    bindingTextView("Холодно", R.color.blue)
                }
            } else {
                val previousDifference = abs(player?.vinNum!! - gamer.lastNum)
                val currentDifference = abs(player?.vinNum!! - input)
                if (previousDifference < currentDifference && gamer.lastNum != input) {
                    bindingTextView("Холоднее", R.color.blue)
                } else if (previousDifference > currentDifference && gamer.lastNum != input) {
                    bindingTextView("Теплее", R.color.red)
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Вы ввели значение, которое уже было",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            val localStorage = LocalStorage(requireContext())
            localStorage.updateList(input, gamer)
        }
    }

    private fun bindingTextView(s: String, green: Int) {
        binding.textViewColdHot.text = s
        binding.textViewColdHot.setTextColor(
            ContextCompat.getColor(
                requireContext(),
                green
            )
        )
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