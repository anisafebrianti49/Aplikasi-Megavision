package com.example.aplikasimegavision

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aplikasimegavision.UI.pengaduan.PengaduanScreen
import com.example.aplikasimegavision.UI.upgradepaket.UpgradePaketScreen
import com.example.aplikasimegavision.UI.upgradepaket.MegavisionBlue
import androidx.compose.foundation.shape.RoundedCornerShape

enum class HalamanApp {
    MENU_UTAMA,
    UPGRADE_PAKET,
    PENGADUAN_GANGGUAN
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var halamanSekarang by remember { mutableStateOf(HalamanApp.MENU_UTAMA) }

            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                when (halamanSekarang) {
                    HalamanApp.MENU_UTAMA -> {
                        MenuUtamaSimulasi(
                            onMajuKeUpgrade = { halamanSekarang = HalamanApp.UPGRADE_PAKET },
                            onMajuKePengaduan = { halamanSekarang = HalamanApp.PENGADUAN_GANGGUAN }
                        )
                    }
                    HalamanApp.UPGRADE_PAKET -> {
                        UpgradePaketScreen(
                            onBackClicked = { halamanSekarang = HalamanApp.MENU_UTAMA }
                        )
                    }
                    HalamanApp.PENGADUAN_GANGGUAN -> {
                        PengaduanScreen(
                            onBackClicked = { halamanSekarang = HalamanApp.MENU_UTAMA }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun MenuUtamaSimulasi(onMajuKeUpgrade: () -> Unit, onMajuKePengaduan: () -> Unit) {
    val interactionSourceUpgrade = remember { MutableInteractionSource() }
    val interactionSourcePengaduan = remember { MutableInteractionSource() }
    val interactionSourceVoucher = remember { MutableInteractionSource() }

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Dashboard Megavision",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = MegavisionBlue
        )
        Text(
            text = "Silakan pilih fitur yang ingin kamu uji coba:",
            fontSize = 14.sp,
            color = Color.Gray,
            modifier = Modifier.padding(top = 4.dp, bottom = 32.dp)
        )

        Button(
            onClick = onMajuKeUpgrade,
            interactionSource = interactionSourceUpgrade,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .bounceScale(interactionSourceUpgrade),
            colors = ButtonDefaults.buttonColors(containerColor = MegavisionBlue),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text("Buka Fitur Upgrade Paket", fontSize = 14.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onMajuKePengaduan,
            interactionSource = interactionSourcePengaduan,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .bounceScale(interactionSourcePengaduan),
            colors = ButtonDefaults.buttonColors(containerColor = MegavisionBlue),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text("Buka Fitur Pengaduan Gangguan", fontSize = 14.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val intent = Intent(context, VoucherActivity::class.java)
                context.startActivity(intent)
            },
            interactionSource = interactionSourceVoucher,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .bounceScale(interactionSourceVoucher),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFB300)),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text("Buka Fitur Voucher Poin", fontSize = 14.sp, color = Color.White)
        }
    }
}

fun Modifier.bounceScale(interactionSource: MutableInteractionSource) = composed {
    val isPressed by interactionSource.collectIsPressedAsState()
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.95f else 1f,
        animationSpec = spring(
            dampingRatio = 0.5f,
            stiffness = 300f
        ),
        label = "scale_anim_main"
    )
    this.graphicsLayer {
        scaleX = scale
        scaleY = scale
    }
}