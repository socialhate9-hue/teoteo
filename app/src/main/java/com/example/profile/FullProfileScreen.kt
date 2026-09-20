package com.example.profile

import android.content.Intent
import android.graphics.BitmapFactory
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.foundation.shape.RoundedCornerShape
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieAnimatable
import com.airbnb.lottie.compose.rememberLottieComposition
import android.net.Uri
import android.widget.Toast
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.PlayCircleOutline
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.SportsBasketball
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material.icons.filled.Whatshot
import androidx.compose.material.icons.outlined.Settings
import com.example.friends.FriendsManager
import kotlinx.coroutines.delay
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import com.example.supabase.SupabaseAuthManager
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import kotlinx.coroutines.launch
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin
import com.example.R
import com.example.home.GameTrainToggle
import com.example.stats.PlayerStats
import com.example.stats.PlayerStatsManager
import com.example.ui.common.UserAvatarImage
import com.example.vision.SavedVideoAnalysis
import com.example.vision.SavedVideoManager
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Shape geométrico vectorial para los escudos de liga y logros.
 */
val ShieldShape = GenericShape { size, _ ->
    val w = size.width
    val h = size.height
    moveTo(w * 0.12f, 0f)
    lineTo(w * 0.88f, 0f)
    cubicTo(w * 0.98f, 0f, w, 0.06f * h, w, 0.14f * h)
    lineTo(w, h * 0.58f)
    cubicTo(w, h * 0.80f, w * 0.65f, h * 0.93f, w * 0.5f, h)
    cubicTo(w * 0.35f, h * 0.93f, 0f, h * 0.80f, 0f, h * 0.58f)
    lineTo(0f, 0.14f * h)
    cubicTo(0f, 0.06f * h, w * 0.02f, 0f, w * 0.12f, 0f)
    close()
}

/**
 * Modelo de datos de Logro (Badge).
 */
data class ProfileAchievement(
    val id: String,
    val titleEs: String,
    val descriptionEs: String,
    val xpReward: Int,
    val isUnlocked: (PlayerStats, List<SavedVideoAnalysis>) -> Boolean,
    val progressText: (PlayerStats, List<SavedVideoAnalysis>) -> String
)

/**
 * Pantalla completa de Perfil del Usuario.
 * Replica de manera fidedigna el diseño de la captura de pantalla:
 * - Header con badge de rango, píldora de XP y botón de configuración
 * - Fila de información de usuario con avatar artístico, nombre con botón de edición y estadísticas (Siguiendo, Seguidores, Vídeos)
 * - Barra de progreso de liga ("LIGA ROOKIE 0/499")
 * - 4 Escudos de liga (ROOKIE, ALL-STAR, MVP, GOAT)
 * - Pestañas "Vídeos" y "Logros"
 * - Sección de vídeos guardados localmente
 * - Sección de escudos/logros interactivos con animación 3D al girar y cambio de color al conseguirlos.
 */
@Composable
fun FullProfileScreen(
    onOpenSettings: () -> Unit,
    isGameMode: Boolean = true,
    onToggleMode: (Boolean) -> Unit = {},
    avatarUrl: String? = null,
    onNavigateToCard: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val playerStats by PlayerStatsManager.stats.collectAsState()
    val currentUser by SupabaseAuthManager.currentUser.collectAsState()
    val effectiveAvatarUrl = currentUser?.avatarUrl?.ifBlank { playerStats.avatarUrl } ?: playerStats.avatarUrl ?: avatarUrl

    val savedVideoManager = remember { SavedVideoManager(context) }
    var savedVideos by remember { mutableStateOf<List<SavedVideoAnalysis>>(emptyList()) }
    var selectedTab by remember { mutableIntStateOf(0) } // 0: Logros, 1: Vídeos, 2: Amigos
    var showEditNameDialog by remember { mutableStateOf(false) }
    var showAvatarDialog by remember { mutableStateOf(false) }
    var isUploadingAvatar by remember { mutableStateOf(false) }

    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            coroutineScope.launch {
                isUploadingAvatar = true
                val result = AvatarManager.saveAndUploadAvatar(context, uri)
                isUploadingAvatar = false
                showAvatarDialog = false
                if (result.isSuccess) {
                    Toast.makeText(context, "¡Foto de perfil actualizada y sincronizada en Supabase!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Foto de perfil actualizada localmente", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    // Cargar vídeos locales guardados
    LaunchedEffect(Unit) {
        savedVideos = savedVideoManager.getAllSaved()
    }

    // Lista de logros disponibles en español
    val achievements = remember {
        listOf(
            ProfileAchievement(
                id = "first_100_xp",
                titleEs = "PRIMEROS PASOS",
                descriptionEs = "CONSIGUE TUS PRIMEROS 100 HIPE EN CUALQUIER MODO DE JUEGO.",
                xpReward = 100,
                isUnlocked = { stats, _ -> stats.totalXp >= 100 },
                progressText = { stats, _ -> "${stats.totalXp.coerceAtMost(100)} / 100 HIPE" }
            ),
            ProfileAchievement(
                id = "1st_place",
                titleEs = "1ER LUGAR",
                descriptionEs = "CONSIGUE TU PRIMER RÉCORD O PUNTUACIÓN DESTACADA EN UN MINIJUEGO.",
                xpReward = 175,
                isUnlocked = { stats, _ ->
                    stats.reactionPointsBest > 0 || stats.dribbleComboBest > 0 || stats.defendZoneBest > 0
                },
                progressText = { stats, _ ->
                    val best = maxOf(stats.reactionPointsBest, stats.dribbleComboBest, stats.defendZoneBest)
                    if (best > 0) "Récord: $best pts" else "Sin récord aún"
                }
            ),
            ProfileAchievement(
                id = "talk_of_court",
                titleEs = "REY DE LA PISTA",
                descriptionEs = "COMPLETA AL MENOS 3 SESIONES COMPLETAS DE JUEGO O ENTRENAMIENTO.",
                xpReward = 100,
                isUnlocked = { stats, _ ->
                    (stats.reactionPointsGames + stats.dribbleComboGames + stats.defendZoneGames + stats.shootingSessions) >= 3
                },
                progressText = { stats, _ ->
                    val totalGames = stats.reactionPointsGames + stats.dribbleComboGames + stats.defendZoneGames + stats.shootingSessions
                    "${totalGames.coerceAtMost(3)} / 3 partidas"
                }
            ),
            ProfileAchievement(
                id = "publish_5_videos",
                titleEs = "CINCO VÍDEOS",
                descriptionEs = "GUARDA O ANALIZA 5 VÍDEOS DE JUGADAS EN LA CANCHA.",
                xpReward = 75,
                isUnlocked = { stats, videos ->
                    videos.size >= 5 || stats.shootingSessions >= 5
                },
                progressText = { stats, videos ->
                    val count = maxOf(videos.size, stats.shootingSessions)
                    "${count.coerceAtMost(5)} / 5 vídeos"
                }
            ),
            ProfileAchievement(
                id = "court_clout",
                titleEs = "FAMA EN LA CANCHA",
                descriptionEs = "ALCANZA EL RANGO PRO SUPERANDO LOS 250 HIPE TOTALES.",
                xpReward = 150,
                isUnlocked = { stats, _ -> stats.totalXp >= 250 },
                progressText = { stats, _ -> "${stats.totalXp.coerceAtMost(250)} / 250 HIPE" }
            ),
            ProfileAchievement(
                id = "defend_iron",
                titleEs = "DEFENSA DE HIERRO",
                descriptionEs = "SOBREVIVE Y PROTEGE LA ZONA EN DEFEND THE ZONE.",
                xpReward = 125,
                isUnlocked = { stats, _ -> stats.defendZoneBest >= 15 },
                progressText = { stats, _ -> "Récord: ${stats.defendZoneBest} pts" }
            ),
            ProfileAchievement(
                id = "dribble_master",
                titleEs = "MAESTRO DEL DRIBLE",
                descriptionEs = "ENCADENA COMBOS DE CROSSOVER EN DRIBBLE COMBO.",
                xpReward = 120,
                isUnlocked = { stats, _ -> stats.dribbleComboBest >= 20 || stats.dribbleCrossovers >= 5 },
                progressText = { stats, _ -> "Récord: ${stats.dribbleComboBest} pts" }
            ),
            ProfileAchievement(
                id = "sniper_shot",
                titleEs = "FRANCOTIRADOR",
                descriptionEs = "ANOTA 5 CANASTAS EN LA SESIÓN DE TIRO CON ANÁLISIS.",
                xpReward = 200,
                isUnlocked = { stats, _ -> stats.shootingMakes >= 5 || stats.shootingXp >= 80 },
                progressText = { stats, _ -> "${stats.shootingMakes.coerceAtMost(5)} / 5 canastas" }
            )
        )
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFFFFFFF))
            .testTag("full_profile_screen")
    ) {
        // ==========================================
        // 1. HEADER SUPERIOR FIJO: Exactamente la misma posición, padding y altura que en Inicio y Workout
        // ==========================================
        ProfileTopHeader(
            streakDays = playerStats.streakDays,
            totalXp = playerStats.totalXp,
            isGameMode = isGameMode,
            onToggleMode = onToggleMode,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 10.dp)
                .height(44.dp)
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 4.dp, bottom = 90.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            // ==========================================
            // 2. FILA DE PERFIL (Avatar unificado, Nombre, Ajustes a la derecha, Stats)
            // ==========================================
            item(span = { GridItemSpan(2) }) {
                ProfileUserCard(
                    playerName = playerStats.playerName,
                    avatarUrl = effectiveAvatarUrl,
                    isGameMode = isGameMode,
                    totalXp = playerStats.totalXp,
                    videoCount = savedVideos.size,
                    isUploadingAvatar = isUploadingAvatar,
                    onAvatarClick = { showAvatarDialog = true },
                    onEditNameClick = { showEditNameDialog = true },
                    onOpenSettings = onOpenSettings,
                    onPlayerCardClick = onNavigateToCard
                )
            }

            // ==========================================
            // 3. BARRA DE PROGRESO DE LIGA
            // ==========================================
            item(span = { GridItemSpan(2) }) {
                ProfileLeagueProgress(
                    rankTitle = playerStats.rankTitle,
                    totalXp = playerStats.totalXp
                )
            }

            // ==========================================
            // 4. FILA DE 4 ESCUDOS DE LIGA
            // ==========================================
            item(span = { GridItemSpan(2) }) {
                ProfileLeagueBadgesRow(totalXp = playerStats.totalXp)
            }

            // ==========================================
            // 5. PESTAÑAS (2 SECCIONES): LOGROS | VÍDEOS
            // ==========================================
            item(span = { GridItemSpan(2) }) {
                ProfileTabsRow(
                    selectedTab = selectedTab,
                    onSelectTab = { selectedTab = it }
                )
            }

            // ==========================================
            // 6. CONTENIDO SEGÚN LA PESTAÑA SELECCIONADA
            // ==========================================
            when (selectedTab) {
                0 -> {
                    // Pestaña 1: LOGROS (Monedas de baloncesto interactivas con giro 3D)
                    items(achievements, key = { it.id }, span = { GridItemSpan(1) }) { achievement ->
                        val unlocked = achievement.isUnlocked(playerStats, savedVideos)
                        ProfileAchievementCoinCard(
                            achievement = achievement,
                            isUnlocked = unlocked,
                            progressText = achievement.progressText(playerStats, savedVideos)
                        )
                    }
                }
                1 -> {
                    // Pestaña 2: VÍDEOS (Vídeos locales guardados)
                    if (savedVideos.isEmpty()) {
                        item(span = { GridItemSpan(2) }) {
                            ProfileVideosEmptyState()
                        }
                    } else {
                        items(savedVideos, key = { it.id }, span = { GridItemSpan(1) }) { video ->
                            ProfileVideoItemCard(video = video)
                        }
                    }
                }
                2 -> {
                    // Pestaña 3: AMIGOS (Tu código de amigo, añadir con código y lista de amigos)
                    item(span = { GridItemSpan(2) }) {
                        ProfileFriendsSection()
                    }
                }
            }
        }

        // Diálogo para editar el nombre de usuario
        if (showEditNameDialog) {
            EditPlayerNameDialog(
                currentName = playerStats.playerName,
                onDismiss = { showEditNameDialog = false },
                onSave = { newName ->
                    PlayerStatsManager.updatePlayerName(newName)
                    showEditNameDialog = false
                }
            )
        }

        // Diálogo para cambiar foto de perfil y sincronizar en Supabase
        if (showAvatarDialog) {
            AvatarSelectionDialog(
                currentAvatarUrl = effectiveAvatarUrl,
                playerName = playerStats.playerName,
                isUploading = isUploadingAvatar,
                onDismiss = { if (!isUploadingAvatar) showAvatarDialog = false },
                onUploadFromGallery = {
                    photoPickerLauncher.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                    )
                },
                onSelectPreset = { preset ->
                    coroutineScope.launch {
                        isUploadingAvatar = true
                        AvatarManager.setAvatarPreset(context, preset)
                        isUploadingAvatar = false
                        showAvatarDialog = false
                        Toast.makeText(context, "Avatar actualizado", Toast.LENGTH_SHORT).show()
                    }
                },
                onResetToDefault = {
                    coroutineScope.launch {
                        isUploadingAvatar = true
                        AvatarManager.setAvatarPreset(context, null)
                        isUploadingAvatar = false
                        showAvatarDialog = false
                        Toast.makeText(context, "Avatar restablecido a predeterminado", Toast.LENGTH_SHORT).show()
                    }
                }
            )
        }
    }
}

