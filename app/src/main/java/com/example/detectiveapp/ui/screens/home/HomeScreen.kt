package com.example.detectiveapp.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircleOutline
import androidx.compose.material.icons.filled.CheckCircleOutline
import androidx.compose.material.icons.filled.FolderOpen
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.detectiveapp.ui.components.BottomNavBar
import com.example.detectiveapp.ui.components.QuickAccessCard
import com.example.detectiveapp.ui.theme.BlueAccent
import com.example.detectiveapp.ui.theme.BlueAccentLight
import com.example.detectiveapp.ui.theme.BorderStroke
import com.example.detectiveapp.ui.theme.NavyCardHighlight
import com.example.detectiveapp.ui.theme.NavyCardSurface
import com.example.detectiveapp.ui.theme.NavyDarkBackground
import com.example.detectiveapp.ui.theme.StatusGreen
import com.example.detectiveapp.ui.theme.TextMuted
import com.example.detectiveapp.ui.theme.TextPrimary
import com.example.detectiveapp.ui.theme.TextSecondary

@Composable
fun HomeScreen(
    onNavigateToCases: () -> Unit,
    onNavigateToNewCase: () -> Unit,
    onNavigateToClosedCases: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = NavyDarkBackground,
        bottomBar = {
            BottomNavBar(
                currentRoute = "home",
                onNavigateToRoute = { route ->
                    if (route == "cases") onNavigateToCases()
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 16.dp)
        ) {
            // Header superior
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(NavyCardSurface)
                            .border(1.dp, BorderStroke, RoundedCornerShape(10.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Logo",
                            tint = BlueAccentLight,
                            modifier = Modifier.size(22.dp)
                        )
                    }
                    Text(
                        text = "CaseTrack",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        ),
                        color = TextPrimary
                    )
                }

                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .background(NavyCardHighlight)
                        .border(1.5.dp, BlueAccent, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Perfil Detective",
                        tint = TextPrimary,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Banner visual
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(130.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(
                        Brush.linearGradient(
                            colors = listOf(
                                NavyCardSurface,
                                NavyCardHighlight,
                                BlueAccent.copy(alpha = 0.25f)
                            )
                        )
                    )
                    .border(1.dp, BorderStroke, RoundedCornerShape(20.dp))
                    .padding(20.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Column {
                    Text(
                        text = "Oficina Central de Investigación",
                        style = MaterialTheme.typography.labelSmall,
                        color = BlueAccentLight
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Expedientes y Evidencias",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        ),
                        color = TextPrimary
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Panel de control y registro criminal",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextSecondary
                    )
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            Text(
                text = "Hola, Detective",
                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                color = TextPrimary
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Selecciona un módulo de trabajo para comenzar:",
                style = MaterialTheme.typography.bodyMedium,
                color = TextMuted
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Cuadrícula con solo las acciones útiles
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                QuickAccessCard(
                    title = "Mis casos",
                    subtitle = "Expedientes activos",
                    icon = Icons.Default.FolderOpen,
                    iconTint = BlueAccentLight,
                    onClick = onNavigateToCases,
                    modifier = Modifier.weight(1f)
                )

                QuickAccessCard(
                    title = "Nuevo caso",
                    subtitle = "Abrir investigación",
                    icon = Icons.Default.AddCircleOutline,
                    iconTint = BlueAccent,
                    onClick = onNavigateToNewCase,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            QuickAccessCard(
                title = "Casos cerrados",
                subtitle = "Historial resuelto",
                icon = Icons.Default.CheckCircleOutline,
                iconTint = StatusGreen,
                onClick = onNavigateToClosedCases,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}