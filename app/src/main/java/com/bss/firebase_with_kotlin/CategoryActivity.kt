package com.bss.firebase_with_kotlin

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.bss.firebase_with_kotlin.databinding.ActivityCategoryBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class CategoryActivity : AppCompatActivity() {
    val db = Firebase.firestore
    private lateinit var binding: ActivityCategoryBinding
    private lateinit var dataList: ArrayList<CategoryModel>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getAllCategory()
    }

    private fun getAllCategory() {
        dataList = arrayListOf()
        db.collection("Category").get().addOnSuccessListener {
            if (it.isEmpty) {

            } else {
                it.documents.forEach { singledata ->
                    val model = singledata.toObject(CategoryModel::class.java)
                    if (model != null) {
                        dataList.add(model)
                    }
                }
                binding.loadingBar.visibility = View.GONE
                binding.categoryRecyllerview.visibility = View.VISIBLE
                initRecyllerView()
            }
        }.addOnFailureListener {

        }
    }

    private fun initRecyllerView() {
        binding.categoryRecyllerview.hasFixedSize()
        binding.categoryRecyllerview.layoutManager = GridLayoutManager(this, 2)
        val adapter = CategoryAdapter(dataList)
        binding.categoryRecyllerview.adapter = adapter

        adapter.setOnCLickListener(object : CategoryAdapter.OnClickListener {
            override fun onClick(position: Int, categoryModel: CategoryModel) {
                val intent = Intent(this@CategoryActivity,MyProductActivity::class.java)
                intent.putExtra("code",categoryModel.name)
                startActivity(intent)
            }

        })
    }
}