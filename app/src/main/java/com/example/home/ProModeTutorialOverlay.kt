package com.example.home

import android.content.Context
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.SportsBasketball
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.example.R
import kotlin.math.roundToInt

/**
 * Minitutorial tipo Spotlight / Coachmark que se lanza al abrir la Home por primera vez.
 * Oscurece la pantalla entera con un zoom dinámico, resalta el toggle superior GAME / PRO
 * y muestra una mano animada señalando cómo cambiar al Modo PRO.
 */
@Composable
fun ProModeTutorialOverlay(
    isGameMode: Boolean,
    onToggleMode: (Boolean) -> Unit,
    onDismiss: () -> Unit,
    toggleBounds: Rect? = null,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val density = LocalDensity.current

    // Animación de entrada con zoom rápido (scale) y opacidad (fade)
    var isVisible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        isVisible = true
    }

    val zoomScale by animateFloatAsState(
        targetValue = if (isVisible) 1.0f else 0.65f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMediumLow
        ),
        label = "spotlight_zoom"
    )

    val backdropAlpha by animateFloatAsState(
        targetValue = if (isVisible) 0.86f else 0f,
        animationSpec = tween(durationMillis = 280, easing = FastOutSlowInEasing),
        label = "backdrop_fade"
    )

    // Animaciones cíclicas de la mano y del halo de pulso
    val infiniteTransition = rememberInfiniteTransition(label = "hand_tap_anim")

    // Movimiento de pulsación de la mano (sube y baja apuntando al botón PRO)
    val handOffsetY by infiniteTransition.animateFloat(
        initialValue = 14f,
        targetValue = -4f,
        animationSpec = infiniteRepeatable(
            animation = tween(650, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "hand_y"
    )

    val handScale by infiniteTransition.animateFloat(
        initialValue = 0.95f,
        targetValue = 1.08f,
        animationSpec = infiniteRepeatable(
            animation = tween(650, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "hand_scale"
    )

    // Halo radar que se expande alrededor del toggle para llamar la atención visual
    val pulseRingScale by infiniteTransition.animateFloat(
        initialValue = 1.0f,
        targetValue = 1.45f,
        animationSpec = infiniteRepeatable(
            animation = tween(1100, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "radar_scale"
    )

    val pulseRingAlpha by infiniteTransition.animateFloat(
        initialValue = 0.75f,
        targetValue = 0.0f,
        animationSpec = infiniteRepeatable(
            animation = tween(1100, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "radar_alpha"
    )

    // Si el usuario pulsa atrás en Android, se cierra el tutorial sin bloquear
    BackHandler {
        onDismiss()
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .zIndex(9999f)
    ) {
        // 1. FONDO OSCURO (Scrim que enfoca la atención)
        // Pulsar en cualquier zona exterior cierra el tutorial al instante
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = backdropAlpha))
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {
                    onDismiss()
                }
        )

        // 2. CONTENIDO PRINCIPAL DEL SPOTLIGHT (Con zoom rápido)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .scale(zoomScale)
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.End
        ) {
            Spacer(modifier = Modifier.height(10.dp))

            // ZONA DEL TOGGLE RESALTADO (Alineado exactamente en la esquina superior derecha)
            Box(
                modifier = Modifier
                    .padding(end = 4.dp),
                contentAlignment = Alignment.Center
            ) {
                // A. Anillo de onda radar pulsante
                Box(
                    modifier = Modifier
                        .width(152.dp)
                        .height(48.dp)
                        .scale(pulseRingScale)
                        .alpha(pulseRingAlpha)
                        .border(
                            width = 2.dp,
                            color = Color(0xFFEA580C),
                            shape = RoundedCornerShape(50)
                        )
                )

                // B. Resplandor exterior de foco (Halo)
                Box(
                    modifier = Modifier
                        .width(148.dp)
                        .height(44.dp)
                        .shadow(elevation = 20.dp, shape = RoundedCornerShape(50), spotColor = Color(0xFFEA580C))
                        .border(
                            width = 2.5.dp,
                            brush = Brush.horizontalGradient(
                                listOf(
                                    Color(0xFF2FB2C9),
                                    Color(0xFFEA580C),
                                    Color(0xFFFFD600)
                                )
                            ),
                            shape = RoundedCornerShape(50)
                        )
                )

                // C. Réplica interactiva y luminosa del Toggle GAME / PRO
                // Al tocar directamente aquí, se conmuta el modo y se completa el tutorial
                Box(
                    modifier = Modifier
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) {
                            onToggleMode(!isGameMode)
                            onDismiss()
                        }
                ) {
                    GameTrainToggle(
                        isGameMode = isGameMode,
                        onToggle = { newMode ->
                            onToggleMode(newMode)
                            onDismiss()
                        }
                    )
                }
            }

            // 3. INDICADOR VISUAL: MANO DIBUJADA ANIMADA APUNTANDO AL TOGGLE
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .offset { IntOffset(x = 0, y = handOffsetY.roundToInt()) }
                    .padding(top = 8.dp, end = 12.dp),
                contentAlignment = Alignment.TopEnd
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Cartelito flotante de atención rápida
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(
                                Brush.horizontalGradient(
                                    listOf(Color(0xFFEA580C), Color(0xFFFF8A00))
                                )
                            )
                            .padding(horizontal = 10.dp, vertical = 5.dp)
                    ) {
                        Text(
                            text = "¡TOCA AQUÍ PARA MODO PRO!",
                            color = Color.White,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Black,
                            letterSpacing = 0.5.sp
                        )
                    }

                    // Vector de la mano animada con rebote
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .scale(handScale),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_hand_pointer),
                            contentDescription = "Mano señalando el botón de Modo PRO",
                            tint = Color.Unspecified,
                            modifier = Modifier.size(40.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // 4. TARJETA EXPLICATIVA (MINITUTORIAL COACHMARK)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(22.dp))
                    .background(Color(0xFF161A22))
                    .border(
                        width = 1.5.dp,
                        brush = Brush.linearGradient(
                            listOf(
                                Color(0xFFEA580C),
                                Color(0xFF2FB2C9).copy(alpha = 0.7f),
                                Color(0x33FFFFFF)
                            )
                        ),
                        shape = RoundedCornerShape(22.dp)
                    )
                    .padding(20.dp)
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    // Fila superior: Badge distintivo + Botón cerrar rápido (X)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .clip(RoundedCornerShape(20.dp))
                                .background(Color(0xFFEA580C).copy(alpha = 0.2f))
                                .padding(horizontal = 10.dp, vertical = 4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Bolt,
                                contentDescription = null,
                                tint = Color(0xFFFFB300),
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "ACCESO RÁPIDO",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Black,
                                color = Color(0xFFFFB300),
                                letterSpacing = 0.8.sp
                            )
                        }

                        // Botón cerrar (X) para seguir al instante
                        IconButton(
                            onClick = onDismiss,
                            modifier = Modifier.size(32.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Cerrar tutorial",
                                tint = Color(0xFF94A3B8),
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Título principal claro y llamativo
                    Text(
                        text = "¿Buscas la versión PRO?",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Black,
                        color = Color.White,
                        lineHeight = 26.sp
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Explicación concisa y directa
                    Text(
                        text = "Usa este selector superior en cualquier momento para alternar entre el Modo GAME (desafíos y juegos interactivos) y el Modo PRO (análisis táctico de tiro por cámara, mapa de calor y estadísticas avanzadas).",
                        fontSize = 13.5.sp,
                        color = Color(0xFFCBD5E1),
                        lineHeight = 19.sp
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    // Botón primario: Cambiar directamente a MODO PRO ahora
                    Box(
                        modifier = Modifier
                            .testTag("tutorial_switch_to_pro_button")
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(14.dp))
                            .background(
                                Brush.horizontalGradient(
                                    listOf(Color(0xFFEA580C), Color(0xFFF97316))
                                )
                            )
                            .clickable {
                                onToggleMode(false) // Activa PRO
                                onDismiss()
                            }
                            .padding(vertical = 14.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.SportsBasketball,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "CAMBIAR A MODO PRO AHORA",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Black,
                                letterSpacing = 0.8.sp,
                                color = Color.White
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Botón secundario: Continuar en Modo GAME
                    Box(
                        modifier = Modifier
                            .testTag("tutorial_stay_game_button")
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(14.dp))
                            .border(1.dp, Color(0x40FFFFFF), RoundedCornerShape(14.dp))
                            .clickable {
                                onDismiss()
                            }
                            .padding(vertical = 12.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Entendido, seguir en Modo GAME",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF94A3B8)
                        )
                    }
                }
            }
        }
    }
}
