package com.example.detectiveapp.ui.screens.casedetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.detectiveapp.logic.CaseDetailViewModel
import com.example.detectiveapp.logic.CaseDetailViewModelFactory
import com.example.detectiveapp.logic.CaseViewModel
import com.example.detectiveapp.ui.components.StatusBadge
import com.example.detectiveapp.ui.theme.BlueAccent
import com.example.detectiveapp.ui.theme.NavyCardSurface
import com.example.detectiveapp.ui.theme.NavyDarkBackground
import com.example.detectiveapp.ui.theme.TextMuted
import com.example.detectiveapp.ui.theme.TextPrimary
import com.example.detectiveapp.ui.theme.TextSecondary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CaseDetailScreen(
    caseId: Int,
    caseViewModel: CaseViewModel,
    onNavigateBack: () -> Unit,
    onNavigateToEdit: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val detailViewModel: CaseDetailViewModel = viewModel(
        factory = CaseDetailViewModelFactory(context, caseId)
    )

    val allCases by caseViewModel.cases.collectAsState(initial = emptyList())
    val currentCase = allCases.find { it.id == caseId }

    val findings by detailViewModel.findings.collectAsState(initial = emptyList())
    val evidenceList by detailViewModel.evidence.collectAsState(initial = emptyList())

    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val tabs = listOf("Resumen", "Hallazgos", "Evidencias", "Cierre")

    if (currentCase == null) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(NavyDarkBackground),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = BlueAccent)
        }
        return
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = NavyDarkBackground,
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(end = 12.dp)
                    ) {
                        Text(
                            text = currentCase.title,
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = TextPrimary,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.weight(1f, fill = false)
                        )
                        Spacer(modifier = Modifier.size(10.dp))
                        StatusBadge(status = currentCase.status)
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Volver",
                            tint = TextPrimary
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
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.CalendarToday,
                    contentDescription = null,
                    tint = TextMuted,
                    modifier = Modifier.size(14.dp)
                )
                Spacer(modifier = Modifier.size(6.dp))
                Text(
                    text = "Apertura: ${currentCase.date}",
                    style = MaterialTheme.typography.labelSmall,
                    color = TextMuted
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            TabRow(
                selectedTabIndex = selectedTabIndex,
                containerColor = NavyCardSurface,
                contentColor = TextPrimary,
                indicator = { tabPositions ->
                    TabRowDefaults.SecondaryIndicator(
                        modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                        color = BlueAccent
                    )
                }
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTabIndex == index,
                        onClick = { selectedTabIndex = index },
                        text = {
                            Text(
                                text = title,
                                style = MaterialTheme.typography.labelMedium.copy(
                                    fontWeight = if (selectedTabIndex == index) FontWeight.Bold else FontWeight.Normal
                                ),
                                color = if (selectedTabIndex == index) TextPrimary else TextSecondary
                            )
                        }
                    )
                }
            }

            Box(modifier = Modifier.fillMaxSize()) {
                when (selectedTabIndex) {
                    0 -> ResumenTab(
                        case = currentCase,
                        recentFindings = findings,
                        onEditCase = { onNavigateToEdit(currentCase.id) },
                        onAddFindingClick = { selectedTabIndex = 1 }
                    )
                    1 -> HallazgosTab(
                        findings = findings,
                        onAddFinding = { desc, date ->
                            detailViewModel.addFinding(desc, date)
                        }
                    )
                    2 -> EvidenciasTab(
                        evidenceList = evidenceList,
                        onAddEvidence = { colDate, desc, loc, imp ->
                            detailViewModel.addEvidence(colDate, desc, loc, imp)
                        }
                    )
                    3 -> CierreTab(
                        case = currentCase,
                        onCloseCase = {
                            detailViewModel.closeCase(currentCase)
                        }
                    )
                }
            }
        }
    }
}