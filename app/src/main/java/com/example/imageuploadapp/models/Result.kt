package com.example.imageuploadapp.models

import android.os.Parcelable
import com.google.firebase.firestore.auth.User
import kotlinx.parcelize.Parcelize

@Parcelize
data class Result(
    val rid: String,
    val quiz: Quiz,
    val marks: Int,
    val totalMarks: Int?,
    val givenBy: Student
        ) : Parcelable