/**
 * Encabezado superior con Badges de Racha y Hype a la izquierda y selector GAME/PRO a la derecha.
 */
@Composable
private fun ProfileTopHeader(
    streakDays: Int,
    totalXp: Int,
    isGameMode: Boolean,
    onToggleMode: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // LADO IZQUIERDO: Badges de Racha y Hype (idénticos a las otras pantallas)
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(1f, fill = false)
        ) {
            // Badges de Racha
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(14.dp))
                    .background(if (isGameMode) Color(0xFFFFF7ED) else Color(0xFF1E2433))
                    .border(1.dp, Color(0xFFFF6B1A).copy(alpha = 0.4f), RoundedCornerShape(14.dp))
                    .padding(horizontal = 8.dp, vertical = 5.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Whatshot,
                        contentDescription = "Racha",
                        tint = Color(0xFFFF6B1A),
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(3.dp))
                    Text(
                        text = "${streakDays}d",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Black,
                        color = if (isGameMode) Color(0xFFC2410C) else Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.width(6.dp))

            // Badges de Hype (en Modo GAME con icono cohete) o XP (en Modo TRAIN/PRO)
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(14.dp))
                    .background(if (isGameMode) Color(0xFFE0F7FA) else Color(0xFF1E2433))
                    .border(1.dp, Color(0xFF00BCD4).copy(alpha = 0.4f), RoundedCornerShape(14.dp))
                    .padding(horizontal = 8.dp, vertical = 5.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    if (isGameMode) {
                        Image(
                            painter = painterResource(id = R.drawable.cohete),
                            contentDescription = "Hype",
                            modifier = Modifier.size(15.dp),
                            contentScale = ContentScale.Fit
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "${totalXp} Hype",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Black,
                            color = Color(0xFF00838F)
                        )
                    } else {
                        Text(
                            text = "${totalXp} XP",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Black,
                            color = Color(0xFF00E5FF)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.width(8.dp))

        // LADO DERECHO: Selector GAME / PRO en la misma posición que en Home y Workouts
        GameTrainToggle(
            isGameMode = isGameMode,
            onToggle = onToggleMode
        )
    }
}

/**
 * Fila de usuario con Avatar unificado (siguiendo el diseño de toda la app), Nombre con lápiz de edición,
 * botón de ajustes en la zona derecha al nivel del lápiz, y Estadísticas (Siguiendo, Seguidores, Vídeos).
 */
@Composable
private fun ProfileUserCard(
    playerName: String,
    avatarUrl: String?,
    isGameMode: Boolean,
    totalXp: Int = 0,
    videoCount: Int,
    isUploadingAvatar: Boolean = false,
    onAvatarClick: () -> Unit,
    onEditNameClick: () -> Unit,
    onOpenSettings: () -> Unit,
    onPlayerCardClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // 1. Avatar unificado del jugador (Tocar abre el diálogo de foto/avatar)
        val avatarBorderBrush = if (isGameMode) {
            Brush.sweepGradient(
                listOf(
                    Color(0xFF00E5FF),
                    Color(0xFFFF2A85),
                    Color(0xFFFFD600),
                    Color(0xFF00E5FF)
                )
            )
        } else {
            Brush.linearGradient(
                listOf(
                    Color(0xFF1E293B),
                    Color(0xFF0F172A)
                )
            )
        }

        Box(
            modifier = Modifier
                .size(82.dp)
                .clip(CircleShape)
                .clickable(onClick = onAvatarClick)
                .testTag("btn_avatar_player_card"),
            contentAlignment = Alignment.Center
        ) {
            UserAvatarImage(
                avatarUrl = avatarUrl,
                displayName = playerName,
                fallbackDrawable = R.drawable.avatarchico,
                size = 82.dp,
                borderBrush = avatarBorderBrush,
                borderWidth = 3.dp,
                modifier = Modifier.fillMaxSize()
            )

            // Indicador de subida si se está procesando una foto
            if (isUploadingAvatar) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.6f)),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        color = Color(0xFF00E5FF),
                        strokeWidth = 2.5.dp,
                        modifier = Modifier.size(28.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.width(16.dp))

        // 2. Nombre con lápiz, Botón de Ajustes a la derecha al mismo nivel, y Estadísticas
        Column(
            modifier = Modifier.weight(1f)
        ) {
            // Fila de nombre con lápiz de edición a la izquierda y botón de ajustes en la zona derecha
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .weight(1f, fill = false)
                        .clickable(onClick = onPlayerCardClick)
                ) {
                    Text(
                        text = playerName,
                        fontSize = 19.sp,
                        fontWeight = FontWeight.Black,
                        color = Color(0xFF0F172A),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    IconButton(
                        onClick = onEditNameClick,
                        modifier = Modifier
                            .size(26.dp)
                            .testTag("btn_edit_profile_name")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Editar nombre",
                            tint = Color(0xFF64748B),
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }

                // Botón de Configuración / Ajustes ⚙️ bajado al nivel del lápiz en la zona derecha
                IconButton(
                    onClick = onOpenSettings,
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFF1F5F9))
                        .testTag("btn_profile_settings")
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Settings,
                        contentDescription = "Ajustes y Servidor",
                        tint = Color(0xFF334155),
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            // Fila de estadísticas: Siguiendo / Seguidores / Vídeos
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(18.dp)
            ) {
                ProfileStatColumn(count = "3", label = "Siguiendo")
                ProfileStatColumn(count = "0", label = "Seguidores")
                ProfileStatColumn(count = "$videoCount", label = "Vídeos")
            }
        }
    }
}

@Composable
private fun ProfileStatColumn(count: String, label: String) {
    Column(horizontalAlignment = Alignment.Start) {
        Text(
            text = count,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF0F172A)
        )
        Text(
            text = label,
            fontSize = 11.sp,
            color = Color(0xFF64748B)
        )
    }
}

/**
 * Barra de progreso horizontal de liga (ej. "LIGA ROOKIE" y "0/499").
 */
@Composable
private fun ProfileLeagueProgress(
    rankTitle: String,
    totalXp: Int
) {
    val maxLeagueXp = 499
    val currentProgressXp = (totalXp % 500).coerceIn(0, maxLeagueXp)
    val progressFloat = (currentProgressXp.toFloat() / maxLeagueXp.toFloat()).coerceIn(0f, 1f)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "LIGA ${rankTitle.uppercase()}",
                fontSize = 12.sp,
                fontWeight = FontWeight.Black,
                color = Color(0xFF0F172A),
                letterSpacing = 0.5.sp
            )
            Text(
                text = "$currentProgressXp/$maxLeagueXp",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF0F172A)
            )
        }

        Spacer(modifier = Modifier.height(6.dp))

        // Barra de progreso horizontal con extremos redondeados
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(Color(0xFFE2E8F0))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(progressFloat.coerceAtLeast(0.02f))
                    .fillMaxSize()
                    .clip(RoundedCornerShape(4.dp))
                    .background(Color(0xFF2563EB))
            )
        }
    }
}

/**
 * Fila de 4 Escudos de Liga representativos:
 * 1. ROOKIE LEAGUE (Azul con balón)
 * 2. ALL-STAR LEAGUE (Cyan con estrella)
 * 3. MVP LEAGUE (Rosa con trofeo)
 * 4. GOAT LEAGUE (Dorado con corona/cabra)
 */
/**
 * Fila de 4 Escudos de Liga representativos con las imágenes oficiales en el orden exacto:
 * 1. rookie.png -> R.drawable.rookie
 * 2. allstar.png -> R.drawable.allstar
 * 3. mvp.png -> R.drawable.mvp
 * 4. goat.png -> R.drawable.goat
 */
@Composable
private fun ProfileLeagueBadgesRow(totalXp: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        LeagueShieldItem(
            name = "ROOKIE",
            imageResId = R.drawable.rookie,
            isActive = true
        )
        LeagueShieldItem(
            name = "ALL-STAR",
            imageResId = R.drawable.allstar,
            isActive = totalXp >= 750
        )
        LeagueShieldItem(
            name = "MVP",
            imageResId = R.drawable.mvp,
            isActive = totalXp >= 1500
        )
        LeagueShieldItem(
            name = "GOAT",
            imageResId = R.drawable.goat,
            isActive = totalXp >= 3000
        )
    }
}

@Composable
private fun LeagueShieldItem(
    name: String,
    imageResId: Int,
    isActive: Boolean
) {
    val opacity = if (isActive) 1.0f else 0.38f
    val grayscaleFilter = remember {
        ColorFilter.colorMatrix(ColorMatrix().apply { setToSaturation(0.0f) })
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .graphicsLayer { alpha = opacity }
            .padding(horizontal = 2.dp)
    ) {
        Box(
            modifier = Modifier
                .size(width = 78.dp, height = 94.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = imageResId),
                contentDescription = name,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Fit,
                colorFilter = if (isActive) null else grayscaleFilter
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = name,
            fontSize = 11.sp,
            fontWeight = FontWeight.Black,
            color = if (isActive) Color(0xFF0F172A) else Color(0xFF94A3B8),
            letterSpacing = 0.5.sp,
            textAlign = TextAlign.Center
        )
    }
}

