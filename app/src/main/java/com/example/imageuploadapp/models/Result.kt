package com.example.imageuploadapp.models

import android.os.Parcelable
import com.google.firebase.firestore.auth.User
import kotlinx.parcelize.Parcelize

@Parcelize
data class Result(
    val rid: String="",
    val quiz: Quiz? =null,
    val marks: Int=0,
    val totalMarks: Int=0,
    val givenBy: Student?=null
        ) : Parcelable