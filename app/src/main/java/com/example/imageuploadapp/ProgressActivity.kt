package com.example.imageuploadapp


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.imageuploadapp.models.Result
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ProgressActivity : AppCompatActivity() {

    private lateinit var adapter: ResultAdapter
    private lateinit var auth: FirebaseAuth
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_progress)

        Log.d("PROGRESS ACTIVITY", "PROGRESS ACTIVITY STARTED")
        auth = FirebaseAuth.getInstance()

        setUpRecyclerView()

    }

    private fun setUpRecyclerView() {


        val resCollection = db.collection("results")
        val query = resCollection.orderBy("givenBy")
        var recyclerOptions: FirestoreRecyclerOptions<Result>

        GlobalScope.launch(Dispatchers.IO) {
            recyclerOptions = FirestoreRecyclerOptions.Builder<Result>().setQuery(query, Result::class.java).build()
            adapter = ResultAdapter(recyclerOptions)

            val recyclerViewr = findViewById<RecyclerView>(R.id.recyclerViewR)

            recyclerViewr.adapter = adapter
            recyclerViewr.layoutManager =
                LinearLayoutManagerWrapper(this@ProgressActivity, LinearLayoutManager.VERTICAL, false)
        }


//        Log.d("not null", recyclerOptions.toString())

        // Now the recycler options are pass in adapter to display in firestore recycler view


    }
}