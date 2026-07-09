package com.example.aplikasimegavision.UI.upgradepaket

import android.widget.Toast
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

val MegavisionBlue = Color(0xFF2451A6)
val SoftBlueBg = Color(0xFFF0F4FA)
val GlassmorphismColor = Color(0x33FFFFFF)

enum class UpgradeState {
    DAFTAR_PAKET, DETAIL_SYARAT, FORM_KONFIRMASI
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpgradePaketScreen(onBackClicked: () -> Unit) {
    var currentScreen by remember { mutableStateOf(UpgradeState.DAFTAR_PAKET) }
    var paketPilihanNama by remember { mutableStateOf("") }
    var paketPilihanHarga by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = if (currentScreen == UpgradeState.DAFTAR_PAKET) "" else "Detail Upgrade",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = if (currentScreen == UpgradeState.DAFTAR_PAKET) Color.White else Color(0xFF0F172A)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        when (currentScreen) {
                            UpgradeState.DAFTAR_PAKET -> onBackClicked()
                            UpgradeState.DETAIL_SYARAT -> currentScreen = UpgradeState.DAFTAR_PAKET
                            UpgradeState.FORM_KONFIRMASI -> currentScreen = UpgradeState.DETAIL_SYARAT
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Kembali",
                            tint = if (currentScreen == UpgradeState.DAFTAR_PAKET) Color.White else Color(0xFF0F172A)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                    scrolledContainerColor = Color.Transparent
                )
            )
        },
        containerColor = SoftBlueBg
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF2A62C9),
                            Color(0xFF6B9DF2),
                            SoftBlueBg,
                            SoftBlueBg
                        ),
                        startY = 0f,
                        endY = 1500f
                    )
                )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                when (currentScreen) {
                    UpgradeState.DAFTAR_PAKET -> {
                        LayarDaftarPaketModern(
                            onPaketDipilih = { nama, harga ->
                                paketPilihanNama = nama
                                paketPilihanHarga = harga
                                currentScreen = UpgradeState.DETAIL_SYARAT
                            }
                        )
                    }
                    UpgradeState.DETAIL_SYARAT -> {
                        LayarDetailSyarat(paketPilihanNama, paketPilihanHarga) { currentScreen = UpgradeState.FORM_KONFIRMASI }
                    }
                    UpgradeState.FORM_KONFIRMASI -> {
                        LayarFormulirKonfirmasi(paketPilihanNama, paketPilihanHarga) { onBackClicked() }
                    }
                }
            }
        }
    }
}

@Composable
fun LayarDaftarPaketModern(onPaketDipilih: (String, String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp)) {
            Text(
                text = "Layanan Anda Saat Ini",
                fontSize = 14.sp,
                color = Color.White.copy(alpha = 0.8f),
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(GlassmorphismColor)
                    .padding(20.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text("Bundling Silver 50 Mbps", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color.White)
                        Spacer(modifier = Modifier.height(4.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.CheckCircle, contentDescription = null, tint = Color(0xFF4ADE80), modifier = Modifier.size(14.dp))
                            Text(" Aktif • Catv Free FO", fontSize = 12.sp, color = Color.White.copy(alpha = 0.9f))
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Rekomendasi Upgrade",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF0F172A),
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .padding(bottom = 12.dp)
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .height(130.dp)
                .bounceClick { onPaketDipilih("Premium Silver 100 Mbps", "Rp 150.000") },
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Row(modifier = Modifier.fillMaxSize()) {
                Column(
                    modifier = Modifier
                        .weight(1.5f)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Rounded.Star, contentDescription = null, tint = Color(0xFFFBBF24), modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Paling Populer", fontSize = 11.sp, color = Color(0xFFF59E0B), fontWeight = FontWeight.Bold)
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text("Premium Silver 100 Mbps", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color(0xFF0F172A), lineHeight = 20.sp)
                    Spacer(modifier = Modifier.height(6.dp))
                    Text("Akses cepat & Free All Channel", fontSize = 11.sp, color = Color(0xFF64748B))
                }

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(topEnd = 20.dp, bottomEnd = 20.dp))
                        .background(Brush.linearGradient(listOf(Color(0xFF6B9DF2), MegavisionBlue))),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.Info, contentDescription = "Gambar Promo", tint = Color.White.copy(alpha = 0.5f), modifier = Modifier.size(40.dp))
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Katalog Paket Lainnya",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF0F172A),
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .padding(bottom = 12.dp)
        )

        val paketLainnya = listOf(
            Triple("Ultra Gold 200 Mbps", "Rp 250.000", "Layanan Prioritas VIP"),
            Triple("Platinum 300 Mbps", "Rp 350.000", "Tanpa Batas FUP"),
            Triple("Gamer Pro 500 Mbps", "Rp 500.000", "Ping Rendah Stabil")
        )

        LazyRow(
            contentPadding = PaddingValues(horizontal = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(paketLainnya) { paket ->
                KartuKatalogHorizontal(
                    nama = paket.first,
                    harga = paket.second,
                    deskripsi = paket.third,
                    onClick = { onPaketDipilih(paket.first, paket.second) }
                )
            }
        }

        Spacer(modifier = Modifier.height(40.dp))
    }
}

@Composable
fun KartuKatalogHorizontal(nama: String, harga: String, deskripsi: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .width(220.dp)
            .bounceClick { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(SoftBlueBg),
                contentAlignment = Alignment.Center
            ) {
                Text(harga, fontWeight = FontWeight.Bold, color = MegavisionBlue)
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(nama, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color(0xFF0F172A))
            Spacer(modifier = Modifier.height(4.dp))
            Text(deskripsi, fontSize = 11.sp, color = Color(0xFF64748B))
        }
    }
}

