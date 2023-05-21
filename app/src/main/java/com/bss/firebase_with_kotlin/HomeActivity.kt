package com.bss.firebase_with_kotlin

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bss.firebase_with_kotlin.databinding.ActivityHomeBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class HomeActivity : AppCompatActivity() {
    val db = Firebase.firestore
    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getCurrentUserProfile()
    }

    private fun getCurrentUserProfile() {
        val prefs = getSharedPreferences("secretDB", MODE_PRIVATE)
        val uid = prefs.getString("currentUID", "")
        val docRef = db.collection("Profile").document(uid.toString())
        docRef.get().addOnSuccessListener {
            if (it != null) {
                val city = it.toObject(MyModel::class.java)
                binding.hello.text = city?.name.toString()
            }

        }.addOnFailureListener { exception ->
            Toast.makeText(this,exception.localizedMessage,Toast.LENGTH_SHORT).show()
        }
    }
}