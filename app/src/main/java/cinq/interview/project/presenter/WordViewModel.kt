package cinq.interview.project.presenter

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import cinq.interview.project.model.Word
import cinq.interview.project.model.WordRepository
import cinq.interview.project.model.WordRoomDatabase
import kotlinx.coroutines.CoroutineScope
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

    fun update(oldword: Word, newword: Word){
        asyncUpdate(oldword,newword,repository,viewModelScope).execute()
    }

    companion object {
        private class asyncUpdate(
                private val oldword: Word,
                private val newword: Word,
                private val repository: WordRepository,
                private val scope: CoroutineScope
        )
            : AsyncTask<String, String, String>() {

            override fun doInBackground(vararg params: String?): String {
                scope.launch {
                    repository.delete(oldword)
                    repository.insert(newword)
                }
                return ""
            }
        }
    }

}