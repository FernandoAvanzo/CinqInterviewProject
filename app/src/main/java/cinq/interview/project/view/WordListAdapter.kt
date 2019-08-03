package cinq.interview.project.view

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import cinq.interview.project.R
import cinq.interview.project.model.Word

class WordListAdapter internal constructor(context: Context)
    : RecyclerView.Adapter<WordListAdapter.WordViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var words = emptyList<Word>()

    inner class WordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val wordItemView: TextView = itemView.findViewById(R.id.textView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val itemView = inflater.inflate(R.layout.recyclerview_item, parent, false)

        itemView.setOnClickListener {
            val intent = Intent(inflater.context, UpdateWordActivity::class.java)
            val value: TextView =   it.findViewById(R.id.textView)
            intent.putExtra(UpdateWordActivity.EXTRA_UPDATE, value.text.toString())
            val context : MainActivity = inflater.context as MainActivity
            context.startActivityForResult (intent, MainActivity.updateWordActivityRequestCode)
        }

        return WordViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        val current = words[position]
        holder.wordItemView.text = current.word
    }

    internal fun setWords(words: List<Word>){
        this.words = words
        notifyDataSetChanged()
    }

    override fun getItemCount() = words.size

}