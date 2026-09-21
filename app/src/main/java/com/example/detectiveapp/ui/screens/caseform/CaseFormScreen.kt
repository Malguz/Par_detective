package com.example.detectiveapp.ui.screens.caseform

import android.app.DatePickerDialog
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.detectiveapp.logic.CaseViewModel
import com.example.detectiveapp.room.Case
import com.example.detectiveapp.ui.theme.BlueAccent
import com.example.detectiveapp.ui.theme.NavyCardHighlight
import com.example.detectiveapp.ui.theme.NavyCardSurface
import com.example.detectiveapp.ui.theme.NavyDarkBackground
import com.example.detectiveapp.ui.theme.TextMuted
import com.example.detectiveapp.ui.theme.TextPrimary
import com.example.detectiveapp.ui.theme.TextSecondary
import kotlinx.coroutines.launch
import java.util.Calendar
import com.example.detectiveapp.ui.theme.BorderStroke as BorderColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CaseFormScreen(
    viewModel: CaseViewModel,
    caseId: Int?,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    val isEditing = caseId != null && caseId > 0
    val cases by viewModel.cases.collectAsState(initial = emptyList())
    val errorMessages by viewModel.errorMessage.collectAsState(initial = emptyList())

    val existingCase = remember(cases, caseId) {
        if (isEditing) cases.find { it.id == caseId } else null
    }

    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    var status by remember { mutableStateOf("En investigación") }
    var isStatusMenuExpanded by remember { mutableStateOf(false) }

    LaunchedEffect(existingCase) {
        existingCase?.let {
            title = it.title
            description = it.description
            date = it.date
            status = it.status
        }
    }

    LaunchedEffect(errorMessages) {
        if (errorMessages.isNotEmpty()) {
            scope.launch {
                snackbarHostState.showSnackbar(errorMessages.joinToString("\n"))
            }
        }
    }

    val calendar = Calendar.getInstance()
    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            date = String.format("%04d-%02d-%02d", year, month + 1, dayOfMonth)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = NavyDarkBackground,
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = if (isEditing) "Editar caso" else "Nuevo expediente",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                        color = TextPrimary
                    )
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
                .verticalScroll(rememberScrollState())
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            Column {
                Text(
                    "Título del caso",
                    style = MaterialTheme.typography.labelLarge,
                    color = TextPrimary
                )
                Spacer(modifier = Modifier.height(6.dp))
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    placeholder = { Text("Ej. Robo en la galería", color = TextMuted) },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = NavyCardSurface,
                        unfocusedContainerColor = NavyCardSurface,
                        focusedBorderColor = BlueAccent,
                        unfocusedBorderColor = BorderColor,
                        focusedTextColor = TextPrimary,
                        unfocusedTextColor = TextPrimary
                    )
                )
            }

            Column {
                Text(
                    "Descripción",
                    style = MaterialTheme.typography.labelLarge,
                    color = TextPrimary
                )
                Spacer(modifier = Modifier.height(6.dp))
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    placeholder = {
                        Text(
                            "Detalla las circunstancias del crimen...",
                            color = TextMuted
                        )
                    },
                    minLines = 4,
                    maxLines = 6,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = NavyCardSurface,
                        unfocusedContainerColor = NavyCardSurface,
                        focusedBorderColor = BlueAccent,
                        unfocusedBorderColor = BorderColor,
                        focusedTextColor = TextPrimary,
                        unfocusedTextColor = TextPrimary
                    )
                )
            }

            Column {
                Text(
                    "Fecha de inicio",
                    style = MaterialTheme.typography.labelLarge,
                    color = TextPrimary
                )
                Spacer(modifier = Modifier.height(6.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { datePickerDialog.show() }
                ) {
                    OutlinedTextField(
                        value = date,
                        onValueChange = {},
                        enabled = false,
                        placeholder = { Text("Seleccionar fecha (AAAA-MM-DD)", color = TextMuted) },
                        trailingIcon = {
                            Icon(
                                Icons.Default.CalendarToday,
                                contentDescription = null,
                                tint = TextSecondary
                            )
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            disabledContainerColor = NavyCardSurface,
                            disabledBorderColor = BorderColor,
                            disabledTextColor = TextPrimary,
                            disabledTrailingIconColor = TextSecondary
                        )
                    )
                }
            }

            Column {
                Text(
                    "Estado de la investigación",
                    style = MaterialTheme.typography.labelLarge,
                    color = TextPrimary
                )
                Spacer(modifier = Modifier.height(6.dp))
                Box(modifier = Modifier.fillMaxWidth()) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { isStatusMenuExpanded = true }
                    ) {
                        OutlinedTextField(
                            value = status,
                            onValueChange = {},
                            enabled = false,
                            trailingIcon = {
                                Icon(
                                    Icons.Default.ArrowDropDown,
                                    contentDescription = null,
                                    tint = TextSecondary
                                )
                            },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                disabledContainerColor = NavyCardSurface,
                                disabledBorderColor = BorderColor,
                                disabledTextColor = TextPrimary,
                                disabledTrailingIconColor = TextSecondary
                            )
                        )
                    }

                    DropdownMenu(
                        expanded = isStatusMenuExpanded,
                        onDismissRequest = { isStatusMenuExpanded = false },
                        modifier = Modifier.background(NavyCardHighlight)
                    ) {
                        DropdownMenuItem(
                            text = { Text("En investigación", color = TextPrimary) },
                            onClick = {
                                status = "En investigación"
                                isStatusMenuExpanded = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Cerrado", color = TextPrimary) },
                            onClick = {
                                status = "Cerrado"
                                isStatusMenuExpanded = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

// Botón Guardar en CaseFormScreen.kt
            Button(
                onClick = {
                    if (title.isBlank() || description.isBlank() || date.isBlank()) {
                        scope.launch {
                            snackbarHostState.showSnackbar("Por favor completa todos los campos")
                        }
                        return@Button
                    }

                    if (isEditing && existingCase != null) {
                        val updatedCase = existingCase.copy(
                            title = title.trim(),
                            description = description.trim(),
                            date = date.trim(),
                            status = status
                        )
                        viewModel.updateCase(updatedCase)
                    } else {
                        // Se pasan sin nombres nombrados en español para evitar discrepancias de firma
                        viewModel.createCase(
                            title.trim(),
                            description.trim(),
                            date.trim(),
                            status
                        )
                    }
                    onNavigateBack()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = BlueAccent)
            ) {
                Icon(Icons.Default.Save, contentDescription = null, tint = TextPrimary)
                Spacer(modifier = Modifier.padding(start = 8.dp))
                Text(
                    text = if (isEditing) "Actualizar expediente" else "Registrar caso",
                    style = MaterialTheme.typography.labelLarge,
                    color = TextPrimary
                )
            }

        }
    }
}
