package com.example.todo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.todo.databinding.ActivitySigninBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.auth.FirebaseAuthCredentialsProvider
import com.google.firebase.ktx.Firebase


class Signin : AppCompatActivity() {
    private lateinit var binding: ActivitySigninBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivitySigninBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth=FirebaseAuth.getInstance()

        binding.btnLogin.setOnClickListener {
            login()
        }

        binding.tvSignup.setOnClickListener {
            startActivity(Intent(this,Signup::class.java))
            finish()
        }

    }

    private fun login() {
        val email=binding.etEmail.text.toString()
        val password=binding.etPassword.text.toString()

        if(email.isBlank() || password.isBlank()){
            Toast.makeText(this,"Please fill all the Credentials",Toast.LENGTH_SHORT).show()
        }

        else{
            auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this){
                if(it.isSuccessful){
                    Toast.makeText(this,"Logged In",Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this,MainActivity::class.java))
                    finish()
                }
                else{
                    Toast.makeText(this,"Not proper credentials",Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}