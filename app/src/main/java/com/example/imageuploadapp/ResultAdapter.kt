package com.example.imageuploadapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.imageuploadapp.models.Result

class ResultAdapter(): RecyclerView.Adapter<ResultAdapter.ResultViewHolder>() {

    var list = listOf<Result>()

    inner class ResultViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val quizName: TextView = itemView.findViewById(R.id.qNameR)
        val dateQuiz: TextView = itemView.findViewById(R.id.dateQuizR)
        val nqr: TextView = itemView.findViewById(R.id.noOfQuesR)
        val marks: TextView = itemView.findViewById(R.id.marks)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultViewHolder {
        return ResultViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_result, parent, false)
        )
    }


    override fun onBindViewHolder(holder: ResultViewHolder, position: Int) {
        holder.quizName.text = list[position].quiz?.quizName
        holder.dateQuiz.text = "Date: " + list[position].quiz?.date
        holder.nqr.text = "Total ques: " + list[position].quiz?.noOfQuestions.toString()
        holder.marks.text = "Marks: " + list[position].marks.toString() +  "/" + list[position].quiz?.noOfQuestions.toString()
    }

    override fun getItemCount(): Int {
        return list.size
    }
}

