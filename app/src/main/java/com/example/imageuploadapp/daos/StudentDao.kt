package com.example.imageuploadapp.daos

import com.example.imageuploadapp.models.Student
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class StudentDao {

    private val db = FirebaseFirestore.getInstance()
    private val userCollection = db.collection("students")

    fun addStudent(Student: Student?) {
        Student?.let {
            GlobalScope.launch(Dispatchers.IO) {
                userCollection.document(Student.uid).set(it)
            }
        }
    }

    fun getStudentById(uId: String): Task<DocumentSnapshot> {
        return userCollection.document(uId).get()
    }
}