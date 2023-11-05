package com.example.todo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.todo.databinding.ActivitySigninBinding
import com.example.todo.databinding.ActivitySignupBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.google.firebase.auth.ktx.auth

class Signup : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth=FirebaseAuth.getInstance()

        binding.tvLogin.setOnClickListener {
            startActivity(Intent(this,Signin::class.java))
            finish()
        }
        binding.btnSignup.setOnClickListener {
            signUp()
        }
    }

    private fun signUp() {
        val email=binding.etEmail.text.toString()
        val password=binding.etPassword.text.toString()
        val confirm_password=binding.etConfirmPassword.text.toString()

        if(email.isBlank() || password.isBlank() || confirm_password.isBlank()){
            Toast.makeText(this,"Please fill all the Credentials", Toast.LENGTH_SHORT).show()
        }
        else if (password!=confirm_password){
            Toast.makeText(this,"Please check your password ", Toast.LENGTH_SHORT).show()
        }
        else{
            auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this){
                if(it.isSuccessful){
                    Toast.makeText(this,"Logged In",Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this,MainActivity::class.java))
                    finish()
                }
                else{
                    Toast.makeText(this,"Error Connecting User",Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}