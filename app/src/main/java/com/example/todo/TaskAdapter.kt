package com.example.todo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.models.tasks
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions

class TaskAdapter(options: FirestoreRecyclerOptions<tasks>,val listener: ITaskAdapter) : FirestoreRecyclerAdapter<tasks, TaskAdapter.TaskViewHolder>(
    options
) {

    class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val task : TextView=itemView.findViewById(R.id.tv_task)
        val delete: ImageView=itemView.findViewById(R.id.iv_delete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.items_task,parent,false)
        val viewHolder=TaskViewHolder(view)
        viewHolder.delete.setOnClickListener{
            listener.OndeleteClicked(snapshots.getSnapshot(viewHolder.adapterPosition).id)
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int, model: tasks) {
        holder.task.text=model.task
        holder.delete.setImageDrawable(ContextCompat.getDrawable(holder.delete.context,R.drawable.delete))
    }

    interface ITaskAdapter{
        fun OndeleteClicked(taskid: String)
    }
}