package com.example.flashcard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.widget.TextView
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity() , CoroutineScope {

    private lateinit var job : Job
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    var wordList : WordList? = null
    lateinit var textView : TextView
    var currentWord : Word? = null
    private lateinit var db  : AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        job = Job()
        db = AppDatabase.getInstance(this)
/*
        addNewWord(Word(0, "Save", "Spara"))
        addNewWord(Word(0, "Welcome", "Välkommen"))
        addNewWord(Word(0, "Deferred", "Avaktande"))
*/


        textView = findViewById(R.id.textView)

        //loadNewWord()

        loadAllWords()

        textView.setOnClickListener {
            revealTranslation()
        }

    }

    fun loadAllWords() {
        val words = async(Dispatchers.IO) {
            db.wordDao.getAll()
        }

        launch {
            val list = words.await().toMutableList()
            wordList = WordList(list)
            loadNewWord()
        }

    }


    fun addNewWord(word : Word) {
        launch(Dispatchers.IO) {
            db.wordDao.insert(word)
        }
    }

    fun loadNewWord() {
        currentWord = wordList?.getNewWord()

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