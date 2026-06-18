package com.example.securenotes

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

// Clase encargada de administrar el almacenamiento seguro
// de información sensible utilizando EncryptedSharedPreferences.
class SecurePreferencesManager(context: Context) {

    // Genera una clave maestra utilizando AES-256-GCM.
    // Esta clave es utilizada para proteger los datos almacenados
    // dentro de las preferencias cifradas.
    private val masterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()


    // Crea un almacenamiento seguro donde tanto las claves
    // como los valores son cifrados automáticamente.
    //
    // AES256_SIV: protege los nombres de las claves.
    // AES256_GCM: protege los valores almacenados.
    private val sharedPreferences =
        EncryptedSharedPreferences.create(
            context,
            "secure_prefs",
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )


    // Guarda el usuario y el hash de la contraseña.
    //
    // La contraseña no se almacena directamente,
    // solamente se guarda el hash generado mediante SHA-256.
    fun saveUser(username: String, passwordHash: String) {

        sharedPreferences.edit()
            .putString("username", username)
            .putString("password", passwordHash)
            .apply()
    }


    // Recupera el nombre de usuario almacenado de forma segura.
    fun getUsername(): String? {

        return sharedPreferences.getString("username", null)
    }


    // Recupera el hash de la contraseña almacenada.
    //
    // Este valor se utiliza durante el login para comparar
    // la contraseña ingresada con la versión protegida.
    fun getPasswordHash(): String? {

        return sharedPreferences.getString("password", null)
    }


    // Guarda el estado de autenticación del usuario.
    //
    // Permite controlar si el usuario tiene una sesión activa
    // antes de permitir el acceso a MainActivity.
    fun saveLoginStatus(isLoggedIn: Boolean) {

        sharedPreferences.edit()
            .putBoolean("logged_in", isLoggedIn)
            .apply()
    }


    // Verifica si existe una sesión activa.
    //
    // Retorna false cuando no hay un usuario autenticado,
    // evitando accesos no autorizados.
    fun isLoggedIn(): Boolean {

        return sharedPreferences.getBoolean("logged_in", false)
    }


    // Finaliza la sesión actual del usuario.
    //
    // Cambia el estado de autenticación a falso,
    // bloqueando nuevamente el acceso a la aplicación.
    fun logout() {

        sharedPreferences.edit()
            .putBoolean("logged_in", false)
            .apply()
    }
}