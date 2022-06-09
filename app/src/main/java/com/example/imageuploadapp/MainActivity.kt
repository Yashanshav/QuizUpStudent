package com.example.imageuploadapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.imageuploadapp.models.Quiz
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {

    private lateinit var adapter : QuizAdapter
    private val db = FirebaseFirestore.getInstance()
    private lateinit var quizCollection: CollectionReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUpRecyclerView()

        findViewById<Button>(R.id.progressButton).setOnClickListener {
            startActivity(Intent(this, ProgressActivity :: class.java))
        }


    }

    private fun setUpRecyclerView() {

        quizCollection = db.collection("quiz")
        val query = quizCollection.orderBy("quizName")
        val recyclerOptions = FirestoreRecyclerOptions.Builder<Quiz>().setQuery(query, Quiz::class.java).build()

        // Now the recycler options are pass in adapter to display in firestore recycler view


        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        adapter = QuizAdapter(recyclerOptions)
        recyclerView.adapter = adapter
        recyclerView.layoutManager =
            LinearLayoutManagerWrapper(this, LinearLayoutManager.VERTICAL, false)

        adapter.setOnItemClickListener(object : QuizAdapter.ItemClickListener {
            override fun onItemClick(position: Int) {
                // go to attempt quiz activity and give the quiz if everything is good.
//                checkDateAndTime(position)

                val qzz = adapter.getItem(position)
                val intent = Intent(this@MainActivity, AttemptQuizActivity :: class.java)
                intent.putExtra("attemptQuiz", qzz)
                startActivity(intent)

            }
        })


    }

//    private fun checkDateAndTime(position: Int) {
//
//        val qzz = adapter.getItem(position)
//
//        val c = Calendar.getInstance()
//
//        val dateText = DateFormat.format("EEEE, MMM d, yyyy", c).toString()
//        val timeText = DateFormat.format("h:mm a", c).toString()
//
//        var hour: Int
//        if(timeText.length == 8) {
//            hour = timeText.subSequence(0, 2).toString().toInt()
//        }
//        else {
//            hour = timeText[0].toString().toInt()
//        }
//
//        var quizHour: Int
//        if(qzz.time.length == 8) {
//            quizHour = qzz.time.subSequence(0, 2).toString().toInt()
//        }
//        else{
//            quizHour = qzz.time[0].toString().toInt()
//        }
//
//        var quizMinute: Int
//        if(qzz.time.length == 8) {
//            quizMinute = qzz.time.subSequence(3, 5).toString().toInt()
//        }
//        else{
//            quizMinute = qzz.time.subSequence(2,4).toString().toInt()
//        }
//
//        var minute: Int
//        if(timeText.length == 8) {
//            minute = timeText.subSequence(3, 5).toString().toInt()
//        }
//        else {
//            minute = timeText.subSequence(2,4).toString().toInt()
//        }
//
//        if(qzz.date == dateText && quizHour == hour && minute >= quizMinute && minute <= quizMinute + qzz.timeLimit) {
//            val intent = Intent(this, AttemptQuizActivity :: class.java)
//            intent.putExtra("attemptQuiz", qzz)
//            startActivity(intent)
//        }
//        else {
//            Toast.makeText(this, "this quiz is not active", Toast.LENGTH_SHORT).show()
//        }
//
//    }

    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }
}

class LinearLayoutManagerWrapper(context: Context?, orientation: Int, reverseLayout: Boolean) :
    LinearLayoutManager(
        context,
        orientation,
        reverseLayout
    ) {

    override fun supportsPredictiveItemAnimations(): Boolean {
        return false
    }
}