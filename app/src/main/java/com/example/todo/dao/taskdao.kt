package com.example.todo.dao

import com.example.todo.models.tasks
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class taskdao {
    val db=FirebaseFirestore.getInstance()
    val auth= Firebase.auth
    val taskcollection=db.collection("tasks")

    fun addTask(text: String){
        GlobalScope.launch {
            val currentUser=auth.currentUser!!.uid

            val currentTime=System.currentTimeMillis()
            val task= tasks(currentUser,text,currentTime)
            taskcollection.document().set(task)
        }
    }
}