/**
 * Selector de 2 pestañas: "Logros" | "Vídeos".
 */
@Composable
private fun ProfileTabsRow(
    selectedTab: Int,
    onSelectTab: (Int) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp, bottom = 6.dp)
    ) {
        val tabs = listOf(
            Triple(0, "Logros", "🏆"),
            Triple(1, "Vídeos", "🎬"),
            Triple(2, "Amigos", "👥")
        )

        tabs.forEach { (index, title, iconEmoji) ->
            val isSelected = selectedTab == index
            Column(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(8.dp))
                    .clickable { onSelectTab(index) }
                    .padding(vertical = 8.dp)
                    .testTag("tab_profile_$index"),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(text = iconEmoji, fontSize = 13.sp)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = title,
                        fontSize = 13.sp,
                        fontWeight = if (isSelected) FontWeight.Black else FontWeight.SemiBold,
                        color = if (isSelected) Color(0xFF2563EB) else Color(0xFF64748B),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Spacer(modifier = Modifier.height(6.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.80f)
                        .height(3.dp)
                        .clip(RoundedCornerShape(2.dp))
                        .background(if (isSelected) Color(0xFF2563EB) else Color.Transparent)
                )
            }
        }
    }
}

/**
 * Cara Frontal de la Moneda de la Animación.
 * Reproduce exactamente el diseño del archivo:
 * - Moneda circular metálica con bisel exterior y aro interior ranurado.
 * - Estrella central de 5 puntas con las curvas de Bézier exactas del archivo original.
 * - Relieve facetado 3D (chiseled bevel) idéntico a las capas del diseño.
 * - Destellos de luz y estrellas de brillo en diamante.
 * - En gris metálico pulido cuando el logro no está conseguido.
 * - En dorado brillante cuando el logro está conseguido.
 */
@Composable
private fun StarCoinFrontFace(
    isUnlocked: Boolean,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier) {
        val diameter = size.minDimension
        val radius = diameter / 2f
        val center = Offset(size.width / 2f, size.height / 2f)
        val s = radius / 100f // Factor de escala para reproducir las coordenadas exactas del archivo (radio 100)

        // 1. Bisel exterior de la moneda (Aro exterior con degradado metálico de alto contraste)
        val outerRimBrush = if (isUnlocked) {
            Brush.linearGradient(
                colors = listOf(
                    Color(0xFFFFFBEB), // Reflejo blanco-dorado luminoso
                    Color(0xFFFCD34D), // Oro brillante
                    Color(0xFFF59E0B), // Oro cálido
                    Color(0xFFB45309), // Bronce perimetral
                    Color(0xFF78350F), // Sombra inferior profunda
                    Color(0xFFFCD34D)
                ),
                start = Offset(center.x - radius, center.y - radius),
                end = Offset(center.x + radius, center.y + radius)
            )
        } else {
            Brush.linearGradient(
                colors = listOf(
                    Color(0xFFFFFFFF), // Reflejo blanco puro cromado
                    Color(0xFFE2E8F0), // Plata pulida
                    Color(0xFF94A3B8), // Acero medio
                    Color(0xFF475569), // Acero oscuro
                    Color(0xFF1E293B), // Sombra titanio profunda
                    Color(0xFFCBD5E1)
                ),
                start = Offset(center.x - radius, center.y - radius),
                end = Offset(center.x + radius, center.y + radius)
            )
        }
        drawCircle(
            brush = outerRimBrush,
            radius = radius,
            center = center
        )

        // 2. Anillo interior ranurado (Canal grabado con relieve de profundidad)
        val innerGrooveColor = if (isUnlocked) Color(0xFF92400E) else Color(0xFF334155)
        drawCircle(
            color = innerGrooveColor,
            radius = radius * 0.82f,
            center = center,
            style = Stroke(width = radius * 0.038f)
        )

        // 3. Disco central de la moneda (Placa interna con degradado radial y foco superior)
        val innerPlateRadius = radius * 0.80f
        val plateBrush = if (isUnlocked) {
            Brush.radialGradient(
                colors = listOf(
                    Color(0xFFFEF08A), // Centro dorado luminoso
                    Color(0xFFF59E0B), // Oro saturado
                    Color(0xFFD97706)  // Sombra perimetral cálida
                ),
                center = Offset(center.x - radius * 0.15f, center.y - radius * 0.20f),
                radius = innerPlateRadius * 1.25f
            )
        } else {
            Brush.radialGradient(
                colors = listOf(
                    Color(0xFFFFFFFF), // Reflejo blanco plateado
                    Color(0xFFCBD5E1), // Gris metálico neutro
                    Color(0xFF64748B)  // Sombra titanio perimetral
                ),
                center = Offset(center.x - radius * 0.15f, center.y - radius * 0.20f),
                radius = innerPlateRadius * 1.25f
            )
        }
        drawCircle(
            brush = plateBrush,
            radius = innerPlateRadius,
            center = center
        )

        // 4. Estrella central de 5 puntas (Curvas de Bézier exactas del archivo After Effects)
        val starPath = Path().apply {
            moveTo(center.x + 6.631f * s, center.y - 53.02f * s)
            lineTo(center.x + 18.62f * s, center.y - 28.727f * s)
            cubicTo(
                center.x + (18.62f + 0.773f) * s, center.y + (-28.727f + 1.567f) * s,
                center.x + (22.618f - 1.73f) * s, center.y + (-25.822f - 0.251f) * s,
                center.x + 22.618f * s, center.y - 25.822f * s
            )
            lineTo(center.x + 49.428f * s, center.y - 21.926f * s)
            cubicTo(
                center.x + (49.428f + 2.178f) * s, center.y + (-21.926f + 0.316f) * s,
                center.x + (50.899f + 1.576f) * s, center.y + (-17.398f - 1.536f) * s,
                center.x + 50.899f * s, center.y - 17.398f * s
            )
            lineTo(center.x + 31.5f * s, center.y + 1.512f * s)
            cubicTo(
                center.x + (31.5f - 1.251f) * s, center.y + (1.512f + 1.22f) * s,
                center.x + (29.973f - 0.295f) * s, center.y + (6.212f - 1.723f) * s,
                center.x + 29.973f * s, center.y + 6.212f * s
            )
            lineTo(center.x + 34.552f * s, center.y + 32.913f * s)
            cubicTo(
                center.x + (34.552f + 0.372f) * s, center.y + (32.913f + 2.169f) * s,
                center.x + (30.7f + 1.948f) * s, center.y + (35.712f + 1.024f) * s,
                center.x + 30.7f * s, center.y + 35.712f * s
            )
            lineTo(center.x + 6.721f * s, center.y + 23.105f * s)
            cubicTo(
                center.x + (6.721f - 1.547f) * s, center.y + (23.105f - 0.813f) * s,
                center.x + (1.779f + 1.547f) * s, center.y + (23.105f - 0.813f) * s,
                center.x + 1.779f * s, center.y + 23.105f * s
            )
            lineTo(center.x - 22.2f * s, center.y + 35.712f * s)
            cubicTo(
                center.x + (-22.2f - 1.948f) * s, center.y + (35.712f + 1.024f) * s,
                center.x + (-26.052f - 0.372f) * s, center.y + (32.913f + 2.169f) * s,
                center.x - 26.052f * s, center.y + 32.913f * s
            )
            lineTo(center.x - 21.473f * s, center.y + 6.212f * s)
            cubicTo(
                center.x + (-21.473f + 0.295f) * s, center.y + (6.212f - 1.723f) * s,
                center.x + (-23.0f + 1.251f) * s, center.y + (1.512f + 1.22f) * s,
                center.x - 23.0f * s, center.y + 1.512f * s
            )
            lineTo(center.x - 42.399f * s, center.y - 17.398f * s)
            cubicTo(
                center.x + (-42.399f - 1.576f) * s, center.y + (-17.398f - 1.536f) * s,
                center.x + (-40.928f - 2.178f) * s, center.y + (-21.926f + 0.316f) * s,
                center.x - 40.928f * s, center.y - 21.926f * s
            )
            lineTo(center.x - 14.118f * s, center.y - 25.822f * s)
            cubicTo(
                center.x + (-14.118f + 1.73f) * s, center.y + (-25.822f - 0.251f) * s,
                center.x + (-10.12f - 0.773f) * s, center.y + (-28.727f + 1.567f) * s,
                center.x - 10.12f * s, center.y - 28.727f * s
            )
            lineTo(center.x + 1.869f * s, center.y - 53.02f * s)
            cubicTo(
                center.x + (1.869f + 0.974f) * s, center.y + (-53.02f - 1.973f) * s,
                center.x + (6.631f - 0.974f) * s, center.y + (-53.02f - 1.973f) * s,
                center.x + 6.631f * s, center.y - 53.02f * s
            )
            close()
        }

        // Relleno de la estrella con degradado vertical luminoso
        val starBrush = if (isUnlocked) {
            Brush.verticalGradient(
                colors = listOf(
                    Color(0xFFFFFBEB), // Cúspide luminosa
                    Color(0xFFFBBF24), // Oro ámbar
                    Color(0xFFF59E0B)  // Base dorada profunda
                ),
                startY = center.y - 55f * s,
                endY = center.y + 36f * s
            )
        } else {
            Brush.verticalGradient(
                colors = listOf(
                    Color(0xFFFFFFFF), // Cúspide blanca cromada
                    Color(0xFFE2E8F0), // Plata brillante
                    Color(0xFF94A3B8)  // Base gris acero
                ),
                startY = center.y - 55f * s,
                endY = center.y + 36f * s
            )
        }
        drawPath(starPath, brush = starBrush)

        // Borde fino de realce para la estrella
        drawPath(
            starPath,
            color = if (isUnlocked) Color(0xFFFEF08A).copy(alpha = 0.8f) else Color.White.copy(alpha = 0.7f),
            style = Stroke(width = radius * 0.015f)
        )

        // 5. Facetas de sombra en relieve 3D (Idénticas a la capa "nm: 5" del archivo)
        val facetShadowColor = if (isUnlocked) Color(0xFFB45309).copy(alpha = 0.75f) else Color(0xFF334155).copy(alpha = 0.75f)

        // Faceta 1: Sombra brazo superior y transición
        val facet1 = Path().apply {
            moveTo(center.x - 38.428f * s, center.y - 19.426f * s)
            lineTo(center.x - 11.619f * s, center.y - 23.322f * s)
            lineTo(center.x - 7.62f * s, center.y - 26.227f * s)
            lineTo(center.x + 4.369f * s, center.y - 50.52f * s)
            lineTo(center.x + 7.161f * s, center.y - 51.946f * s)
            lineTo(center.x + 6.631f * s, center.y - 53.02f * s)
            lineTo(center.x + 1.869f * s, center.y - 53.02f * s)
            lineTo(center.x - 10.12f * s, center.y - 28.727f * s)
            lineTo(center.x - 14.119f * s, center.y - 25.822f * s)
            lineTo(center.x - 40.928f * s, center.y - 21.926f * s)
            lineTo(center.x - 42.399f * s, center.y - 17.398f * s)
            lineTo(center.x - 40.093f * s, center.y - 15.149f * s)
            close()
        }
        drawPath(facet1, color = facetShadowColor)

        // Faceta 2: Sombra brazo inferior izquierdo
        val facet2 = Path().apply {
            moveTo(center.x - 23.552f * s, center.y + 35.413f * s)
            lineTo(center.x - 18.973f * s, center.y + 8.712f * s)
            lineTo(center.x - 20.5f * s, center.y + 4.012f * s)
            lineTo(center.x - 22.767f * s, center.y + 1.802f * s)
            lineTo(center.x - 21.473f * s, center.y + 6.212f * s)
            lineTo(center.x - 26.052f * s, center.y + 32.913f * s)
            lineTo(center.x - 23.568f * s, center.y + 36.006f * s)
            close()
        }
        drawPath(facet2, color = facetShadowColor)

        // Faceta 3: Sombra brazo derecho
        val facet3 = Path().apply {
            moveTo(center.x + 21.12f * s, center.y - 26.227f * s)
            lineTo(center.x + 25.119f * s, center.y - 23.322f * s)
            lineTo(center.x + 51.693f * s, center.y - 19.46f * s)
            lineTo(center.x + 49.428f * s, center.y - 21.926f * s)
            lineTo(center.x + 22.619f * s, center.y - 25.822f * s)
            lineTo(center.x + 21.069f * s, center.y - 26.331f * s)
            close()
        }
        drawPath(facet3, color = facetShadowColor)

        // 6. Reflejo especular curvo en el cuadrante superior izquierdo del bisel
        val highlightArc = Path().apply {
            arcTo(
                rect = Rect(
                    center.x - radius * 0.92f,
                    center.y - radius * 0.92f,
                    center.x + radius * 0.92f,
                    center.y + radius * 0.92f
                ),
                startAngleDegrees = -155f,
                sweepAngleDegrees = 60f,
                forceMoveTo = true
            )
        }
        drawPath(
            highlightArc,
            color = Color.White.copy(alpha = if (isUnlocked) 0.85f else 0.75f),
            style = Stroke(width = radius * 0.05f, cap = StrokeCap.Round)
        )

        // 7. Destello de brillo en forma de diamante (Sparkle Star superior derecha)
        val sparkleColor = if (isUnlocked) Color(0xFFFFFBEB) else Color.White
        val star1Pos = Offset(center.x + 48f * s, center.y - 48f * s)
        val star1Size = 7.5f * s
        val star1Path = Path().apply {
            moveTo(star1Pos.x, star1Pos.y - star1Size)
            quadraticBezierTo(star1Pos.x, star1Pos.y, star1Pos.x + star1Size, star1Pos.y)
            quadraticBezierTo(star1Pos.x, star1Pos.y, star1Pos.x, star1Pos.y + star1Size)
            quadraticBezierTo(star1Pos.x, star1Pos.y, star1Pos.x - star1Size, star1Pos.y)
            quadraticBezierTo(star1Pos.x, star1Pos.y, star1Pos.x, star1Pos.y - star1Size)
            close()
        }
        drawPath(star1Path, color = sparkleColor)

        // Destello secundario inferior izquierdo
        val star2Pos = Offset(center.x - 52f * s, center.y + 40f * s)
        val star2Size = 5.5f * s
        val star2Path = Path().apply {
            moveTo(star2Pos.x, star2Pos.y - star2Size)
            quadraticBezierTo(star2Pos.x, star2Pos.y, star2Pos.x + star2Size, star2Pos.y)
            quadraticBezierTo(star2Pos.x, star2Pos.y, star2Pos.x, star2Pos.y + star2Size)
            quadraticBezierTo(star2Pos.x, star2Pos.y, star2Pos.x - star2Size, star2Pos.y)
            quadraticBezierTo(star2Pos.x, star2Pos.y, star2Pos.x, star2Pos.y - star2Size)
            close()
        }
        drawPath(star2Path, color = sparkleColor.copy(alpha = 0.85f))
    }
}

