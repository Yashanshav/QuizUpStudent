package com.example.imageuploadapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.imageuploadapp.models.Quiz
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions

class QuizAdapter (options: FirestoreRecyclerOptions<Quiz>) : FirestoreRecyclerAdapter<Quiz, QuizAdapter.QuizViewHolder>(
    options
) {

    private lateinit var mListener: ItemClickListener

    interface ItemClickListener
    {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: ItemClickListener) {
        mListener = listener
    }

    class QuizViewHolder(itemView: View, listener: ItemClickListener): RecyclerView.ViewHolder(itemView) {
        val quizName: TextView = itemView.findViewById(R.id.qName)
        val dateQuiz: TextView = itemView.findViewById(R.id.dateQuiz)
        val startTimeQuiz: TextView = itemView.findViewById(R.id.startTimeQuiz)
        val timeLimitQuiz: TextView = itemView.findViewById(R.id.timeLimitQuiz)

        init {
            itemView.setOnClickListener {
                listener.onItemClick(absoluteAdapterPosition)
            }
        }

    }

    // Firestore listens to data in real time and gets us data automatically
    // We just need to inflate the views and bind data to it.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuizViewHolder {
        return QuizViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_quiz, parent, false),
            mListener
        )
    }

    // binding data from firestore collections to the views in ViewHolder
    override fun onBindViewHolder(holder: QuizViewHolder, position: Int, model: Quiz) {
        holder.quizName.text = model.quizName
        holder.dateQuiz.text = model.date
        holder.startTimeQuiz.text = model.time
        holder.timeLimitQuiz.text = model.timeLimit.toString()

    }
}

