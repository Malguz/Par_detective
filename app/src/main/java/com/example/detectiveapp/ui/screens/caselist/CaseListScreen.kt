package com.example.detectiveapp.ui.screens.caselist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.FolderOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.detectiveapp.logic.CaseViewModel
import com.example.detectiveapp.room.Case
import com.example.detectiveapp.ui.components.CaseListItem
import com.example.detectiveapp.ui.components.DetectiveSearchBar
import com.example.detectiveapp.ui.theme.BlueAccent
import com.example.detectiveapp.ui.theme.BorderStroke
import com.example.detectiveapp.ui.theme.NavyCardHighlight
import com.example.detectiveapp.ui.theme.NavyCardSurface
import com.example.detectiveapp.ui.theme.NavyDarkBackground
import com.example.detectiveapp.ui.theme.TextMuted
import com.example.detectiveapp.ui.theme.TextPrimary
import com.example.detectiveapp.ui.theme.TextSecondary

enum class CaseFilterType(val label: String) {
    TODOS("Todos"),
    EN_INVESTIGACION("En investigación"),
    CERRADOS("Cerrados")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CaseListScreen(
    viewModel: CaseViewModel,
    initialFilter: CaseFilterType = CaseFilterType.TODOS,
    onNavigateToDetail: (Int) -> Unit,
    onNavigateToCreateCase: () -> Unit,
    modifier: Modifier = Modifier
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedFilter by remember { mutableStateOf(initialFilter) }

    val casesState by if (searchQuery.isBlank()) {
        viewModel.cases.collectAsState(initial = emptyList())
    } else {
        viewModel.search(searchQuery).collectAsState(initial = emptyList())
    }

    val displayedCases = remember(casesState, selectedFilter) {
        when (selectedFilter) {
            CaseFilterType.TODOS -> casesState
            CaseFilterType.EN_INVESTIGACION -> casesState.filter {
                it.status.trim().equals("En investigación", ignoreCase = true)
            }
            CaseFilterType.CERRADOS -> casesState.filter {
                it.status.trim().equals("Cerrado", ignoreCase = true)
            }
        }
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = NavyDarkBackground,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Mis casos",
                        style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                        color = TextPrimary
                    )
                },
                actions = {
                    Button(
                        onClick = onNavigateToCreateCase,
                        colors = ButtonDefaults.buttonColors(containerColor = BlueAccent),
                        shape = RoundedCornerShape(10.dp),
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                        modifier = Modifier.padding(end = 8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Nuevo caso",
                            tint = TextPrimary,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.size(6.dp))
                        Text(
                            text = "Nuevo caso",
                            style = MaterialTheme.typography.labelLarge,
                            color = TextPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = NavyDarkBackground
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        ) {
            DetectiveSearchBar(
                query = searchQuery,
                onQueryChange = { searchQuery = it }
            )

            Spacer(modifier = Modifier.height(14.dp))

            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(CaseFilterType.values()) { filter ->
                    val isSelected = selectedFilter == filter
                    FilterChip(
                        selected = isSelected,
                        onClick = { selectedFilter = filter },
                        label = {
                            Text(
                                text = filter.label,
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal
                                )
                            )
                        },
                        colors = FilterChipDefaults.filterChipColors(
                            containerColor = NavyCardSurface,
                            labelColor = TextSecondary,
                            selectedContainerColor = NavyCardHighlight,
                            selectedLabelColor = TextPrimary
                        ),
                        border = FilterChipDefaults.filterChipBorder(
                            borderColor = BorderStroke,
                            selectedBorderColor = BlueAccent,
                            borderWidth = 1.dp,
                            selectedBorderWidth = 1.5.dp,
                            enabled = true,
                            selected = isSelected
                        ),
                        shape = RoundedCornerShape(10.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            if (displayedCases.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 40.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            imageVector = Icons.Default.FolderOff,
                            contentDescription = "Sin casos",
                            tint = TextMuted,
                            modifier = Modifier.size(56.dp)
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = if (searchQuery.isNotBlank()) "No se encontraron casos para '$searchQuery'"
                            else "No hay expedientes en esta sección",
                            style = MaterialTheme.typography.bodyLarge,
                            color = TextSecondary
                        )
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(bottom = 24.dp)
                ) {
                    items(
                        items = displayedCases,
                        key = { it.id }
                    ) { caseItem ->
                        CaseListItem(
                            case = caseItem,
                            onClick = { onNavigateToDetail(caseItem.id) },
                            onDelete = { viewModel.deleteCase(caseItem) }
                        )
                    }
                }
            }
        }
    }
}