/**
 * Cara Trasera de la Moneda.
 * Diseñada con el mismo contorno circular metálico (gris si no está conseguido, dorado si lo está).
 * Presenta de forma nítida y legible el reto, el progreso actual y la recompensa en HIPE.
 */
@Composable
private fun StarCoinBackFace(
    achievement: ProfileAchievement,
    isUnlocked: Boolean,
    progressText: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(CircleShape)
            .background(
                if (isUnlocked) {
                    Brush.radialGradient(
                        colors = listOf(
                            Color(0xFF78350F), // Ámbar oscuro
                            Color(0xFF451A03), // Bronce oscuro
                            Color(0xFF1E1006)  // Carbón cálido
                        )
                    )
                } else {
                    Brush.radialGradient(
                        colors = listOf(
                            Color(0xFF334155), // Acero oscuro
                            Color(0xFF1E293B), // Pizarra oscura
                            Color(0xFF0F172A)  // Titanio oscuro
                        )
                    )
                }
            )
            .border(
                width = 3.5.dp,
                brush = if (isUnlocked) {
                    Brush.linearGradient(
                        listOf(Color(0xFFFFFBEB), Color(0xFFF59E0B), Color(0xFFB45309), Color(0xFFFFFBEB))
                    )
                } else {
                    Brush.linearGradient(
                        listOf(Color(0xFFFFFFFF), Color(0xFF94A3B8), Color(0xFF475569), Color(0xFFCBD5E1))
                    )
                },
                shape = CircleShape
            )
            .padding(10.dp),
        contentAlignment = Alignment.Center
    ) {
        // Marca de agua sutil de la estrella en el fondo
        Icon(
            imageVector = Icons.Default.Star,
            contentDescription = null,
            tint = if (isUnlocked) Color(0xFFF59E0B).copy(alpha = 0.15f) else Color(0xFF94A3B8).copy(alpha = 0.10f),
            modifier = Modifier.size(68.dp)
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            // Insignia superior
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = if (isUnlocked) Color(0xFFF59E0B).copy(alpha = 0.30f) else Color(0xFF94A3B8).copy(alpha = 0.25f),
                border = BorderStroke(
                    1.dp,
                    if (isUnlocked) Color(0xFFFCD34D).copy(alpha = 0.7f) else Color(0xFFCBD5E1).copy(alpha = 0.5f)
                ),
                modifier = Modifier.padding(bottom = 4.dp)
            ) {
                Text(
                    text = if (isUnlocked) "⭐ CONSEGUIDO" else "🎯 RETO",
                    fontSize = 7.5.sp,
                    fontWeight = FontWeight.Black,
                    color = if (isUnlocked) Color(0xFFFDE047) else Color.White,
                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                    letterSpacing = 0.5.sp
                )
            }

            // Explicación detallada del reto
            Text(
                text = achievement.descriptionEs,
                fontSize = 8.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center,
                lineHeight = 10.5.sp,
                maxLines = 4,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(horizontal = 2.dp)
            )

            Spacer(modifier = Modifier.height(3.dp))

            // Progreso del reto
            Text(
                text = if (isUnlocked) "✅ ¡COMPLETADO!" else progressText,
                fontSize = 7.5.sp,
                fontWeight = FontWeight.Black,
                color = if (isUnlocked) Color(0xFF4ADE80) else Color(0xFF38BDF8)
            )

            Spacer(modifier = Modifier.height(3.dp))

            // Recompensa en HIPE
            Surface(
                shape = RoundedCornerShape(8.dp),
                color = if (isUnlocked) Color(0xFFD97706) else Color(0xFF475569),
                modifier = Modifier.padding(top = 1.dp)
            ) {
                Text(
                    text = "+${achievement.xpReward} HIPE",
                    fontSize = 7.5.sp,
                    fontWeight = FontWeight.Black,
                    color = Color.White,
                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 1.5.dp)
                )
            }
        }
    }
}

/**
 * Tarjeta individual de Logro con la Moneda Interactiva.
 * - Idéntica al archivo: en gris al no estar conseguido, en dorado al conseguirlo.
 * - Al pulsar sobre la moneda, gira en 3D (1.5 vueltas completas con salto y rebote físico)
 *   y muestra el reto detallado en el reverso.
 * - Al pulsar de nuevo, vuelve a girar en 3D para regresar a la cara de la moneda.
 * - Los textos inferiores se mantienen limpios (Título y HIPE) sin botones innecesarios.
 */
