package my.progekt.wordsinword2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ResultListRVAdapter: RecyclerView.Adapter<ResultListRVAdapter.MyViewHolder>() {
    private var tags = mutableListOf<String>()

    fun setData(newTags: MutableList<String>) {
        tags.clear()
        tags.addAll(newTags)
        notifyDataSetChanged()
    }

    inner class MyViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val tagTV: TextView = view.findViewById(R.id.itemTextView) as TextView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return tags.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.tagTV.text = tags[position]
    }
}