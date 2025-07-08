package com.finapp.presentation.screens.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.finapp.presentation.viewModels.HomeViewModel
import com.finapp.databinding.FragmentHomeBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.finapp.presentation.uiState.UiState

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.balanceState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }

                is UiState.Success -> {
                    binding.progressBar.visibility = View.GONE
                    val account = state.data
                    binding.textViewBalance.text =
                        "Olá ${account.name}, seu saldo é R$ ${account.balance}"
                }
                is UiState.Error ->{
                    binding.textViewBalance.text = state.message
                    binding.progressBar.visibility = View.GONE
                }

                else -> Unit
            }
        }

        viewModel.loadHomeData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

