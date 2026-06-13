package com.example.aplikasimegavision

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment

class BantuanFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_bantuan, container, false)

        // Navigation Menu Utama
        view.findViewById<View>(R.id.card_administrasi).setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, AdministrasiTagihanFragment())
                .addToBackStack(null)
                .commit()
        }

        view.findViewById<View>(R.id.card_upgrade_paket).setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, UpgradeFragment())
                .addToBackStack(null)
                .commit()
        }

        view.findViewById<View>(R.id.card_permasalahan_teknis).setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, TeknisFragment())
                .addToBackStack(null)
                .commit()
        }

        // Klik Pertanyaan FAQ 1
        val cardP1 = view.findViewById<View>(R.id.card_pertanyaan1)
        cardP1.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, DetailSolusiUpgradeFragment())
                .addToBackStack(null)
                .commit()
        }

        // Live Filter Pencarian
        val etSearch = view.findViewById<EditText>(R.id.et_search)
        etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val query = s.toString().lowercase().trim()
                if ("bagaimana cara mengajukan upgrade".contains(query)) {
                    cardP1.visibility = View.VISIBLE
                } else {
                    cardP1.visibility = View.GONE
                }
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        return view
    }
}