@Composable
fun LayarDetailSyarat(namaPaket: String, hargaPaket: String, onSetujuClick: () -> Unit) {
    val interactionSource = remember { MutableInteractionSource() }
    val coroutineScope = rememberCoroutineScope() // Ditambahkan agar bisa pakai delay

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp)
    ) {
        Text(text = "Ketentuan Perubahan Kontrak", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color(0xFF0F172A))
        Spacer(modifier = Modifier.height(16.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text(text = "Paket Yang Dipilih:", fontSize = 12.sp, color = Color(0xFF475569))
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = namaPaket, fontWeight = FontWeight.Bold, fontSize = 18.sp, color = MegavisionBlue)
                Text(text = "$hargaPaket/bulan (Belum PPN)", fontSize = 13.sp, color = Color(0xFF64748B))
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        val listSyarat = listOf(
            "Perubahan paket hanya diizinkan ke tingkat harga yang lebih tinggi (Upgrade).",
            "Pelanggan terikat kontrak baru selama 1 Tahun ke depan sejak disetujui.",
            "Apabila pemutusan sebelum masa kontrak berakhir, dikenakan penalti Rp 388.500.",
            "Gratis biaya perawatan perangkat baru."
        )

        listSyarat.forEach { syarat ->
            Row(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp), verticalAlignment = Alignment.Top) {
                Icon(Icons.Default.CheckCircle, contentDescription = null, tint = MegavisionBlue, modifier = Modifier.size(20.dp))
                Spacer(modifier = Modifier.width(12.dp))
                Text(text = syarat, fontSize = 14.sp, color = Color(0xFF334155), lineHeight = 20.sp)
            }
        }

        Spacer(modifier = Modifier.height(40.dp))

        Button(
            onClick = {
                coroutineScope.launch {
                    delay(150)
                    onSetujuClick()
                }
            },
            interactionSource = interactionSource,
            modifier = Modifier.fillMaxWidth().height(54.dp).bounceScale(interactionSource),
            colors = ButtonDefaults.buttonColors(containerColor = MegavisionBlue),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Saya Setuju & Lanjutkan", fontSize = 15.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun LayarFormulirKonfirmasi(namaPaket: String, hargaPaket: String, onSuksesSubmit: () -> Unit) {
    val context = LocalContext.current
    var inputEmail by remember { mutableStateOf("") }
    var inputTelepon by remember { mutableStateOf("") }
    var inputCatatan by remember { mutableStateOf("") }
    val interactionSource = remember { MutableInteractionSource() }

    val isEmailValid = inputEmail.contains("@") && inputEmail.endsWith(".com")
    val isTeleponValid = inputTelepon.length >= 10 && inputTelepon.all { it.isDigit() }
    val isFormValid = isEmailValid && isTeleponValid

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp)
    ) {
        Text(text = "Informasi Kontak", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color(0xFF0F172A))
        Text(text = "Tim kami akan menghubungi Anda untuk konfirmasi teknis.", fontSize = 13.sp, color = Color(0xFF64748B))

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = inputEmail,
            onValueChange = { inputEmail = it },
            label = { Text("Email Aktif") },
            modifier = Modifier.fillMaxWidth(),
            isError = inputEmail.isNotEmpty() && !isEmailValid,
            shape = RoundedCornerShape(12.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = MegavisionBlue)
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = inputTelepon,
            onValueChange = { inputTelepon = it },
            label = { Text("Nomor WhatsApp/Telepon") },
            modifier = Modifier.fillMaxWidth(),
            isError = inputTelepon.isNotEmpty() && !isTeleponValid,
            shape = RoundedCornerShape(12.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = MegavisionBlue)
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = inputCatatan,
            onValueChange = { inputCatatan = it },
            label = { Text("Catatan Tambahan (Opsional)") },
            modifier = Modifier.fillMaxWidth().height(120.dp),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = MegavisionBlue)
        )

        Spacer(modifier = Modifier.height(40.dp))

        Button(
            onClick = {
                val database = FirebaseDatabase.getInstance()
                val myRef = database.getReference("pengajuan_upgrade")
                val dataUpgrade = hashMapOf(
                    "namaPaket" to namaPaket,
                    "hargaPaket" to hargaPaket,
                    "email" to inputEmail,
                    "telepon" to inputTelepon,
                    "catatan" to inputCatatan,
                    "status" to "Menunggu"
                )
                myRef.push().setValue(dataUpgrade)
                    .addOnSuccessListener {
                        Toast.makeText(context, "Upgrade diajukan!", Toast.LENGTH_SHORT).show()
                        onSuksesSubmit()
                    }
            },
            enabled = isFormValid,
            interactionSource = interactionSource,
            modifier = Modifier.fillMaxWidth().height(54.dp).bounceScale(interactionSource),
            colors = ButtonDefaults.buttonColors(
                containerColor = MegavisionBlue,
                disabledContainerColor = Color(0xFFCBD5E1)
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Kirim Permintaan", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = if (isFormValid) Color.White else Color(0xFF94A3B8))
        }
    }
}

fun Modifier.bounceScale(interactionSource: MutableInteractionSource) = composed {
    val isPressed by interactionSource.collectIsPressedAsState()
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.92f else 1f,
        animationSpec = spring(dampingRatio = 0.5f, stiffness = 400f),
        label = ""
    )
    this.graphicsLayer { scaleX = scale; scaleY = scale }
}

fun Modifier.bounceClick(onClick: () -> Unit) = composed {
    val interactionSource = remember { MutableInteractionSource() }
    val coroutineScope = rememberCoroutineScope()
    this
        .bounceScale(interactionSource)
        .clickable(
            interactionSource = interactionSource,
            indication = null,
            onClick = {
                coroutineScope.launch {
                    delay(150)
                    onClick()
                }
            }
        )
}