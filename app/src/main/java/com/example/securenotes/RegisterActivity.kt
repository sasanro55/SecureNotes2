package com.example.securenotes

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // Referencias a los controles de la interfaz
        val etUsername = findViewById<EditText>(R.id.etUsername)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val btnRegister = findViewById<Button>(R.id.btnRegister)

        btnRegister.setOnClickListener {

            // Obtiene los datos ingresados por el usuario
            val username = etUsername.text.toString().trim()
            val password = etPassword.text.toString().trim()

            // Valida que los campos no estén vacíos
            if (username.isEmpty() || password.isEmpty()) {

                Toast.makeText(
                    this,
                    "Complete todos los campos",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            // Valida que la contraseña tenga una longitud mínima
            if (password.length < 6) {

                Toast.makeText(
                    this,
                    "La contraseña debe tener al menos 6 caracteres",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            // Genera el hash SHA-256 de la contraseña
            // para evitar almacenarla en texto plano
            val hashedPassword =
                SecurityUtils.hashPassword(password)

            // Crea una instancia del administrador
            // de preferencias cifradas
            val securePrefs =
                SecurePreferencesManager(this)

            // Guarda el usuario y el hash de la contraseña
            // utilizando EncryptedSharedPreferences
            securePrefs.saveUser(
                username,
                hashedPassword
            )

            // Muestra mensaje de éxito
            Toast.makeText(
                this,
                "Usuario registrado correctamente",
                Toast.LENGTH_LONG
            ).show()

            // Regresa a la pantalla de inicio de sesión
            finish()
        }
    }
}