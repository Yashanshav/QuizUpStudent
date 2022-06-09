package com.example.imageuploadapp.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Student (
    val uid: String = "",
    val name: String? = "",
    val branch: String? = "",
    val semester: String? = "",
    val regNo: String? = ""
        ) : Parcelable