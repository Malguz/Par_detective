package com.example.detectiveapp.logic

object CaseValidator {

    val VALID_STATUSES = listOf("En investigación", "Cerrado")

    fun validate(title: String, description: String, date: String, status: String): List<String> {
        val errors = mutableListOf<String>()

        if (title.isBlank()) errors.add("El título no puede estar vacío.")
        if (description.isBlank()) errors.add("La descripción no puede estar vacía.")
        if (date.isBlank()) {
            errors.add("La fecha no puede estar vacía.")
        } else if (!date.matches(Regex("""\d{4}-\d{2}-\d{2}"""))) {
            errors.add("La fecha debe tener el formato AAAA-MM-DD.")
        }
        if (status.isBlank()) {
            errors.add("El estado no puede estar vacío.")
        } else if (status !in VALID_STATUSES) {
            errors.add("El estado debe ser uno de: ${VALID_STATUSES.joinToString(", ")}.")
        }

        return errors
    }

    fun isValid(title: String, description: String, date: String, status: String): Boolean {
        return validate(title, description, date, status).isEmpty()
    }

    fun closeCase(currentStatus: String): String = "Cerrado"

    fun isClosed(status: String): Boolean = status == "Cerrado"
}