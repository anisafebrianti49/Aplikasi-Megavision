package com.example.aplikasimegavision.ui.profile

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.aplikasimegavision.R
import com.example.aplikasimegavision.databinding.FragmentEditProfileBinding
import com.google.firebase.FirebaseApp
import com.google.firebase.database.*
import java.text.SimpleDateFormat
import java.util.*

class EditProfileFragment : Fragment() {

    private var _binding: FragmentEditProfileBinding? = null
    private val binding get() = _binding!!

    private val calendar = Calendar.getInstance()

    private lateinit var database: DatabaseReference
    private var userId: String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        try {
            FirebaseApp.initializeApp(requireContext())
        } catch (e: Exception) {
            e.printStackTrace()
        }

        val prefs =
            requireActivity().getSharedPreferences("MegavisionPrefs", Context.MODE_PRIVATE)
        userId = prefs.getString("USER_ID", "") ?: ""

        if (userId.isEmpty()) {
            Toast.makeText(requireContext(), "Sesi login tidak valid", Toast.LENGTH_SHORT).show()
            return
        }

        database = FirebaseDatabase
            .getInstance("https://myapp-megavision-default-rtdb.asia-southeast1.firebasedatabase.app")
            .getReference("pelanggan")
            .child(userId)

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
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, ProfileFragment())
                .commit()
        }
    }

    private fun setupGenderDropdown() {
        val genderOptions = listOf(
            "Pilih Jenis Kelamin",
            "Laki-laki",
            "Perempuan"
        )

        val adapter = ArrayAdapter(
            requireContext(),
            R.layout.item_dropdown,
            genderOptions
        )

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

        val dateSetListener =
            DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->

                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, month)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                val sdf =
                    SimpleDateFormat("dd MMMM yyyy", Locale("id", "ID"))

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

        database.addListenerForSingleValueEvent(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                if (!isAdded || _binding == null) return

                if (snapshot.exists()) {

                    val nama =
                        snapshot.child("nama").value?.toString() ?: ""

                    val tanggalLahir =
                        snapshot.child("tanggal_lahir").value?.toString() ?: ""

                    val jenisKelamin =
                        snapshot.child("jenis_kelamin").value?.toString() ?: ""

                    binding.etNamaLengkap.setText(nama)
                    binding.etTanggalLahir.setText(tanggalLahir)

                    if (jenisKelamin.isNotEmpty()) {
                        binding.actvJenisKelamin.setText(
                            jenisKelamin,
                            false
                        )
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {

                if (isAdded) {
                    Toast.makeText(
                        requireContext(),
                        "Gagal memuat data lama",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })
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

        binding.btnSimpanPerubahan.isEnabled = false

        Toast.makeText(
            requireContext(),
            "Menyimpan perubahan...",
            Toast.LENGTH_SHORT
        ).show()

        val updates = mapOf<String, Any>(
            "nama" to nama,
            "tanggal_lahir" to tanggalLahir,
            "jenis_kelamin" to gender
        )

        database.updateChildren(updates)
            .addOnCompleteListener { task ->

                if (!isAdded || _binding == null) return@addOnCompleteListener

                binding.btnSimpanPerubahan.isEnabled = true

                if (task.isSuccessful) {

                    requireActivity()
                        .getSharedPreferences(
                            "MegavisionPrefs",
                            Context.MODE_PRIVATE
                        )
                        .edit()
                        .putString("NAMA", nama)
                        .apply()

                    Toast.makeText(
                        requireContext(),
                        "Perubahan berhasil disimpan!",
                        Toast.LENGTH_SHORT
                    ).show()

                    parentFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, ProfileFragment())
                        .commit()

                } else {

                    Toast.makeText(
                        requireContext(),
                        "Gagal menyimpan data: ${task.exception?.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}