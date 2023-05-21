package com.bss.firebase_with_kotlin

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bss.firebase_with_kotlin.databinding.ActivityMainBinding
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var firebaseAuth: FirebaseAuth
    val db = Firebase.firestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth = FirebaseAuth.getInstance()
        getLocalData()


        binding.btnSignup.setOnClickListener {
            binding.btnSignup.visibility = View.GONE
            binding.progressbar.visibility = View.VISIBLE
            val email = binding.emailET.text.toString()
            val password = binding.passwordET.text.toString()
            val name = binding.nameEt.text.toString()
            val number = binding.numberET.text.toString()
            firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                addDataToFirebase(it, email, number.toInt(), name)

            }.addOnFailureListener {
                Toast.makeText(this, it.localizedMessage, Toast.LENGTH_SHORT).show()
                binding.btnSignup.visibility = View.VISIBLE
                binding.progressbar.visibility = View.GONE
            }

            firebaseAuth.signInWithEmailAndPassword(email, password)
        }

    }

    private fun addDataToFirebase(
        it: Task<AuthResult>,
        email: String,
        number: Number,
        name: String
    ) {
        val model = MyModel(it.result.user?.uid.toString(), name, email, number.toString())
        savedCreditenction(it.result.user?.uid.toString())
        db.collection("Profile").document(it.result.user?.uid.toString()).set(model)
            .addOnCompleteListener {
                binding.progressbar.visibility = View.GONE
                binding.btnSignup.visibility = View.VISIBLE
                Toast.makeText(this, "Account Created", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
                finish()
            }.addOnFailureListener {
                Toast.makeText(this, it.localizedMessage, Toast.LENGTH_SHORT).show()
            }
    }

    private fun savedCreditenction(uid: String) {
        val prefs = getSharedPreferences("secretDB", MODE_PRIVATE)
        var editor = prefs.edit()
        editor.putString("currentUID", uid)
        editor.putBoolean("isLoggedIn", true)
        editor.apply()
    }


    private fun getLocalData() {
        val prefs = getSharedPreferences("secretDB", MODE_PRIVATE)
        val name = prefs.getBoolean("isLoggedIn", false)
        if (name) {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
}