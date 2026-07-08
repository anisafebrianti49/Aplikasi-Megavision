package com.example.aplikasimegavision.UI.pengaduan

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

val MegavisionBlue = Color(0xFF2451A6)
val SoftGrayBg = Color(0xFFF0F4FA)
val AdminBubbleColor = Color.White
val UserBubbleColor = MegavisionBlue

data class PesanChat(
    val teks: String = "",
    val dariUser: Boolean = true,
    val timestamp: Long = 0L
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PengaduanScreen(onBackClicked: () -> Unit) {
    val coroutineScope = rememberCoroutineScope()
    var inputPesan by remember { mutableStateOf("") }
    var daftarPesan by remember { mutableStateOf(listOf<PesanChat>()) }
    val listState = rememberLazyListState()

    val context = LocalContext.current
    SideEffect {
        val window = (context as? Activity)?.window
        window?.statusBarColor = Color.White.toArgb()
    }

    val database = FirebaseDatabase.getInstance("https://myapp-megavision-default-rtdb.asia-southeast1.firebasedatabase.app/")
    val chatRef = database.getReference("live_chat_pengaduan/sesi_demo_user")

    LaunchedEffect(Unit) {
        chatRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val list = mutableListOf<PesanChat>()
                for (child in snapshot.children) {
                    val pesan = child.getValue(PesanChat::class.java)
                    if (pesan != null) {
                        list.add(pesan)
                    }
                }
                daftarPesan = list.sortedBy { it.timestamp }
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }

    LaunchedEffect(daftarPesan.size) {
        if (daftarPesan.isNotEmpty()) {
            listState.animateScrollToItem(daftarPesan.size - 1)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(10.dp)
                                .clip(CircleShape)
                                .background(Color(0xFF10B981))
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Column {
                            Text(
                                text = "Bantuan Teknis",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color(0xFF0F172A)
                            )
                            Text(
                                text = "Admin Online",
                                fontSize = 11.sp,
                                color = Color(0xFF10B981)
                            )
                        }
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBackClicked) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Kembali")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        },
        containerColor = SoftGrayBg
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            LazyColumn(
                state = listState,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                contentPadding = PaddingValues(vertical = 16.dp)
            ) {
                items(daftarPesan) { pesan ->
                    ChatBubble(pesan = pesan, showAvatar = !pesan.dariUser)
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }

            KotakInputPesan(
                inputPesan = inputPesan,
                onValueChange = { inputPesan = it },
                onKirim = {
                    if (inputPesan.isNotBlank()) {
                        val pesanTerkirim = inputPesan
                        inputPesan = ""

                        val pesanUser = PesanChat(pesanTerkirim, true, System.currentTimeMillis())
                        chatRef.push().setValue(pesanUser)

                        coroutineScope.launch {
                            delay(1500)

                            val balasanAdmin = if (
                                pesanTerkirim.contains("halo", true) ||
                                pesanTerkirim.contains("helo", true) ||
                                pesanTerkirim.contains("min", true) ||
                                pesanTerkirim.contains("p ", true) ||
                                pesanTerkirim.equals("p", true) ||
                                pesanTerkirim.contains("tes", true)
                            ) {
                                "Halo! Selamat datang di layanan Live Chat Megavision. Ada yang bisa admin bantu hari ini? 😊"
                            } else if (
                                pesanTerkirim.contains("keluhan", true) ||
                                pesanTerkirim.contains("komplain", true) ||
                                pesanTerkirim.contains("gangguan", true)
                            ) {
                                "Baik, mohon maaf atas kendalanya ya. Bisa diinfokan keluhan detailnya mengenai apa? Apakah internetnya melambat atau putus total?"
                            } else if (
                                pesanTerkirim.contains("mati", true) ||
                                pesanTerkirim.contains("los", true) ||
                                pesanTerkirim.contains("merah", true)
                            ) {
                                "Waduh, lampu LOS merah atau mati ya? Boleh minta Nomor ID Pelanggan Anda agar tim teknisi kami bisa langsung mengecek jaringan ke rumah?"
                            } else if (pesanTerkirim.any { it.isDigit() } && pesanTerkirim.length > 5) {
                                "Terima kasih atas datanya. ID Pelanggan Anda sudah kami terima dan sedang disinkronisasi ke sistem teknisi lapangan. Mohon ditunggu ya!"
                            } else {
                                "Laporan Anda sudah kami catat nih. Ada informasi tambahan lain yang ingin disampaikan sebelum diteruskan ke agen spesialis kami?"
                            }

                            val pesanBot = PesanChat(balasanAdmin, false, System.currentTimeMillis())
                            chatRef.push().setValue(pesanBot)
                        }
                    }
                }
            )
        }
    }
}

@Composable
fun ChatBubble(pesan: PesanChat, showAvatar: Boolean) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (pesan.dariUser) Arrangement.End else Arrangement.Start,
        verticalAlignment = Alignment.Bottom
    ) {
        if (!pesan.dariUser && showAvatar) {
            Box(
                modifier = Modifier.size(32.dp).clip(CircleShape).background(MegavisionBlue),
                contentAlignment = Alignment.Center
            ) {
                Text("M", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 14.sp)
            }
            Spacer(modifier = Modifier.width(8.dp))
        } else if (!pesan.dariUser) {
            Spacer(modifier = Modifier.width(40.dp))
        }

        Box(
            modifier = Modifier
                .widthIn(max = 260.dp)
                .clip(
                    RoundedCornerShape(
                        topStart = 16.dp,
                        topEnd = 16.dp,
                        bottomStart = if (pesan.dariUser) 16.dp else 4.dp,
                        bottomEnd = if (pesan.dariUser) 4.dp else 16.dp
                    )
                )
                .background(if (pesan.dariUser) UserBubbleColor else AdminBubbleColor)
                .padding(12.dp)
        ) {
            Text(
                text = pesan.teks,
                color = if (pesan.dariUser) Color.White else Color(0xFF0F172A),
                fontSize = 14.sp,
                lineHeight = 20.sp
            )
        }
    }
}

@Composable
fun KotakInputPesan(inputPesan: String, onValueChange: (String) -> Unit, onKirim: () -> Unit) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = Color.White,
        shadowElevation = 8.dp
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = inputPesan,
                onValueChange = onValueChange,
                placeholder = { Text("Ketik pesan Anda...") },
                modifier = Modifier.weight(1f).heightIn(min = 50.dp, max = 120.dp),
                shape = RoundedCornerShape(24.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Color(0xFFCBD5E1),
                    focusedBorderColor = MegavisionBlue
                )
            )
            Spacer(modifier = Modifier.width(12.dp))
            IconButton(
                onClick = onKirim,
                modifier = Modifier.size(50.dp).clip(CircleShape).background(if (inputPesan.isNotBlank()) MegavisionBlue else Color(0xFF94A3B8))
            ) {
                Icon(Icons.Default.Send, contentDescription = "Kirim", tint = Color.White, modifier = Modifier.size(20.dp))
            }
        }
    }
}