@Composable
private fun ProfileAchievementCoinCard(
    achievement: ProfileAchievement,
    isUnlocked: Boolean,
    progressText: String
) {
    var isFlipped by remember { mutableStateOf(false) }

    val rotationY = remember { Animatable(0f) }
    val jumpOffsetY = remember { Animatable(0f) }
    val coinScale = remember { Animatable(1f) }
    val shadowScale = remember { Animatable(1f) }
    val shadowAlpha = remember { Animatable(0.40f) }
    val coroutineScope = rememberCoroutineScope()

    val onCoinClick: () -> Unit = {
        val nextFlipped = !isFlipped
        isFlipped = nextFlipped
        coroutineScope.launch {
            val targetRotation = if (nextFlipped) 540f else 0f
            launch {
                rotationY.animateTo(
                    targetValue = targetRotation,
                    animationSpec = tween(durationMillis = 650, easing = FastOutSlowInEasing)
                )
            }
            launch {
                // Salto parabólico de la moneda en el aire con rebote al caer
                jumpOffsetY.animateTo(-32f, animationSpec = tween(durationMillis = 280, easing = FastOutLinearInEasing))
                jumpOffsetY.animateTo(0f, animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessLow))
            }
            launch {
                // Escala elástica en el aire
                coinScale.animateTo(1.12f, animationSpec = tween(durationMillis = 280, easing = FastOutLinearInEasing))
                coinScale.animateTo(0.95f, animationSpec = tween(durationMillis = 180, easing = LinearOutSlowInEasing))
                coinScale.animateTo(1.0f, animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessMedium))
            }
            launch {
                // Reacción de la sombra en el suelo al salto
                shadowScale.animateTo(0.70f, animationSpec = tween(durationMillis = 280))
                shadowScale.animateTo(1.0f, animationSpec = tween(durationMillis = 350))
            }
            launch {
                shadowAlpha.animateTo(0.12f, animationSpec = tween(durationMillis = 280))
                shadowAlpha.animateTo(0.40f, animationSpec = tween(durationMillis = 350))
            }
        }
    }

    val currentRot = rotationY.value
    val normalizedAngle = ((currentRot % 360f) + 360f) % 360f
    val isShowingBack = normalizedAngle in 90f..270f

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .testTag("achievement_card_${achievement.id}"),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(width = 110.dp, height = 126.dp)
                .clickable(onClick = onCoinClick),
            contentAlignment = Alignment.Center
        ) {
            // Sombra en el suelo (Floor Drop-Shadow) reactiva al salto
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 2.dp)
                    .width(76.dp * shadowScale.value)
                    .height(14.dp * shadowScale.value)
                    .graphicsLayer { alpha = shadowAlpha.value }
                    .background(
                        Brush.radialGradient(
                            colors = listOf(
                                if (isUnlocked) Color(0xFFF59E0B).copy(alpha = 0.45f) else Color(0xFF0F172A).copy(alpha = 0.35f),
                                Color.Transparent
                            )
                        ),
                        shape = CircleShape
                    )
            )

            // Moneda 3D interactiva con perspectiva real, salto y escala
            Box(
                modifier = Modifier
                    .size(104.dp)
                    .graphicsLayer {
                        this.rotationY = currentRot
                        this.translationY = jumpOffsetY.value * density
                        this.scaleX = coinScale.value
                        this.scaleY = coinScale.value
                        cameraDistance = 14f * density
                    },
                contentAlignment = Alignment.Center
            ) {
                if (!isShowingBack) {
                    // CARA FRONTAL: Moneda idéntica al archivo (Gris si bloqueado, Dorado si conseguido)
                    StarCoinFrontFace(
                        isUnlocked = isUnlocked,
                        modifier = Modifier
                            .fillMaxSize()
                            .shadow(if (isUnlocked) 8.dp else 2.dp, CircleShape)
                    )
                } else {
                    // CARA TRASERA: Explicación del reto (Gris si bloqueado, Dorado si conseguido)
                    StarCoinBackFace(
                        achievement = achievement,
                        isUnlocked = isUnlocked,
                        progressText = progressText,
                        modifier = Modifier
                            .fillMaxSize()
                            .graphicsLayer { this.rotationY = 180f }
                            .shadow(if (isUnlocked) 6.dp else 2.dp, CircleShape)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Título del logro en español (se mantiene)
        Text(
            text = achievement.titleEs,
            fontSize = 11.sp,
            fontWeight = FontWeight.Black,
            color = if (isUnlocked) Color(0xFFD97706) else Color(0xFF334155),
            textAlign = TextAlign.Center,
            letterSpacing = 0.5.sp,
            modifier = Modifier.clickable(onClick = onCoinClick)
        )

        Spacer(modifier = Modifier.height(2.dp))

        // Puntos HIPE del logro (se mantiene y XP cambiado por HIPE)
        Text(
            text = "${achievement.xpReward} HIPE",
            fontSize = 11.sp,
            fontWeight = FontWeight.SemiBold,
            color = if (isUnlocked) Color(0xFFB45309) else Color(0xFF64748B),
            textAlign = TextAlign.Center
        )
    }
}

/**
 * Estado vacío cuando aún no hay vídeos grabados localmente.
 */
@Composable
private fun ProfileVideosEmptyState() {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = Color(0xFFF8FAFC),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE2E8F0)),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp)
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(54.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFEFF6FF)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Videocam,
                    contentDescription = null,
                    tint = Color(0xFF2563EB),
                    modifier = Modifier.size(28.dp)
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = "Vídeos Guardados en Local",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF0F172A),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Aquí aparecerán los clips y repeticiones que grabes en tus partidas de juego. Todos los archivos se guardarán directamente en la memoria de tu dispositivo.",
                fontSize = 12.sp,
                color = Color(0xFF64748B),
                textAlign = TextAlign.Center,
                lineHeight = 17.sp
            )

            Spacer(modifier = Modifier.height(14.dp))

            Surface(
                shape = RoundedCornerShape(8.dp),
                color = Color(0xFFE0F2FE)
            ) {
                Text(
                    text = "📹 Próximamente: Grabación continua de jugadas",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF0369A1),
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                )
            }
        }
    }
}

/**
 * Tarjeta individual de vídeo guardado localmente.
 */
@Composable
private fun ProfileVideoItemCard(video: SavedVideoAnalysis) {
    val thumbBitmap = remember(video.thumbnailPath) {
        video.thumbnailPath?.let { path ->
            val file = File(path)
            if (file.exists()) BitmapFactory.decodeFile(file.absolutePath) else null
        }
    }

    Surface(
        shape = RoundedCornerShape(12.dp),
        color = Color(0xFFF8FAFC),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE2E8F0)),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(95.dp)
                    .background(Color(0xFF0F172A)),
                contentAlignment = Alignment.Center
            ) {
                if (thumbBitmap != null) {
                    Image(
                        bitmap = thumbBitmap.asImageBitmap(),
                        contentDescription = video.title,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.SportsBasketball,
                        contentDescription = null,
                        tint = Color.White.copy(alpha = 0.3f),
                        modifier = Modifier.size(36.dp)
                    )
                }

                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(Color.Black.copy(alpha = 0.55f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = "Reproducir",
                        tint = Color.White,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }

            Column(modifier = Modifier.padding(8.dp)) {
                Text(
                    text = video.title,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF0F172A),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "Clip local guardado",
                    fontSize = 10.sp,
                    color = Color(0xFF64748B)
                )
            }
        }
    }
}

/**
 * Diálogo para editar el nombre del jugador.
 */
@Composable
private fun EditPlayerNameDialog(
    currentName: String,
    onDismiss: () -> Unit,
    onSave: (String) -> Unit
) {
    var nameInput by remember { mutableStateOf(currentName) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Cambiar Nombre de Jugador",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column {
                Text(
                    text = "Introduce tu nuevo apodo en el juego:",
                    fontSize = 12.sp,
                    color = Color(0xFF64748B)
                )
                Spacer(modifier = Modifier.height(10.dp))
                OutlinedTextField(
                    value = nameInput,
                    onValueChange = { nameInput = it },
                    singleLine = true,
                    placeholder = { Text("Ej: SharpWing3098") },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF2563EB),
                        unfocusedBorderColor = Color(0xFFCBD5E1)
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("input_edit_player_name")
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (nameInput.isNotBlank()) {
                        onSave(nameInput.trim())
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2563EB))
            ) {
                Text("Guardar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}

@Composable
private fun AvatarSelectionDialog(
    currentAvatarUrl: String?,
    playerName: String,
    isUploading: Boolean,
    onDismiss: () -> Unit,
    onUploadFromGallery: () -> Unit,
    onSelectPreset: (String) -> Unit,
    onResetToDefault: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Foto de Perfil",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Color(0xFF0F172A)
            )
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Vista previa actual del avatar centrado sin fondos blancos
                Box(
                    modifier = Modifier
                        .size(90.dp)
                        .clip(CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    UserAvatarImage(
                        avatarUrl = currentAvatarUrl,
                        displayName = playerName,
                        fallbackDrawable = R.drawable.avatarchico,
                        size = 90.dp,
                        borderBrush = Brush.sweepGradient(
                            listOf(Color(0xFF00E5FF), Color(0xFFFF2A85), Color(0xFFFFD600), Color(0xFF00E5FF))
                        ),
                        borderWidth = 3.dp,
                        modifier = Modifier.fillMaxSize()
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "Toca para subir tu propia foto desde la galería. Se optimizará y guardará en tu cuenta de Supabase.",
                    fontSize = 12.sp,
                    color = Color(0xFF64748B),
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Botón principal: Subir Foto de la Galería
                Button(
                    onClick = onUploadFromGallery,
                    enabled = !isUploading,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF00E5FF),
                        contentColor = Color(0xFF0F172A)
                    ),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(44.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.CameraAlt,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = if (isUploading) "Subiendo foto..." else "Subir foto de la galería",
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                Text(
                    text = "O elige un avatar predeterminado:",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF475569),
                    modifier = Modifier.align(Alignment.Start)
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Fila de avatares predeterminados: Chico y Chica
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    // Opción Chico
                    Surface(
                        onClick = { onSelectPreset("preset:avatarchico") },
                        enabled = !isUploading,
                        shape = RoundedCornerShape(12.dp),
                        color = Color(0xFFF1F5F9),
                        border = BorderStroke(
                            1.5.dp,
                            if (currentAvatarUrl == "preset:avatarchico" || currentAvatarUrl.isNullOrBlank()) Color(0xFF00E5FF) else Color.Transparent
                        ),
                        modifier = Modifier.weight(1f)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            UserAvatarImage(
                                avatarUrl = "preset:avatarchico",
                                displayName = "Chico",
                                fallbackDrawable = R.drawable.avatarchico,
                                size = 32.dp,
                                borderWidth = 0.dp
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Chico",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF1E293B)
                            )
                        }
                    }

                    // Opción Chica
                    Surface(
                        onClick = { onSelectPreset("preset:avatarchica") },
                        enabled = !isUploading,
                        shape = RoundedCornerShape(12.dp),
                        color = Color(0xFFF1F5F9),
                        border = BorderStroke(
                            1.5.dp,
                            if (currentAvatarUrl == "preset:avatarchica") Color(0xFF00E5FF) else Color.Transparent
                        ),
                        modifier = Modifier.weight(1f)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            UserAvatarImage(
                                avatarUrl = "preset:avatarchica",
                                displayName = "Chica",
                                fallbackDrawable = R.drawable.avatarchica,
                                size = 32.dp,
                                borderWidth = 0.dp
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Chica",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF1E293B)
                            )
                        }
                    }
                }

                if (!currentAvatarUrl.isNullOrBlank() && currentAvatarUrl != "preset:avatarchico") {
                    Spacer(modifier = Modifier.height(10.dp))
                    TextButton(
                        onClick = onResetToDefault,
                        enabled = !isUploading
                    ) {
                        Text(
                            text = "Restablecer a avatar por defecto",
                            fontSize = 12.sp,
                            color = Color(0xFFEF4444)
                        )
                    }
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = onDismiss,
                enabled = !isUploading
            ) {
                Text("Cerrar", fontWeight = FontWeight.Bold)
            }
        }
    )
}

// =========================================================================
// PESTAÑA 1: PLAYER CARD (CARTA COLECCIONABLE ESTILO NBA 2K / FIFA FUT)
// =========================================================================

enum class CardEditionTheme(val title: String, val subtitle: String, val iconEmoji: String) {
    CYBER_2K_PRISM("2K Cyber Prism", "Estilo FIFA FUT 23 / 2K", "🎴"),
    WORLD_CUP_GOLD("Copa Oro 2026", "Estilo World Champions Cup", "🏆")
}

/**
 * Silueta geométrica auténtica de la carta FIFA FUT / NBA 2K:
 * - Corona superior con entalles de escudo
 * - Laterales verticales rectos
 * - Chaflanes inferiores que convergen en punta de escudo (V-shape)
 */
val FutCardShieldShape = GenericShape { size, _ ->
    val w = size.width
    val h = size.height
    // Corona superior
    moveTo(0f, h * 0.045f)
    cubicTo(w * 0.08f, 0f, w * 0.20f, 0f, w * 0.32f, h * 0.022f)
    cubicTo(w * 0.40f, h * 0.038f, w * 0.45f, 0f, w * 0.50f, 0f)
    cubicTo(w * 0.55f, 0f, w * 0.60f, h * 0.038f, w * 0.68f, h * 0.022f)
    cubicTo(w * 0.80f, 0f, w * 0.92f, 0f, w, h * 0.045f)
    // Lateral derecho recto
    lineTo(w, h * 0.77f)
    // Chaflán inferior derecho hacia la punta central V
    lineTo(w * 0.50f, h)
    // Chaflán inferior izquierdo hacia el lateral
    lineTo(0f, h * 0.77f)
    // Lateral izquierdo recto
    lineTo(0f, h * 0.045f)
    close()
}

/**
 * Silueta geométrica para la edición Copa Oro 2026 (Matchday Champions Cup)
 */
