package com.example.detectiveapp.logic

object CaseValidator {

    val ESTADOS_VALIDOS = listOf("En investigación", "Cerrado")

    fun validar(titulo: String, descripcion: String, fecha: String, estado: String): List<String> {
        val errores = mutableListOf<String>()

        if (titulo.isBlank()) errores.add("El título no puede estar vacío.")
        if (descripcion.isBlank()) errores.add("La descripción no puede estar vacía.")
        if (fecha.isBlank()) {
            errores.add("La fecha no puede estar vacía.")
        } else if (!fecha.matches(Regex("""\d{4}-\d{2}-\d{2}"""))) {
            errores.add("La fecha debe tener el formato AAAA-MM-DD.")
        }
        if (estado.isBlank()) {
            errores.add("El estado no puede estar vacío.")
        } else if (estado !in ESTADOS_VALIDOS) {
            errores.add("El estado debe ser uno de: ${ESTADOS_VALIDOS.joinToString(", ")}.")
        }

        return errores
    }

    fun esValido(titulo: String, descripcion: String, fecha: String, estado: String): Boolean {
        return validar(titulo, descripcion, fecha, estado).isEmpty()
    }

    fun cerrarCaso(estadoActual: String): String = "Cerrado"

    fun estaCerrado(estado: String): Boolean = estado == "Cerrado"
}