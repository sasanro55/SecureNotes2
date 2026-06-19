package com.example.securenotes

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Verifica si ya existe una sesión activa.
        // Si el usuario ya inició sesión previamente,
        // se redirige directamente a MainActivity.
        val securePrefs = SecurePreferencesManager(this)

        if (securePrefs.isLoggedIn()) {

            startActivity(
                Intent(this, MainActivity::class.java)
            )

            finish()
            return
        }

        setContentView(R.layout.activity_login)

        // Referencias a los componentes de la interfaz
        val etUsername = findViewById<EditText>(R.id.etUsername)
        val etPassword = findViewById<EditText>(R.id.etPassword)

        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val btnGoRegister = findViewById<Button>(R.id.btnGoRegister)

        // Permite navegar hacia la pantalla de registro
        // para crear un nuevo usuario
        btnGoRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        // Proceso de autenticación del usuario
        btnLogin.setOnClickListener {

            // Obtiene los datos ingresados en el formulario
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

            // Recupera el usuario almacenado
            val storedUsername =
                securePrefs.getUsername()

            // Recupera el hash de la contraseña almacenada
            val storedPasswordHash =
                securePrefs.getPasswordHash()

            // Genera el hash SHA-256 de la contraseña ingresada
            val inputPasswordHash =
                SecurityUtils.hashPassword(password)

            // Verifica que el usuario y el hash coincidan
            if (
                username == storedUsername &&
                inputPasswordHash == storedPasswordHash
            ) {

                Toast.makeText(
                    this,
                    "Autenticación exitosa",
                    Toast.LENGTH_LONG
                ).show()

                // Guarda el estado de sesión activa
                securePrefs.saveLoginStatus(true)

                // Permite el acceso a la aplicación
                startActivity(
                    Intent(this, MainActivity::class.java)
                )

                finish()

            } else {

                Toast.makeText(
                    this,
                    "Usuario o contraseña incorrectos",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}