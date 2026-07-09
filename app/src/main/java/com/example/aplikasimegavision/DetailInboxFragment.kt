package com.example.aplikasimegavision

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment

class DetailInboxFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detail_inbox, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tvTitle = view.findViewById<TextView>(R.id.tvDetailTitle)
        val tvDate = view.findViewById<TextView>(R.id.tvDetailDate)
        val tvDescription = view.findViewById<TextView>(R.id.tvDetailDescription)
        val btnBack = view.findViewById<ImageView>(R.id.btnBack)

        val title = arguments?.getString("TITLE")
        val date = arguments?.getString("DATE")
        val description = arguments?.getString("DESC")

        tvTitle.text = title
        tvDate.text = date
        tvDescription.text = description

        btnBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }
}