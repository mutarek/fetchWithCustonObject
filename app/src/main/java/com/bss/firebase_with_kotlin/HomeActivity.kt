package com.bss.firebase_with_kotlin

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.bss.firebase_with_kotlin.databinding.ActivityHomeBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class HomeActivity : AppCompatActivity() {
    val db = Firebase.firestore
    private lateinit var binding: ActivityHomeBinding
    private lateinit var dataSource: ArrayList<ProductModel>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //getCurrentUserProfile()
        getAllProducts()
//        binding.hello.setOnClickListener {
//            startActivity(Intent(this, ProfileActivity::class.java))
//        }
    }

    private fun getAllProducts() {
        binding.mView.visibility = View.GONE
        binding.progressBar.visibility = View.VISIBLE
        dataSource = arrayListOf()
        db.collection("Products").get().addOnSuccessListener {
            if (it.isEmpty) {

            } else {
                it.documents.forEach { singleProduct ->
                    val product = singleProduct.toObject(ProductModel::class.java)
                    if (product != null) {
                        dataSource.add(product)
                    }
                }
                binding.mView.visibility = View.VISIBLE
                binding.progressBar.visibility = View.GONE
                initRecyllerView()
            }
        }.addOnFailureListener {
            Toast.makeText(this, it.localizedMessage, Toast.LENGTH_SHORT).show()
        }
    }

    private fun initRecyllerView() {
        binding.mView.hasFixedSize()
        binding.mView.layoutManager = GridLayoutManager(this, 2)
        binding.mView.adapter = CustomAdapter(dataSource)
    }


//    private fun getCurrentUserProfile() {
//        val prefs = getSharedPreferences("secretDB", MODE_PRIVATE)
//        val uid = prefs.getString("currentUID", "")
//        val docRef = db.collection("Profile").document(uid.toString())
//        docRef.get().addOnSuccessListener {
//            if (it != null) {
//                val city = it.toObject(MyModel::class.java)
//                binding.hello.text = city?.name.toString()
//            }
//
//        }.addOnFailureListener { exception ->
//            Toast.makeText(this, exception.localizedMessage, Toast.LENGTH_SHORT).show()
//        }
//    }
}