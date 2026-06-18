package com.example.securenotes

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Accede a las preferencias seguras de la aplicación
        val securePrefs = SecurePreferencesManager(this)

        // Verifica si existe una sesión activa.
        // Si el usuario no ha iniciado sesión,
        // se redirige automáticamente a LoginActivity.
        if (!securePrefs.isLoggedIn()) {

            startActivity(
                Intent(this, LoginActivity::class.java)
            )

            // Evita que el usuario pueda regresar a esta pantalla
            // sin autenticarse correctamente.
            finish()
            return
        }

        setContentView(R.layout.activity_main)

        // Referencia al botón de cierre de sesión
        val btnLogout =
            findViewById<Button>(R.id.btnLogout)

        btnLogout.setOnClickListener {

            // Elimina el estado de sesión activa
            securePrefs.logout()

            // Regresa al usuario a la pantalla de inicio de sesión
            startActivity(
                Intent(this, LoginActivity::class.java)
            )

            // Finaliza la actividad actual para impedir
            // el acceso mediante el botón Atrás
            finish()
        }
    }
}