package com.example.detectiveapp.ui.screens.casedetail

import android.app.DatePickerDialog
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.detectiveapp.room.Evidence
import com.example.detectiveapp.ui.theme.BlueAccent
import com.example.detectiveapp.ui.theme.NavyCardHighlight
import com.example.detectiveapp.ui.theme.NavyCardSurface
import com.example.detectiveapp.ui.theme.TextMuted
import com.example.detectiveapp.ui.theme.TextPrimary
import com.example.detectiveapp.ui.theme.TextSecondary
import java.util.Calendar
import com.example.detectiveapp.ui.theme.BorderStroke as BorderColor

@Composable
fun EvidenciasTab(
    evidenceList: List<Evidence>,
    onAddEvidence: (collectionDate: String, description: String, location: String, importance: String) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var description by remember { mutableStateOf("") }
    var collectionDate by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var importance by remember { mutableStateOf("Alta") }
    var isImportanceMenuExpanded by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    val importanceOptions = listOf("Alta", "Media", "Baja")

    val calendar = Calendar.getInstance()
    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            collectionDate = String.format("%04d-%02d-%02d", year, month + 1, dayOfMonth)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
        contentPadding = PaddingValues(bottom = 24.dp)
    ) {
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = NavyCardSurface),
                border = BorderStroke(1.dp, BorderColor)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        text = "Registrar nueva evidencia",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = TextPrimary
                    )

                    OutlinedTextField(
                        value = description,
                        onValueChange = {
                            description = it
                            errorMessage = null
                        },
                        placeholder = { Text("Descripción del objeto o indicio...", color = TextMuted) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = NavyCardSurface,
                            unfocusedContainerColor = NavyCardSurface,
                            focusedBorderColor = BlueAccent,
                            unfocusedBorderColor = BorderColor,
                            focusedTextColor = TextPrimary,
                            unfocusedTextColor = TextPrimary
                        )
                    )

                    OutlinedTextField(
                        value = location,
                        onValueChange = {
                            location = it
                            errorMessage = null
                        },
                        placeholder = { Text("Lugar del hallazgo (ej. Callejón norte)", color = TextMuted) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = NavyCardSurface,
                            unfocusedContainerColor = NavyCardSurface,
                            focusedBorderColor = BlueAccent,
                            unfocusedBorderColor = BorderColor,
                            focusedTextColor = TextPrimary,
                            unfocusedTextColor = TextPrimary
                        )
                    )

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { datePickerDialog.show() }
                    ) {
                        OutlinedTextField(
                            value = collectionDate,
                            onValueChange = {},
                            enabled = false,
                            placeholder = { Text("Fecha de recolección", color = TextMuted) },
                            trailingIcon = {
                                Icon(
                                    imageVector = Icons.Default.CalendarToday,
                                    contentDescription = null,
                                    tint = TextSecondary
                                )
                            },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(10.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                disabledContainerColor = NavyCardSurface,
                                disabledBorderColor = BorderColor,
                                disabledTextColor = TextPrimary,
                                disabledTrailingIconColor = TextSecondary
                            )
                        )
                    }

                    Box(modifier = Modifier.fillMaxWidth()) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { isImportanceMenuExpanded = true }
                        ) {
                            OutlinedTextField(
                                value = "Importancia: $importance",
                                onValueChange = {},
                                enabled = false,
                                trailingIcon = {
                                    Icon(
                                        imageVector = Icons.Default.ArrowDropDown,
                                        contentDescription = null,
                                        tint = TextSecondary
                                    )
                                },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(10.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    disabledContainerColor = NavyCardSurface,
                                    disabledBorderColor = BorderColor,
                                    disabledTextColor = TextPrimary,
                                    disabledTrailingIconColor = TextSecondary
                                )
                            )
                        }

                        DropdownMenu(
                            expanded = isImportanceMenuExpanded,
                            onDismissRequest = { isImportanceMenuExpanded = false },
                            modifier = Modifier.background(NavyCardHighlight)
                        ) {
                            importanceOptions.forEach { opt ->
                                DropdownMenuItem(
                                    text = { Text(opt, color = TextPrimary) },
                                    onClick = {
                                        importance = opt
                                        isImportanceMenuExpanded = false
                                    }
                                )
                            }
                        }
                    }

                    if (errorMessage != null) {
                        Text(
                            text = errorMessage!!,
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }

                    Button(
                        onClick = {
                            if (description.isBlank() || collectionDate.isBlank() || location.isBlank()) {
                                errorMessage = "Completa todos los campos de evidencia."
                            } else {
                                onAddEvidence(collectionDate.trim(), description.trim(), location.trim(), importance)
                                description = ""
                                collectionDate = ""
                                location = ""
                                importance = "Alta"
                                errorMessage = null
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = BlueAccent)
                    ) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = null)
                        Spacer(modifier = Modifier.padding(start = 6.dp))
                        Text(text = "Registrar evidencia", color = TextPrimary)
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Cadena de custodia (${evidenceList.size})",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = TextPrimary
            )
        }

        if (evidenceList.isEmpty()) {
            item {
                Text(
                    text = "No se han fichado evidencias físicas.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextMuted,
                    modifier = Modifier.padding(vertical = 12.dp)
                )
            }
        } else {
            items(
                items = evidenceList,
                key = { it.id }
            ) { evidence ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = NavyCardSurface),
                    border = BorderStroke(1.dp, BorderColor)
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Ficha: ${evidence.collection_date}",
                                style = MaterialTheme.typography.labelSmall,
                                color = BlueAccent
                            )
                            Text(
                                text = "Relevancia: ${evidence.importance}",
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                color = TextSecondary
                            )
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = evidence.description,
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                            color = TextPrimary
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Place,
                                contentDescription = null,
                                tint = TextMuted,
                                modifier = Modifier.height(14.dp)
                            )
                            Text(
                                text = evidence.location,
                                style = MaterialTheme.typography.bodySmall,
                                color = TextMuted
                            )
                        }
                    }
                }
            }
        }
    }
}