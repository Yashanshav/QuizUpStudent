package com.example.imageuploadapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.example.imageuploadapp.daos.ResultDao
import com.example.imageuploadapp.daos.StudentDao
import com.example.imageuploadapp.models.Questions
import com.example.imageuploadapp.models.Quiz
import com.example.imageuploadapp.models.Result
import com.example.imageuploadapp.models.Student
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AttemptQuizActivity : AppCompatActivity() {

    private lateinit var radioGroup: RadioGroup
    private lateinit var o1: RadioButton
    private lateinit var o2: RadioButton
    private lateinit var o3: RadioButton
    private lateinit var o4: RadioButton
    private lateinit var nButton: Button
    private lateinit var backButton: Button
    private lateinit var cqtv: TextView
    private lateinit var name: TextView
    private lateinit var question: TextView
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_attempt_quiz)

        initializeViews()

        var currQ = 1
        val qz = intent.getParcelableExtra<Quiz>("attemptQuiz")
        val value = qz?.noOfQuestions
        Log.i("value", value.toString())
        val questionList = qz?.Questions
        fillUI(currQ)
        val selectedOptions = ArrayList<String>()

        // Get radio group selected status and text using button click event
        nButton.setOnClickListener{
            // Get the checked radio button id from radio group
            val id: Int = radioGroup.checkedRadioButtonId
            if (id!=-1){ // If any radio button checked from radio group
                // Get the instance of radio button using id
                val radio:RadioButton = findViewById(id)



                Log.i("selected Option", radio.toString())
                selectedOptions.add(radio.toString())
                currQ++

                if(currQ == value!!.toInt() ) {
                    nButton.text = getString(R.string.submit)
                }

                if(currQ > value.toInt()) {
                    val marks = calculateMarks(selectedOptions, questionList)

                    auth = FirebaseAuth.getInstance()
                    val currentuser = auth.currentUser
                    var result: Result? = null

                    GlobalScope.launch(Dispatchers.IO) {
                        val userDao = StudentDao()
                        val userSnapshot = userDao.getStudentById(currentuser!!.uid)

                        userSnapshot.addOnCompleteListener {
                            val users = userSnapshot.result.toObject(Student :: class.java)

                            result = Result(currentuser.uid, qz, marks, questionList?.size, users!!)
                            ResultDao().addResult(result)
                        }
                    }

                    Toast.makeText(this, "Quiz completed successfully", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MainActivity :: class.java)
                    intent.putExtra("result", result)
                    startActivity(intent)
                    finish()
                }
                else {
                    fillUI(currQ)
                }
            }

        }

        backButton.setOnClickListener {
            fillUI(--currQ)
            cqtv.text = "Question $currQ of $value"
            if(currQ == 1) {
                backButton.visibility = View.GONE
            }
        }
    }

    private fun initializeViews() {
        radioGroup = findViewById(R.id.radio_group)
        o1 = findViewById(R.id.option1)
        o2 = findViewById(R.id.option2)
        o3 = findViewById(R.id.option3)
        o4 = findViewById(R.id.option4)
        nButton = findViewById(R.id.nextButton)
        name = findViewById(R.id.createQuizText2)
        cqtv = findViewById(R.id.questionCreateNumber)
        question = findViewById(R.id.question)
        backButton = findViewById(R.id.backButton)
    }

    private fun calculateMarks(selectedOptions: ArrayList<String>, questionList: ArrayList<Questions>?): Int {
        var marks = 0
        if (questionList != null) {
            for(i in 0 until questionList.size) {
                if(selectedOptions[i].equals(questionList[i])) marks++
            }
        }
        return marks
    }

    private fun fillUI(currQ: Int) {
        val qz = intent.getParcelableExtra<Quiz>("attemptQuiz")
        val values = qz?.noOfQuestions
        cqtv.text = "Question $currQ of $values"
        name.text = qz?.quizName
        val questionList = qz?.Questions
        question.text = questionList?.get(currQ - 1)?.ques // error
        o1.text = questionList?.get(currQ - 1)?.options?.get(0)
        o2.text = questionList?.get(currQ - 1)?.options?.get(1)
        o3.text = questionList?.get(currQ - 1)?.options?.get(2)
        o4.text = questionList?.get(currQ - 1)?.options?.get(3)
        radioGroup.clearCheck()
    }
}