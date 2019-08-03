package cinq.interview.project.presenter

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import cinq.interview.project.model.Word
import cinq.interview.project.model.WordRepository
import cinq.interview.project.model.WordRoomDatabase
import kotlinx.coroutines.launch

class WordViewModel(application: Application): AndroidViewModel(application){

    private val repository: WordRepository

    val allwords: LiveData<List<Word>>

    init {
        val wordsdao = WordRoomDatabase.getDatabase(application, viewModelScope).wordDao()
        repository = WordRepository(wordsdao)
        allwords = repository.allWords
    }

    fun insert(word: Word) = viewModelScope.launch {
        repository.insert(word)
    }
}