package com.finapp.presentation.screens.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.finapp.databinding.FragmentTransferBinding
import com.finapp.presentation.viewModels.TransferViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.finapp.presentation.uiState.UiState

class TransferFragment : Fragment() {

    private var _binding: FragmentTransferBinding? = null
    private val binding get() = _binding!!
    private val transferViewModel: TransferViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTransferBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonTransfer.setOnClickListener {
            val to = binding.editTextTo.text.toString()
            val amount = binding.editTextAmount.text.toString().toDoubleOrNull() ?: 0.0
            transferViewModel.transfer(to, amount)
        }

        transferViewModel.transferState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }

                is UiState.Success -> {
                    binding.progressBar.visibility = View.GONE
                    binding.textViewResult.text = "Transferência realizada com sucesso."
                }

                is UiState.Error -> binding.textViewResult.text = state.message
                null -> {
                    binding.progressBar.visibility = View.GONE
                    binding.textViewResult.text = ""
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
