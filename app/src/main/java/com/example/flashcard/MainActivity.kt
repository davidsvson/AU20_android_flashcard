package com.example.flashcard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    val wordList = WordList()
    lateinit var textView : TextView
    var currentWord : Word? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textView = findViewById(R.id.textView)

        loadNewWord()

        textView.setOnClickListener {
            revealTranslation()
        }
    }

    fun loadNewWord() {
        currentWord = wordList.getNewWord()

        textView.text = currentWord?.english
    }

    fun revealTranslation() {
        textView.text = currentWord?.swedish
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event?.action == MotionEvent.ACTION_UP ) {
            loadNewWord()
        }
        return true
    }
}