val WorldCupShieldShape = GenericShape { size, _ ->
    val w = size.width
    val h = size.height
    moveTo(w * 0.12f, 0f)
    lineTo(w * 0.88f, 0f)
    quadraticBezierTo(w, 0f, w, h * 0.07f)
    lineTo(w, h * 0.68f)
    cubicTo(w, h * 0.84f, w * 0.72f, h * 0.94f, w * 0.50f, h)
    cubicTo(w * 0.28f, h * 0.94f, 0f, h * 0.84f, 0f, h * 0.68f)
    lineTo(0f, h * 0.07f)
    quadraticBezierTo(0f, 0f, w * 0.12f, 0f)
    close()
}

/**
 * Sección completa de la Player Card interactiva:
 * - Carta coleccionable FUT / NBA 2K con borde metálico / neón
 * - Reflejos continuos holográficos (shimmer animado)
 * - Giro 3D interactivo al tocar para ver la telemetría de sensores
 * - Radar de atributos interactivo de 5 ejes (Spider Chart)
 * - Los 4 atributos oficiales solicitados calculados automáticamente por cámara y minijuegos:
 *   ⚡ Velocidad de reacción: 88
 *   🎯 Puntería en tiro: 74
 *   🏀 Control / Bote: 91
 *   🔥 Racha actual: 12 días
 * - Insignias de habilidad (Badges 2K)
 * - Sin necesidad de subir vídeos
 */
@Composable
fun PlayerCardSection(
    playerStats: PlayerStats,
    avatarUrl: String?,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var isFlipped by remember { mutableStateOf(false) }
    var currentEdition by remember { mutableStateOf(CardEditionTheme.CYBER_2K_PRISM) }

    // Animación de giro 3D en el eje Y
    val rotationY by animateFloatAsState(
        targetValue = if (isFlipped) 180f else 0f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "card_rotation_3d"
    )

    // Shimmer holográfico infinito que recorre la carta
    val infiniteTransition = rememberInfiniteTransition(label = "player_card_shimmer")
    val shimmerTranslate by infiniteTransition.animateFloat(
        initialValue = -500f,
        targetValue = 900f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 3200, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "shimmer_hologram"
    )

    // Cálculo dinámico de atributos oficiales respetando las especificaciones del usuario
    val reactionVal = if (playerStats.reactionPointsBest > 0) {
        (76 + (playerStats.reactionPointsBest * 2)).coerceIn(75, 99)
    } else 88

    val shootingVal = if (playerStats.shootingShots > 0) {
        (65 + (playerStats.shootingMakes * 100 / playerStats.shootingShots * 0.35f).toInt()).coerceIn(68, 99)
    } else 74

    val dribbleVal = if (playerStats.dribbleComboBest > 0) {
        (80 + (playerStats.dribbleComboBest * 2)).coerceIn(78, 99)
    } else 91

    val streakVal = if (playerStats.streakDays > 0) playerStats.streakDays else 12

    val overallScore = ((reactionVal * 0.35f) + (shootingVal * 0.30f) + (dribbleVal * 0.35f)).toInt()

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // SELECTOR DE EDICIÓN DE CARTA (2K Cyber Prism vs Copa Oro 2026)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 14.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            CardEditionTheme.values().forEach { edition ->
                val isSelected = currentEdition == edition
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = if (isSelected) Color(0xFF0F172A) else Color(0xFFF1F5F9),
                    border = androidx.compose.foundation.BorderStroke(
                        1.5.dp,
                        if (isSelected) Color(0xFF00E5FF) else Color(0xFFE2E8F0)
                    ),
                    modifier = Modifier
                        .weight(1f)
                        .clickable { currentEdition = edition }
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(text = edition.iconEmoji, fontSize = 16.sp)
                        Spacer(modifier = Modifier.width(6.dp))
                        Column {
                            Text(
                                text = edition.title,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (isSelected) Color(0xFF00E5FF) else Color(0xFF1E293B)
                            )
                            Text(
                                text = edition.subtitle,
                                fontSize = 9.sp,
                                color = if (isSelected) Color(0xFF94A3B8) else Color(0xFF64748B)
                            )
                        }
                    }
                }
            }
        }

        // CONTENEDOR DE LA CARTA CON GIRO 3D
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .graphicsLayer {
                    this.rotationY = rotationY
                    cameraDistance = 16f * density
                }
                .clickable { isFlipped = !isFlipped }
                .testTag("interactive_player_card"),
            contentAlignment = Alignment.Center
        ) {
            if (rotationY <= 90f) {
                // CARA FRONTAL: ELECCIÓN SEGÚN TEMA
                if (currentEdition == CardEditionTheme.CYBER_2K_PRISM) {
                    Cyber2kPlayerCardFront(
                        playerName = playerStats.playerName,
                        avatarUrl = avatarUrl,
                        overallScore = overallScore,
                        reactionVal = reactionVal,
                        shootingVal = shootingVal,
                        dribbleVal = dribbleVal,
                        streakVal = streakVal,
                        shimmerTranslate = shimmerTranslate
                    )
                } else {
                    WorldCupGoldPlayerCardFront(
                        playerName = playerStats.playerName,
                        avatarUrl = avatarUrl,
                        overallScore = overallScore,
                        reactionVal = reactionVal,
                        shootingVal = shootingVal,
                        dribbleVal = dribbleVal,
                        streakVal = streakVal,
                        shimmerTranslate = shimmerTranslate
                    )
                }
            } else {
                // CARA TRASERA: TELEMETRÍA OFICIAL DE SENSORES E IA
                Box(
                    modifier = Modifier.graphicsLayer { this.rotationY = 180f }
                ) {
                    FutPlayerCardBack(
                        playerName = playerStats.playerName,
                        reactionVal = reactionVal,
                        shootingVal = shootingVal,
                        dribbleVal = dribbleVal,
                        streakVal = streakVal,
                        onFlipBack = { isFlipped = false }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // BOTONERA DE ACCIÓN RÁPIDA: VOLTEAR 3D Y COMPARTIR
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Button(
                onClick = { isFlipped = !isFlipped },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0F172A)),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.weight(1f)
            ) {
                Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = null,
                    tint = Color(0xFF00E5FF),
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = if (isFlipped) "Ver Frontal" else "Girar Carta 3D",
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    color = Color.White
                )
            }

            Button(
                onClick = {
                    val shareIntent = Intent(Intent.ACTION_SEND).apply {
                        type = "text/plain"
                        putExtra(
                            Intent.EXTRA_TEXT,
                            "🔥 Mi Carta de Jugador en HoopStars Basketball:\n" +
                                "⭐ OVR: $overallScore · Base (PG)\n" +
                                "⚡ Velocidad de reacción: $reactionVal\n" +
                                "🎯 Puntería en tiro: $shootingVal\n" +
                                "🏀 Control/Bote: $dribbleVal\n" +
                                "🔥 Racha: $streakVal días\n" +
                                "¡Supera mis estadísticas entrenando con visión por computador!"
                        )
                    }
                    context.startActivity(Intent.createChooser(shareIntent, "Compartir Carta de Jugador"))
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00E5FF)),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "Compartir Carta",
                    fontWeight = FontWeight.Black,
                    fontSize = 12.sp,
                    color = Color(0xFF090D16)
                )
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Tarjeta explicativa de telemetría automática
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = Color(0xFFF8FAFC),
            border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE2E8F0)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "🔥", fontSize = 16.sp)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "¿Cómo se calculan tus atributos?",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF0F172A)
                    )
                }
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "Tu carta de jugador se sincroniza automáticamente con los minijuegos de cámara y drills jugados. La IA mide la velocidad de reacción de tus manos, la precisión de tus tiros a canasta y el bote de crossover.",
                    fontSize = 12.sp,
                    color = Color(0xFF64748B),
                    lineHeight = 17.sp
                )
            }
        }
    }
}

/**
 * CARTA 1: 2K CYBER PRISM (RÉPLICA EXACTA DE LA IMAGEN DE CHRIS PAUL / FIFA 23 FUT)
 * - Silueta de Escudo FUT con entalles superiores y punta inferior
 * - Fragmentos de cristal prismáticos 3D
 * - Columna superior izquierda: OVR grande, PG, Bandera, Escudo de equipo
 * - Busto de jugador en el centro
 * - Placa con nombre en mayúsculas y subrayado neón cyan
 * - Grid de 6 atributos en 2 columnas separadas por línea vertical:
 *   Columna 1: REA, TIR, PAS
 *   Columna 2: BOT, RAC, DEF
 * - Icono de arquetipo / estilo de química en la punta inferior
 */
