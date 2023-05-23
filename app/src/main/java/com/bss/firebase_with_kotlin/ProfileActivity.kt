package com.bss.firebase_with_kotlin

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bss.firebase_with_kotlin.databinding.ActivityProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ProfileActivity : AppCompatActivity() {
    val db = Firebase.firestore
    private lateinit var binding: ActivityProfileBinding
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth = FirebaseAuth.getInstance()
        getLocalData()


        binding.logout.setOnClickListener {
            firebaseAuth.signOut()
            val prefs = getSharedPreferences("secretDB", MODE_PRIVATE)
            prefs.edit().remove("currentUID").apply()
            prefs.edit().remove("isLoggedIn").apply()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finishAffinity()
        }
    }

    private fun getLocalData() {
        val prefs = getSharedPreferences("secretDB", MODE_PRIVATE)
        val uid = prefs.getString("currentUID", "")
        val docref = db.collection("Profile").document(uid.toString())

        docref.get().addOnCompleteListener {
            if (it.isSuccessful) {
                val model = it.result.toObject(MyModel::class.java)
                binding.nameID.text = model?.name.toString()
                binding.emailID.text = model?.email.toString()
                binding.numberID.text = model?.number.toString()
            }
        }.addOnFailureListener {
            Toast.makeText(this, it.localizedMessage, Toast.LENGTH_SHORT).show()
        }


    }

}