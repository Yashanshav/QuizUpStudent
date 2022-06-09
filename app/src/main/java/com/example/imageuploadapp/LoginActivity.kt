package com.example.imageuploadapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.example.imageuploadapp.R.id.loginPassword
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class LoginActivity : AppCompatActivity() {

    private lateinit var username: EditText
    private lateinit var password: EditText
    private lateinit var loginButton: Button
    private lateinit var register: TextView
    private lateinit var pbl: ProgressBar
    private lateinit var auth: FirebaseAuth
    private val tag = "Login Activity Tag"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initializeViews()
        auth = FirebaseAuth.getInstance()
        onStart()

        loginButton.setOnClickListener {
                changeUI()
                signIn(username.text.toString().trim(), password.text.toString().trim())
        }

        register.setOnClickListener {
            goToRegistrationActivity()
        }

    }

    private fun initializeViews() {
        username = findViewById(R.id.loginEmail)
        password = findViewById(loginPassword)
        loginButton = findViewById(R.id.loginButton)
        register = findViewById(R.id.signUp)
        pbl = findViewById(R.id.progressBarLogin)
    }

    private fun goToRegistrationActivity() {
        val registrationActivity = Intent(this, RegistrationActivity :: class.java)
        startActivity(registrationActivity)
        finish()
    }

    private fun changeUI() {
        register.visibility = View.GONE
        loginButton.visibility = View.GONE
        pbl.visibility = View.VISIBLE
    }

    private fun signIn(username: String, password: String) {

        FirebaseAuth.getInstance().signInWithEmailAndPassword(username, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in Student's information
                    Log.d(tag, "signInWithEmail:success")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the Student.
                    Log.w(tag, "signInWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                    updateUI(null)
                }
            }
    }

    private fun updateUI(FirebaseUser: FirebaseUser?) {
        if(FirebaseUser != null) {
            val loginActivityIntent = Intent(this, MainActivity :: class.java)
            startActivity(loginActivityIntent)
            finish()
        }
        else {
            pbl.visibility = View.GONE
            loginButton.visibility = View.VISIBLE
            register.visibility = View.VISIBLE
        }
    }

    public override fun onStart() {
        super.onStart()
        // Check if Student is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if(currentUser != null){
            updateUI(currentUser)
        }
    }

}