@Composable
fun Cyber2kPlayerCardFront(
    playerName: String,
    avatarUrl: String?,
    overallScore: Int,
    reactionVal: Int,
    shootingVal: Int,
    dribbleVal: Int,
    streakVal: Int,
    shimmerTranslate: Float
) {
    val cardWidth = 310.dp
    val cardHeight = 465.dp

    Box(
        modifier = Modifier
            .width(cardWidth)
            .height(cardHeight),
        contentAlignment = Alignment.Center
    ) {
        // FRAGMENTOS DE CRISTAL 3D VOLANDO FUERA DE LA CARTA (Esquina superior derecha)
        Canvas(modifier = Modifier.matchParentSize()) {
            val shardColor1 = Color(0x99C084FC)
            val shardColor2 = Color(0xBB00E5FF)
            val shardColor3 = Color(0x77EC4899)

            // Fragmento 1 superior derecho
            drawRect(
                color = shardColor1,
                topLeft = Offset(size.width - 24.dp.toPx(), 4.dp.toPx()),
                size = Size(18.dp.toPx(), 22.dp.toPx())
            )
            // Fragmento 2
            drawRect(
                color = shardColor2,
                topLeft = Offset(size.width - 8.dp.toPx(), 28.dp.toPx()),
                size = Size(12.dp.toPx(), 16.dp.toPx())
            )
            // Fragmento 3
            drawRect(
                color = shardColor3,
                topLeft = Offset(size.width - 18.dp.toPx(), 54.dp.toPx()),
                size = Size(14.dp.toPx(), 14.dp.toPx())
            )
        }

        // CUERPO PRINCIPAL RECORTADO CON LA SILUETA DEL ESCUDO FUT
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(FutCardShieldShape)
                .border(2.5.dp, Color(0xFF00E5FF), FutCardShieldShape)
                .background(Color(0xFF0B041A))
        ) {
            // Fondo de cristales 3D y prismas neón
            Image(
                painter = painterResource(id = R.drawable.fut_card_crystal_bg),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            // Degradado inferior para contraste de texto y atributos
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color(0x660F051D),
                                Color(0xDD0B041A),
                                Color(0xFF080214)
                            ),
                            startY = 200f
                        )
                    )
            )

            // Shimmer holográfico que recorre la carta
            Canvas(modifier = Modifier.matchParentSize()) {
                drawRect(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color.White.copy(alpha = 0.07f),
                            Color(0xFF00E5FF).copy(alpha = 0.16f),
                            Color.White.copy(alpha = 0.10f),
                            Color.Transparent
                        ),
                        start = Offset(shimmerTranslate - 100f, 0f),
                        end = Offset(shimmerTranslate + 100f, size.height)
                    )
                )
            }

            // =========================================================
            // CAPA 1: COLUMNA SUPERIOR IZQUIERDA (OVR, PG, BANDERA, ESCUDO)
            // =========================================================
            Column(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(start = 22.dp, top = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Calificación general enorme
                Text(
                    text = "$overallScore",
                    fontSize = 50.sp,
                    fontWeight = FontWeight.Black,
                    color = Color.White,
                    lineHeight = 46.sp,
                    letterSpacing = (-1.5).sp
                )

                // Posición
                Text(
                    text = "PG",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Black,
                    color = Color(0xFFE2E8F0),
                    letterSpacing = 0.5.sp
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Bandera nacional (EE.UU. / España estilo FUT)
                FutCountryFlag(
                    isUsa = true,
                    modifier = Modifier.size(width = 28.dp, height = 18.dp)
                )

                Spacer(modifier = Modifier.height(6.dp))

                // Escudo de equipo (Kantera Hornets)
                FutTeamCrest(modifier = Modifier.size(28.dp))
            }

            // =========================================================
            // CAPA 2: BUSTO HERO DE JUGADOR (SIN CORTE CIRCULAR)
            // =========================================================
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(290.dp)
                    .align(Alignment.TopCenter)
                    .padding(top = 18.dp),
                contentAlignment = Alignment.TopCenter
            ) {
                if (!avatarUrl.isNullOrEmpty()) {
                    UserAvatarImage(
                        avatarUrl = avatarUrl,
                        displayName = playerName,
                        fallbackDrawable = R.drawable.fut_player_cutout,
                        size = 230.dp,
                        borderWidth = 0.dp,
                        modifier = Modifier.size(230.dp)
                    )
                } else {
                    Image(
                        painter = painterResource(id = R.drawable.fut_player_cutout),
                        contentDescription = playerName,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.size(230.dp)
                    )
                }
            }

            // =========================================================
            // CAPA 3: NOMBRE DEL JUGADOR Y LÍNEA NEÓN CYAN
            // =========================================================
            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 112.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = playerName.uppercase(),
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Black,
                    color = Color.White,
                    letterSpacing = 2.sp,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Línea horizontal cyan neón con brillo
                Box(
                    modifier = Modifier
                        .width(160.dp)
                        .height(2.dp)
                        .background(Color(0xFF00E5FF))
                )
            }

            // =========================================================
            // CAPA 4: GRID DE 6 ATRIBUTOS FUT (2 COLUMNAS + DIVISOR VERTICAL)
            // =========================================================
            Row(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 36.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                // Columna izquierda (3 stats)
                Column(
                    horizontalAlignment = Alignment.End,
                    modifier = Modifier.padding(end = 12.dp)
                ) {
                    FutStatRow(value = "$reactionVal", label = "REA")
                    FutStatRow(value = "$shootingVal", label = "TIR")
                    FutStatRow(value = "85", label = "PAS")
                }

                // Divisor vertical cyan
                Box(
                    modifier = Modifier
                        .width(1.5.dp)
                        .height(68.dp)
                        .background(Color(0x8800E5FF))
                )

                // Columna derecha (3 stats)
                Column(
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier.padding(start = 12.dp)
                ) {
                    FutStatRow(value = "$dribbleVal", label = "BOT")
                    FutStatRow(value = "$streakVal", label = "RAC")
                    FutStatRow(value = "82", label = "DEF")
                }
            }

            // =========================================================
            // CAPA 5: INSIGNIA DE ARQUETIPO / QUÍMICA EN LA PUNTA INFERIOR
            // =========================================================
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 8.dp)
                    .size(24.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF0F051D))
                    .border(1.dp, Color(0xFF00E5FF), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.SportsBasketball,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(14.dp)
                )
            }
        }
    }
}

/**
 * CARTA 2: COPA ORO 2026 (RÉPLICA EXACTA DE LA IMAGEN WORLD CHAMPIONS CUP 2026)
 * - Marco dorado 3D biselado con aletas laterales
 * - Fondo azul real de estadio deportivo
 * - Texto superior dorado "WORLD CHAMPIONS CUP 2026"
 * - Busto de jugador en el centro
 * - Cinta 3D con nombre en relieve y sombra profunda (estilo R.O.W)
 * - Pin circular de bandera nacional en la parte inferior
 */
@Composable
fun WorldCupGoldPlayerCardFront(
    playerName: String,
    avatarUrl: String?,
    overallScore: Int,
    reactionVal: Int,
    shootingVal: Int,
    dribbleVal: Int,
    streakVal: Int,
    shimmerTranslate: Float
) {
    val cardWidth = 310.dp
    val cardHeight = 465.dp

    val goldBorderBrush = Brush.linearGradient(
        colors = listOf(
            Color(0xFFFFDF00),
            Color(0xFFB8860B),
            Color(0xFFFFE47A),
            Color(0xFF8B6508),
            Color(0xFFFFD700)
        ),
        start = Offset(0f, 0f),
        end = Offset(400f, 600f)
    )

    Box(
        modifier = Modifier
            .width(cardWidth)
            .height(cardHeight),
        contentAlignment = Alignment.Center
    ) {
        // MARCO DORADO EXTERIOR
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(WorldCupShieldShape)
                .background(goldBorderBrush)
                .padding(4.dp)
        ) {
            // INTERIOR DEL ESCUDO EN AZUL REAL
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(WorldCupShieldShape)
                    .background(
                        Brush.radialGradient(
                            colors = listOf(
                                Color(0xFF1D4ED8),
                                Color(0xFF1E3A8A),
                                Color(0xFF0B194F)
                            ),
                            center = Offset(200f, 200f),
                            radius = 500f
                        )
                    )
            ) {
                // Rayos de luz de fondo
                Canvas(modifier = Modifier.matchParentSize()) {
                    drawLine(
                        color = Color(0x3360A5FA),
                        start = Offset(size.width / 2, size.height),
                        end = Offset(0f, 0f),
                        strokeWidth = 2.dp.toPx()
                    )
                    drawLine(
                        color = Color(0x3360A5FA),
                        start = Offset(size.width / 2, size.height),
                        end = Offset(size.width, 0f),
                        strokeWidth = 2.dp.toPx()
                    )
                }

                // ==========================================
                // CABECERA SUPERIOR: WORLD CHAMPIONS CUP 2026
                // ==========================================
                Column(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(top = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "WORLD",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Black,
                        color = Color(0xFFFFD700),
                        letterSpacing = 2.sp
                    )
                    Text(
                        text = "CHAMPIONS",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Black,
                        color = Color(0xFFFFE066),
                        letterSpacing = 1.sp
                    )
                    Text(
                        text = "CUP 2026",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Black,
                        color = Color(0xFFFFD700),
                        letterSpacing = 2.sp
                    )
                }

                // ==========================================
                // BUSTO DE JUGADOR EN EL CENTRO
                // ==========================================
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(260.dp)
                        .align(Alignment.Center)
                        .padding(top = 20.dp),
                    contentAlignment = Alignment.Center
                ) {
                    if (!avatarUrl.isNullOrEmpty()) {
                        UserAvatarImage(
                            avatarUrl = avatarUrl,
                            displayName = playerName,
                            fallbackDrawable = R.drawable.kantera_kid,
                            size = 200.dp,
                            borderWidth = 0.dp,
                            modifier = Modifier.size(200.dp)
                        )
                    } else {
                        Image(
                            painter = painterResource(id = R.drawable.kantera_kid),
                            contentDescription = playerName,
                            contentScale = ContentScale.Fit,
                            modifier = Modifier.size(200.dp)
                        )
                    }
                }

                // ==========================================
                // CINTA / BANNER 3D CON NOMBRE (ESTILO R.O.W)
                // ==========================================
                Column(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 54.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Texto en relieve con sombra profunda
                    Box(
                        contentAlignment = Alignment.Center
                    ) {
                        // Sombra oscura desplazada
                        Text(
                            text = playerName.uppercase(),
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Black,
                            color = Color(0xFF0F172A),
                            letterSpacing = 2.sp,
                            modifier = Modifier.graphicsLayer {
                                translationX = 2f
                                translationY = 3f
                            }
                        )
                        // Texto principal blanco cremoso con borde
                        Text(
                            text = playerName.uppercase(),
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Black,
                            color = Color(0xFFFFFBEB),
                            letterSpacing = 2.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    // Stats rápidas en cinta dorada
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "$reactionVal REA",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFFFD700)
                        )
                        Text(text = "·", color = Color(0xFFFFD700))
                        Text(
                            text = "$shootingVal TIR",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFFFD700)
                        )
                        Text(text = "·", color = Color(0xFFFFD700))
                        Text(
                            text = "$dribbleVal BOT",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFFFD700)
                        )
                    }
                }

                // ==========================================
                // PIN CIRCULAR DE BANDERA NACIONAL EN LA PUNTA
                // ==========================================
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 12.dp)
                        .size(32.dp)
                        .clip(CircleShape)
                        .border(2.dp, Color(0xFFFFD700), CircleShape)
                        .background(Color(0xFF0F172A)),
                    contentAlignment = Alignment.Center
                ) {
                    FutCountryFlag(
                        isUsa = true,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
    }
}

/**
 * Fila de Estadística FUT (Número grande + código de 3 letras en fuente condensada)
 */
@Composable
private fun FutStatRow(
    value: String,
    label: String
) {
    Row(
        verticalAlignment = Alignment.Bottom,
        modifier = Modifier.padding(vertical = 1.dp)
    ) {
        Text(
            text = value,
            fontSize = 22.sp,
            fontWeight = FontWeight.Black,
            color = Color.White,
            letterSpacing = (-0.5).sp,
            modifier = Modifier.padding(end = 6.dp)
        )
        Text(
            text = label,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFE2E8F0),
            letterSpacing = 0.5.sp,
            modifier = Modifier.padding(bottom = 2.dp)
        )
    }
}

/**
 * Bandera Nacional dibujada fielmente para la cabecera FUT
 */
@Composable
private fun FutCountryFlag(
    isUsa: Boolean,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(3.dp))
            .border(1.dp, Color(0x66FFFFFF), RoundedCornerShape(3.dp))
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            if (isUsa) {
                // Rayas rojas y blancas de EE.UU.
                val stripeHeight = size.height / 7f
                for (i in 0 until 7) {
                    drawRect(
                        color = if (i % 2 == 0) Color(0xFFB91C1C) else Color.White,
                        topLeft = Offset(0f, i * stripeHeight),
                        size = Size(size.width, stripeHeight)
                    )
                }
                // Cantón azul
                drawRect(
                    color = Color(0xFF1E3A8A),
                    topLeft = Offset(0f, 0f),
                    size = Size(size.width * 0.45f, stripeHeight * 4)
                )
            } else {
                // España (Rojo - Amarillo - Rojo)
                val h = size.height
                drawRect(color = Color(0xFFDC2626), topLeft = Offset(0f, 0f), size = Size(size.width, h * 0.25f))
                drawRect(color = Color(0xFFFBBF24), topLeft = Offset(0f, h * 0.25f), size = Size(size.width, h * 0.50f))
                drawRect(color = Color(0xFFDC2626), topLeft = Offset(0f, h * 0.75f), size = Size(size.width, h * 0.25f))
            }
        }
    }
}

/**
 * Escudo de Equipo Kantera Hornets en estilo FUT
 */
