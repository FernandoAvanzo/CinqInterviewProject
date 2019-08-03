package cinq.interview.project.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.lifecycle.Observer
import cinq.interview.project.R
import cinq.interview.project.model.Word
import cinq.interview.project.presenter.WordViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlin.system.measureNanoTime
import kotlin.system.measureTimeMillis

class MainActivity : AppCompatActivity() {

    private lateinit var wordViewModel: WordViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val adpter = WordListAdapter(this)
        recyclerView.adapter = adpter
        recyclerView.layoutManager = LinearLayoutManager(this)

        wordViewModel = ViewModelProviders.of(this).get(WordViewModel::class.java)

        wordViewModel.allwords.observe(this, Observer{ words ->
            words?.let {
                val time = measureNanoTime { adpter.setWords(it) }
                val registers = findViewById<TextView>(R.id.textView3)
                registers.text = adpter.itemCount.toString().plus(" Regs")

                val loadTime = findViewById<TextView>(R.id.textView4)
                loadTime.text = time.toString().plus(" NanoS")
            }
        })


        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            val intent = Intent(this@MainActivity, NewWordActivity::class.java)
            startActivityForResult(intent, newWordActivityRequestCode)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == newWordActivityRequestCode && resultCode == Activity.RESULT_OK){

            data?.let {
                val word = Word(it.getStringExtra(NewWordActivity.EXTRA_REPLY))
                wordViewModel.insert(word)
            }

        } else if(requestCode == updateWordActivityRequestCode && resultCode == Activity.RESULT_OK){

            data?.let {
                val newWord = Word(it.getStringExtra(UpdateWordActivity.EXTRA_REPLY))
                val oldWord = Word(it.getStringExtra(UpdateWordActivity.EXTRA_OLD))
                wordViewModel.update(oldWord, newWord)
            }

        } else {
            Toast.makeText(
                applicationContext,
                R.string.empty_not_saved,
                Toast.LENGTH_LONG).show()
        }
    }

    companion object {
        const val newWordActivityRequestCode = 1
        const val updateWordActivityRequestCode = 2
    }

}