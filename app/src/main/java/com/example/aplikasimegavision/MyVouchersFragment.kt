package com.example.aplikasimegavision

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MyVouchersFragment : Fragment() {

    private lateinit var rvMyVouchers: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_my_vouchers, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnBack = view.findViewById<ImageView>(R.id.btnBackMyVouchers)
        btnBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        rvMyVouchers = view.findViewById(R.id.rvMyVouchers)
        rvMyVouchers.layoutManager = LinearLayoutManager(requireContext())

        loadDataDariFirebase()
    }

    private fun loadDataDariFirebase() {
        val database = FirebaseDatabase.getInstance("https://myapp-megavision-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("VoucherSaya")

        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val daftarVoucherAktif = mutableListOf<VoucherAktif>()

                for (data in snapshot.children) {
                    val voucher = data.getValue(VoucherAktif::class.java)
                    if (voucher != null) {
                        daftarVoucherAktif.add(voucher)
                    }
                }

                rvMyVouchers.adapter = MyVoucherAdapter(
                    listVoucher = daftarVoucherAktif,
                    onGunakanClick = { voucherYgDiklik ->
                        database.child(voucherYgDiklik.idFirebase).removeValue()

                        showSuccessTukarDialog()
                    }
                )
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    private fun showSuccessTukarDialog() {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_success_tukar, null)
        val dialog = MaterialAlertDialogBuilder(requireContext())
            .setView(dialogView)
            .create()

        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val tvTitle = dialogView.findViewById<TextView>(R.id.tvSuccessTitle)
        val tvMessage = dialogView.findViewById<TextView>(R.id.tvSuccessMessage)
        val btnKembali = dialogView.findViewById<Button>(R.id.btnTutupSuccess)

        tvTitle?.text = "Voucher Digunakan!"
        tvMessage?.text = "Silakan tunjukkan bukti ini saat melakukan pembayaran tagihan."
        btnKembali?.text = "Tutup"

        if (btnKembali != null) {
            btnKembali.setOnClickListener {
                dialog.dismiss()
            }
        } else {
            dialog.setCanceledOnTouchOutside(true)
        }

        dialog.show()
    }
}