package cinq.interview.project.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import cinq.interview.project.R

class UpdateWordActivity : AppCompatActivity(){

    private lateinit var editWordView: EditText
    private lateinit var oldWordView: TextView

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_word)
        editWordView = findViewById(R.id.update_word)
        oldWordView = findViewById(R.id.old_word)
        oldWordView.text = intent.getStringExtra(EXTRA_UPDATE)

        val button = findViewById<Button>(R.id.button_update)

        button.setOnClickListener {
            val replyIntent = Intent()

            if(TextUtils.isEmpty(editWordView.text)){
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                val word = editWordView.text.toString()
                replyIntent.putExtra(EXTRA_REPLY, word)
                replyIntent.putExtra(EXTRA_OLD, oldWordView.text.toString())
                setResult(Activity.RESULT_OK, replyIntent)
            }
            finish()
        }
    }

    companion object {
        const val EXTRA_REPLY = "cinq.interview.project.wordlistsql.REPLY"
        const val EXTRA_OLD = "cinq.interview.project.wordlistsql.OLD"
        const val EXTRA_UPDATE = "cinq.interview.project.wordlistsql.UPDATE"
    }
}