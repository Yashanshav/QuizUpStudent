package com.example.imageuploadapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.example.imageuploadapp.daos.StudentDao
import com.example.imageuploadapp.models.Student
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
class RegistrationActivity : AppCompatActivity() {

    private lateinit var username: EditText
    private lateinit var password: EditText
    private lateinit var registerButton: Button
    private lateinit var pbr: ProgressBar
    private lateinit var name: EditText
    private lateinit var branch: EditText
    private lateinit var sem: EditText
    private lateinit var regNo: EditText
    private lateinit var auth: FirebaseAuth
    private val tag = "Registration Tag"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        initializeViews()

        auth = FirebaseAuth.getInstance()

        registerButton.setOnClickListener {

            registerButton.visibility = View.GONE
            pbr.visibility = View.VISIBLE

            createAccount(username.text.toString().trim(),
                password.text.toString().trim(),
                name.text.toString().trim(),
                branch.text.toString().trim(),
                sem.text.toString().trim(),
                regNo.text.toString().trim())
        }
    }

    private fun initializeViews() {
        username = findViewById(R.id.registerEmail)
        password = findViewById(R.id.registerPassword)
        registerButton = findViewById(R.id.registerButton)
        pbr = findViewById(R.id.progressBarRegister)
        name = findViewById(R.id.registerName)
        branch = findViewById(R.id.registerBranch)
        sem = findViewById(R.id.registerSemester)
        regNo = findViewById(R.id.registerRegNo)
    }

    private fun createAccount(username: String, password: String, name: String?,
                              branch: String?, sem: String?, regNo: String?) {

        auth.createUserWithEmailAndPassword(username, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {

                    Log.d(tag, getString(R.string.SuccessfulRegistration))

                    // Sign in success, update UI with the signed-in Student's information
                    val user = task.result.user
                    updateUI(user)
                    Toast.makeText(baseContext, getString(R.string.SuccessRegister),
                        Toast.LENGTH_SHORT).show()

                 // add Student to firebase DB
                    val studentDao = StudentDao()
                    val studentInfo = Student(user?.uid.toString(), name, branch, sem, regNo)
                    studentDao.addStudent(studentInfo)


                    startActivity(Intent(this, LoginActivity :: class.java))
                } else {
                    // If sign in fails, display a message to the Student.
                    Log.w(tag, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                    updateUI(null)
                }
            }
    }

    private fun updateUI(FirebaseUser: FirebaseUser?) {
        if(FirebaseUser != null) {
            startActivity(Intent(this, LoginActivity :: class.java))
            finish()
        }
        else {
            pbr.visibility = View.GONE
            registerButton.visibility = View.VISIBLE
        }
    }
}