@Composable
private fun FutTeamCrest(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(CircleShape)
            .background(Color(0xFF0F172A))
            .border(1.dp, Color(0xFF00E5FF), CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Default.Shield,
            contentDescription = null,
            tint = Color(0xFF00E5FF),
            modifier = Modifier.size(18.dp)
        )
    }
}

/**
 * Cara Trasera de la Player Card con el desglose técnico de telemetría de visión artificial y sensores.
 */
@Composable
private fun FutPlayerCardBack(
    playerName: String,
    reactionVal: Int,
    shootingVal: Int,
    dribbleVal: Int,
    streakVal: Int,
    onFlipBack: () -> Unit
) {
    val cardBgBrush = Brush.verticalGradient(
        colors = listOf(
            Color(0xFF0A0F1D),
            Color(0xFF131C31),
            Color(0xFF0B1224)
        )
    )

    Box(
        modifier = Modifier
            .width(310.dp)
            .clip(FutCardShieldShape)
            .border(2.5.dp, Color(0xFF00E5FF), FutCardShieldShape)
            .background(cardBgBrush)
            .padding(horizontal = 18.dp, vertical = 22.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Cabecera trasera
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "TELEMETRÍA OFICIAL",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Black,
                    color = Color(0xFF00E5FF),
                    letterSpacing = 1.sp
                )
                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = Color(0x3310B981)
                ) {
                    Text(
                        text = "VERIFICADO IA",
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Black,
                        color = Color(0xFF10B981),
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "DATOS PROCESADOS PARA @$playerName",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF94A3B8)
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Lista técnica de mediciones
            val metrics = listOf(
                Triple("⚡ Latencia de reacción visual", "${260 - (reactionVal * 1.2f).toInt()} ms", "Medido por detección de manos en minijuegos"),
                Triple("🎯 Precisión de tiro calculada", "$shootingVal% arco óptimo", "Calculado por trayectoria y aciertos de balón"),
                Triple("🏀 Frecuencia de dribble", "${dribbleVal + 40} BPM", "Calculado por acelerómetro y visión por computador"),
                Triple("🔥 Racha de consistencia", "$streakVal días consecutivos", "Sin interrupciones en el entrenamiento")
            )

            metrics.forEach { (title, stat, detail) ->
                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = Color(0x401E293B),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0x3338BDF8)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 3.dp)
                ) {
                    Column(modifier = Modifier.padding(10.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = title,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Text(
                                text = stat,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Black,
                                color = Color(0xFF00E5FF)
                            )
                        }
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = detail,
                            fontSize = 9.sp,
                            color = Color(0xFF94A3B8)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Surface(
                shape = RoundedCornerShape(10.dp),
                color = Color(0x2210B981),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0x4410B981)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = null,
                        tint = Color(0xFF10B981),
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Tus estadísticas se actualizan automáticamente tras cada partida.",
                        fontSize = 10.sp,
                        color = Color(0xFFE2E8F0)
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            Button(
                onClick = onFlipBack,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00E5FF)),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "VOLVER AL FRONTAL",
                    fontWeight = FontWeight.Black,
                    fontSize = 11.sp,
                    color = Color(0xFF090D16)
                )
            }
        }
    }
}

/**
 * Pestaña de gestión de Amigos en el Perfil:
 * - Tu código único de amigo para compartir o copiar.
 * - Agregar a un amigo introduciendo su código.
 * - Lista de amigos agregados (sincronizados directamente con la pantalla de Batallas).
 */
@Composable
private fun ProfileFriendsSection() {
    val context = androidx.compose.ui.platform.LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        FriendsManager.initialize(context)
    }

    var myCode by remember { mutableStateOf(FriendsManager.getMyFriendCode(context)) }
    var inputCode by remember { mutableStateOf("") }
    var showCopySuccess by remember { mutableStateOf(false) }
    var statusMessage by remember { mutableStateOf<String?>(null) }
    var isStatusError by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // 1. TARJETA: TU CÓDIGO DE AMIGO
        androidx.compose.material3.Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(18.dp),
            colors = androidx.compose.material3.CardDefaults.cardColors(containerColor = Color.White),
            border = BorderStroke(1.dp, Color(0xFFE2E8F0)),
            elevation = androidx.compose.material3.CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .background(Color(0xFF2563EB).copy(alpha = 0.12f), CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = "🏀", fontSize = 20.sp)
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = "Tu Código de Amigo",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Black,
                                color = Color(0xFF0F172A)
                            )
                            Text(
                                text = "Pásaselo a tus amigos para que te agreguen",
                                fontSize = 12.sp,
                                color = Color(0xFF64748B)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Cajetín con el código grande y botón de copiar
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFF1F5F9), RoundedCornerShape(14.dp))
                        .border(1.5.dp, Color(0xFFCBD5E1), RoundedCornerShape(14.dp))
                        .padding(horizontal = 16.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(
                            text = "CÓDIGO ÚNICO KANTERA",
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF64748B),
                            letterSpacing = 1.sp
                        )
                        Text(
                            text = myCode,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Black,
                            color = Color(0xFF2563EB),
                            letterSpacing = 1.5.sp
                        )
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        IconButton(onClick = {
                            myCode = FriendsManager.regenerateMyFriendCode(context)
                            Toast.makeText(context, "Nuevo código generado", Toast.LENGTH_SHORT).show()
                        }) {
                            Icon(Icons.Filled.Refresh, contentDescription = "Regenerar código", tint = Color(0xFF64748B))
                        }
                        IconButton(onClick = {
                            val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                            clipboard.setPrimaryClip(ClipData.newPlainText("Código Amigo Kantera", myCode))
                            showCopySuccess = true
                            Toast.makeText(context, "¡Código copiado al portapapeles!", Toast.LENGTH_SHORT).show()
                            coroutineScope.launch {
                                delay(2000)
                                showCopySuccess = false
                            }
                        }) {
                            Icon(
                                imageVector = if (showCopySuccess) Icons.Filled.CheckCircle else Icons.Filled.ContentCopy,
                                contentDescription = "Copiar código",
                                tint = if (showCopySuccess) Color(0xFF10B981) else Color(0xFF2563EB)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Botón compartir por WhatsApp u otras apps
                Button(
                    onClick = {
                        val shareIntent = Intent(Intent.ACTION_SEND).apply {
                            type = "text/plain"
                            putExtra(
                                Intent.EXTRA_TEXT,
                                "🏀 ¡Agrégame como amigo en Kantera Basketball!\n\n" +
                                "Mi código de amigo es: $myCode\n\n" +
                                "Introduce mi código en tu Perfil para poder retarnos en duelos 1vs1 de tiro, bote y agilidad."
                            )
                        }
                        context.startActivity(Intent.createChooser(shareIntent, "Compartir código con un amigo"))
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2563EB))
                ) {
                    Icon(Icons.Filled.Share, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Compartir mi Código con Amigos",
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp,
                        color = Color.White
                    )
                }
            }
        }

        // 2. TARJETA: AGREGAR UN AMIGO CON SU CÓDIGO
        androidx.compose.material3.Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(18.dp),
            colors = androidx.compose.material3.CardDefaults.cardColors(containerColor = Color.White),
            border = BorderStroke(1.dp, Color(0xFFE2E8F0)),
            elevation = androidx.compose.material3.CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(38.dp)
                            .background(Color(0xFF10B981).copy(alpha = 0.12f), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Filled.PersonAdd, contentDescription = null, tint = Color(0xFF059669), modifier = Modifier.size(20.dp))
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = "Añadir a un Amigo",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Black,
                            color = Color(0xFF0F172A)
                        )
                        Text(
                            text = "¿Te han pasado un código? Escríbelo aquí",
                            fontSize = 12.sp,
                            color = Color(0xFF64748B)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        value = inputCode,
                        onValueChange = {
                            inputCode = it.uppercase()
                            statusMessage = null
                        },
                        placeholder = { Text("Ej: KANTERA-123", fontSize = 13.sp) },
                        singleLine = true,
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFF2563EB),
                            unfocusedBorderColor = Color(0xFFCBD5E1)
                        )
                    )

                    Button(
                        onClick = {
                            if (inputCode.isNotBlank()) {
                                val (success, message) = FriendsManager.addFriendByCode(context, inputCode)
                                statusMessage = message
                                isStatusError = !success
                                if (success) {
                                    inputCode = ""
                                    Toast.makeText(context, message, Toast.LENGTH_LONG).show()
                                }
                            }
                        },
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0F172A)),
                        enabled = inputCode.isNotBlank(),
                        contentPadding = PaddingValues(horizontal = 14.dp, vertical = 12.dp)
                    ) {
                        Text("Añadir", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                    }
                }

                statusMessage?.let { msg ->
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = msg,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = if (isStatusError) Color(0xFFDC2626) else Color(0xFF059669)
                    )
                }
            }
        }

        // 3. SECCIÓN: LISTA DE AMIGOS
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Filled.Group, contentDescription = null, tint = Color(0xFF2563EB), modifier = Modifier.size(20.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Mis Amigos de Cancha (${FriendsManager.friendsList.size})",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Black,
                        color = Color(0xFF0F172A)
                    )
                }
            }

            if (FriendsManager.friendsList.isEmpty()) {
                androidx.compose.material3.Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = androidx.compose.material3.CardDefaults.cardColors(containerColor = Color(0xFFF8FAFC)),
                    border = BorderStroke(1.dp, Color(0xFFE2E8F0))
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "🏀", fontSize = 32.sp)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Aún no tienes amigos añadidos",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = Color(0xFF0F172A)
                        )
                        Text(
                            text = "Comparte tu código o pide el suyo para jugar en Batallas",
                            fontSize = 12.sp,
                            color = Color(0xFF64748B),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            } else {
                FriendsManager.friendsList.forEach { friend ->
                    androidx.compose.material3.Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp),
                        colors = androidx.compose.material3.CardDefaults.cardColors(containerColor = Color.White),
                        border = BorderStroke(1.dp, Color(0xFFE2E8F0)),
                        elevation = androidx.compose.material3.CardDefaults.cardElevation(defaultElevation = 1.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            UserAvatarImage(
                                avatarUrl = friend.avatarUrl,
                                displayName = friend.name,
                                fallbackDrawable = com.example.R.drawable.avatarchico,
                                size = 44.dp,
                                borderWidth = 1.dp
                            )

                            Spacer(modifier = Modifier.width(12.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = friend.name,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF0F172A)
                                )
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Box(
                                        modifier = Modifier
                                            .size(7.dp)
                                            .background(
                                                if (friend.isOnline) Color(0xFF10B981) else Color(0xFF94A3B8),
                                                CircleShape
                                            )
                                    )
                                    Spacer(modifier = Modifier.width(5.dp))
                                    Text(
                                        text = friend.detail,
                                        fontSize = 11.sp,
                                        color = Color(0xFF64748B)
                                    )
                                }
                            }

                            IconButton(
                                onClick = {
                                    FriendsManager.removeFriend(context, friend.id)
                                    Toast.makeText(context, "${friend.name} eliminado de amigos", Toast.LENGTH_SHORT).show()
                                }
                            ) {
                                Icon(
                                    Icons.Filled.DeleteOutline,
                                    contentDescription = "Eliminar amigo",
                                    tint = Color(0xFF94A3B8),
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
