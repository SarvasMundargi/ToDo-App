package com.example.todo

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todo.dao.taskdao
import com.example.todo.databinding.ActivityMainBinding
import com.example.todo.models.tasks
import com.example.todo.ui.theme.ToDoTheme
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity(), TaskAdapter.ITaskAdapter {
    private lateinit var binding: ActivityMainBinding
    private lateinit var taskdao: taskdao
    private lateinit var mAdapter: TaskAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLetsgo.setOnClickListener {
            addTask()
        }
        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        taskdao=taskdao()
        val taskcollection=taskdao.taskcollection
        val query=taskcollection.orderBy("createdAt",com.google.firebase.firestore.Query.Direction.ASCENDING)
        val recyclerViewOptions=
            FirestoreRecyclerOptions.Builder<tasks>().setQuery(query,tasks::class.java).build()

        mAdapter= TaskAdapter(recyclerViewOptions,this)
        binding.recyclerview.adapter=mAdapter
        binding.recyclerview.layoutManager=LinearLayoutManager(this)
    }

    private fun addTask() {
        val text=binding.etEnterTasks.text.toString()
        taskdao=taskdao()

        if(text.isEmpty()){
            Toast.makeText(this,"Please enter the task",Toast.LENGTH_SHORT).show()
        }
        else{
            taskdao.addTask(text)
            Toast.makeText(this,"Task Added",Toast.LENGTH_SHORT).show()
            binding.etEnterTasks.text.clear()
        }
    }

    override fun onStart() {
        super.onStart()
        mAdapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        mAdapter.stopListening()
    }

    override fun OndeleteClicked(taskid: String) {
        val taskRef=taskdao.taskcollection.document(taskid)
        taskRef.delete().addOnSuccessListener {
            Toast.makeText(this,"Task is deleted",Toast.LENGTH_SHORT).show()
        }
            .addOnFailureListener {e->
                Toast.makeText(this,"Error deleting the task",Toast.LENGTH_SHORT).show()
            }
    }

}
