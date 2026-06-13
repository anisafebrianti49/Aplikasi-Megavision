package com.example.aplikasimegavision.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.aplikasimegavision.R
import com.example.aplikasimegavision.databinding.FragmentProfileOptionBinding

class ProfileOptionFragment : Fragment() {

    private var _binding: FragmentProfileOptionBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileOptionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Navigasi ke LoginFragment
        binding.btnLoginAkun.setOnClickListener {
            findNavController().navigate(R.id.action_profileOptionFragment_to_loginFragment)
        }

        // Navigasi ke RegisterFragment
        binding.btnPasangBaru.setOnClickListener {
            findNavController().navigate(R.id.action_profileOptionFragment_to_registerFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}