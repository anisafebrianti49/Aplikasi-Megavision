package com.example.aplikasimegavision

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.cardview.widget.CardView
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment

class BantuanFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Mengarah ke layout dashboard bantuan Anda
        return inflater.inflate(R.layout.fragment_bantuan, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val cardAdministrasi = view.findViewById<CardView>(R.id.card_administrasi)
        val cardUpgradePaket = view.findViewById<CardView>(R.id.card_upgrade_paket)
        val cardPermasalahanTeknis = view.findViewById<CardView>(R.id.card_permasalahan_teknis)

        val etSearch = view.findViewById<EditText>(R.id.et_search)
        val cardPertanyaan1 = view.findViewById<CardView>(R.id.card_pertanyaan1)
        val cardPertanyaan2 = view.findViewById<CardView>(R.id.card_pertanyaan2)
        val cardPertanyaan3 = view.findViewById<CardView>(R.id.card_pertanyaan3)

        etSearch?.doOnTextChanged { text, _, _, _ ->
            val query = text.toString().lowercase().trim()

            if (query.isEmpty()) {
                cardPertanyaan1?.visibility = View.VISIBLE
                cardPertanyaan2?.visibility = View.VISIBLE
                cardPertanyaan3?.visibility = View.VISIBLE
            } else {
                cardPertanyaan1?.visibility = if ("bagaimana cara mengajukan upgrade".contains(query)) {
                    View.VISIBLE
                } else {
                    View.GONE
                }

                cardPertanyaan2?.visibility = if ("kapan tagihan megavision jatuh tempo".contains(query)) {
                    View.VISIBLE
                } else {
                    View.GONE
                }

                cardPertanyaan3?.visibility = if ("bagaimana cara mengatasi internet lambat".contains(query)) {
                    View.VISIBLE
                } else {
                    View.GONE
                }
            }
        }



        cardAdministrasi?.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, AdministrasiTagihanFragment())
                .addToBackStack(null)
                .commit()
        }

        cardUpgradePaket?.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, FaqUpgradeKecepatanFragment())
                .addToBackStack(null)
                .commit()
        }

        cardPermasalahanTeknis?.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, TeknisFragment())
                .addToBackStack(null)
                .commit()
        }
    }
}