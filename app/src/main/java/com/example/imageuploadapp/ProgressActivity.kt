package com.example.imageuploadapp


import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.imageuploadapp.models.Result
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ProgressActivity : AppCompatActivity() {

    private lateinit var adapter: ResultAdapter
    private lateinit var auth: FirebaseAuth
    private val db = FirebaseFirestore.getInstance()
    private val listLiveData :MutableLiveData<List<Result>> = MutableLiveData()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_progress)

        Log.d("PROGRESS ACTIVITY", "PROGRESS ACTIVITY STARTED")
        auth = FirebaseAuth.getInstance()
        setUpRecyclerView()

        listLiveData.observe(this) {
            adapter.list = it
            adapter.notifyDataSetChanged()
        }

    }


    override fun onResume() {
        super.onResume()
        setUpRecyclerView()
        getDataFromFireStore()
    }

    private fun setUpRecyclerView() {
        adapter = ResultAdapter()
        val recyclerViewr = findViewById<RecyclerView>(R.id.recyclerViewR)
        recyclerViewr.adapter = adapter
        recyclerViewr.layoutManager =
            LinearLayoutManagerWrapper(this@ProgressActivity, LinearLayoutManager.VERTICAL, false)
    }

    private fun getDataFromFireStore(){
        db.collection("results").get().addOnCompleteListener {
            if(it.isSuccessful){
                Log.d("MAIN__","success")
                val result = mutableListOf<Result>()
                it.result.documents.forEach{
                    Log.d("MAIN__",it.data.toString())
                    val doc = it.toObject(Result::class.java)
                    if (doc != null) {
                        result.add(doc)
                    }
                }
                listLiveData.value = result
            }
            else{
                Log.d("MAIN__","success")
            }
        }
    }
}