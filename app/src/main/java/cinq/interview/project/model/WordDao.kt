package cinq.interview.project.model

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface WordDao{

    @Query("SELECT * from word_table ORDER BY word ASC")
    fun getAllWords(): LiveData<List<Word>>

    @Insert
    suspend fun insert(word: Word)

    @Query("DELETE FROM word_table")
    fun deleteAll()
}