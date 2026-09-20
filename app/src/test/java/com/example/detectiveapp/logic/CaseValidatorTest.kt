package com.example.detectiveapp.logic

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.Assert.*
import com.example.detectiveapp.logic.CaseValidator
class CaseValidatorTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }
    @Test
    fun `caso valido no genera errores`() {
        val errors = CaseValidator.validate("Robo en la galería", "Robo de piezas de arte", "2026-03-12", "En investigación")
        assertTrue(errors.isEmpty())
    }
    @Test
    fun `titulo vacio genera error`() {
        val errors = CaseValidator.validate("", "Descripcion", "2026-03-12", "Cerrado")
        assertTrue(errors.contains("El título no puede estar vacío."))
    }
    @Test
    fun `fecha con formato invalido genera error`() {
        val errors = CaseValidator.validate("Titulo", "Descripcion", "12-03-2026", "Cerrado")
        assertTrue(errors.any { it.contains("formato") })
    }
    @Test
    fun `estado invalido genera error`() {
        val errors = CaseValidator.validate("Titulo", "Descripcion", "2026-03-12", "Pendiente")
        assertTrue(errors.any { it.contains("estado") })
    }
    @Test
    fun `cerrar caso cambia el estado a Cerrado`() {
        assertEquals("Cerrado", CaseValidator.closeCase("En investigación"))
    }
    @Test
    fun `estaCerrado detecta correctamente`() {
        assertTrue(CaseValidator.isClosed("Cerrado"))
        assertFalse(CaseValidator.isClosed("En investigación"))
    }
}