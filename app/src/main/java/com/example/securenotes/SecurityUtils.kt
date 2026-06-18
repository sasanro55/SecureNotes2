package com.example.securenotes

import java.security.MessageDigest

// Clase utilitaria encargada de las funciones de seguridad
object SecurityUtils {

    /**
     * Genera un hash SHA-256 a partir de una contraseña.
     *
     * La contraseña original nunca se almacena directamente.
     * En su lugar, se guarda una representación hash irreversible,
     * lo que reduce el riesgo de exposición de credenciales.
     */
    fun hashPassword(password: String): String {

        // Convierte la contraseña en un arreglo de bytes
        // y aplica el algoritmo SHA-256
        val bytes = MessageDigest
            .getInstance("SHA-256")
            .digest(password.toByteArray())

        // Convierte el resultado a una cadena hexadecimal
        // para facilitar su almacenamiento y comparación
        return bytes.joinToString("") {
            "%02x".format(it)
        }
    }
}