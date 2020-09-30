package com.example.flashcard

class WordList() {
    private val words = mutableListOf<Word>()

    init {
        initialize()
    }

    private fun initialize() {
        var word = Word("Hello", "Hej")
        words.add(word)
        words.add(Word("Good bye", "Hej då"))
        words.add(Word("Thank you", "Tack"))
        words.add(Word("Welcome", "Välkommen"))
    }

    fun getNewWord() : Word {
        if (words.size <= 0) {
            initialize()
        }
        val rnd = (0 until words.size).random()
        val word = words.removeAt(rnd)
        return word
    }

}