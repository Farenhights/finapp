package com.finapp.presentation.screens.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.finapp.databinding.FragmentHistoryBinding
import com.finapp.presentation.screens.adapters.TransactionAdapter
import com.finapp.presentation.uiState.UiState
import com.finapp.presentation.viewModels.HistoryViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class HistoryFragment : Fragment() {

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!
    private val historyViewModel: HistoryViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = TransactionAdapter()
        binding.recyclerHistory.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerHistory.adapter = adapter

        historyViewModel.transactionsState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.recyclerHistory.visibility = View.GONE
                    binding.emptyStateText.visibility = View.GONE
                }

                is UiState.Success -> {
                    Log.d("HistoryFragment", "Lista: ${state.data}")
                    adapter.submitList(state.data)

                    binding.progressBar.visibility = View.GONE
                    binding.recyclerHistory.visibility = View.VISIBLE
                    binding.emptyStateText.visibility = View.GONE
                }

                is UiState.Error -> {
                    binding.progressBar.visibility = View.GONE
                    binding.recyclerHistory.visibility = View.GONE
                    binding.emptyStateText.visibility = View.VISIBLE
                    binding.emptyStateText.text = "Erro ao carregar transações"
                }

                null -> Unit
            }
        }

        historyViewModel.loadTransactionHistory()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
