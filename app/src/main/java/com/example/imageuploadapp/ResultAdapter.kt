package com.example.imageuploadapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.imageuploadapp.models.Result
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions

class ResultAdapter (options: FirestoreRecyclerOptions<Result>) : FirestoreRecyclerAdapter<Result, ResultAdapter.ResultViewHolder>(
    options)
{

    class ResultViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val quizName: TextView = itemView.findViewById(R.id.qNameR)
        val dateQuiz: TextView = itemView.findViewById(R.id.dateQuizR)
        val nqr: TextView = itemView.findViewById(R.id.noOfQuesR)
        val marks: TextView = itemView.findViewById(R.id.marks)

    }

    // Firestore listens to data in real time and gets us data automatically
    // We just need to inflate the views and bind data to it.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultViewHolder {
        return ResultViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_result, parent, false)
        )
    }

    // binding data from firestore collections to the views in ViewHolder
    override fun onBindViewHolder(holder: ResultViewHolder, position: Int, model: Result) {
        holder.quizName.text = model.quiz.quizName
        holder.dateQuiz.text = "Date: " + model.quiz.date
        holder.nqr.text = "Total ques: " + model.quiz.noOfQuestions.toString()
        holder.marks.text = "Marks: " + model.marks.toString() +  "/" + model.quiz.noOfQuestions.toString()

    }
}

