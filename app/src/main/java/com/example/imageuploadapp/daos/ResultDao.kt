package com.example.imageuploadapp.daos

import com.example.imageuploadapp.models.Result
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ResultDao {

    private val db = FirebaseFirestore.getInstance()
    private val resultCollection = db.collection("results")

    fun addResult(result: Result?) {
        result.let {
            GlobalScope.launch {
                if (it != null) {
                    resultCollection.document().set(it)
                }
            }
        }
    }

}