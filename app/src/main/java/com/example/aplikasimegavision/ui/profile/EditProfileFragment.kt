package com.example.aplikasimegavision.ui.profile

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.aplikasimegavision.R
import com.example.aplikasimegavision.databinding.FragmentEditProfileBinding
import java.text.SimpleDateFormat
import java.util.*

class EditProfileFragment : Fragment() {

    private var _binding: FragmentEditProfileBinding? = null
    private val binding get() = _binding!!

    private val calendar = Calendar.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar()
        setupGenderDropdown()
        setupDatePicker()
        populateExistingData()

        binding.btnSimpanPerubahan.setOnClickListener {
            saveChanges()
        }
    }

    private fun setupToolbar() {
        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun setupGenderDropdown() {
        val genderOptions = listOf("Pilih Jenis Kelamin", "Laki-laki", "Perempuan")
        val adapter = ArrayAdapter(requireContext(), R.layout.item_dropdown, genderOptions)
        binding.actvJenisKelamin.setAdapter(adapter)
    }

    private fun setupDatePicker() {
        binding.etTanggalLahir.setOnClickListener {
            showDatePickerDialog()
        }
        binding.tilTanggalLahir.setEndIconOnClickListener {
            showDatePickerDialog()
        }
    }

    private fun showDatePickerDialog() {
        val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            val sdf = SimpleDateFormat("dd MMM yyyy", Locale("id", "ID"))
            binding.etTanggalLahir.setText(sdf.format(calendar.time))
        }
        DatePickerDialog(
            requireContext(),
            dateSetListener,
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    private fun populateExistingData() {
        // In real app, load from ViewModel
        binding.etNamaLengkap.setText("Rubby Ferdiansyah")
        binding.etTanggalLahir.setText("29 May 2026")
    }

    private fun saveChanges() {
        val nama = binding.etNamaLengkap.text.toString().trim()
        val gender = binding.actvJenisKelamin.text.toString()
        val tanggalLahir = binding.etTanggalLahir.text.toString().trim()

        if (nama.isEmpty()) {
            binding.tilNamaLengkap.error = "Nama tidak boleh kosong"
            return
        }
        binding.tilNamaLengkap.error = null

        // TODO: Call ViewModel to save via API
        Toast.makeText(requireContext(), "Perubahan disimpan!", Toast.LENGTH_SHORT).show()
        findNavController().navigateUp()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}