package com.dgioto.fairytalesinukrainian

import com.dgioto.fairytalesinukrainian.data.models.FairyTale
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class FairyTaleRepository {

    private val database: DatabaseReference by lazy {
        FirebaseDatabase.getInstance().reference.child("fairytales")
    }

    fun getFairyTalesFromFirebase(): Flow<List<FairyTale>> {
        val fairyTalesFlow = MutableStateFlow<List<FairyTale>>(emptyList())

        database.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val fairyTales = mutableListOf<FairyTale>()
                for (childSnapshot in snapshot.children) {
                    val image = childSnapshot.child("image").getValue(Int::class.java) ?: 0
                    val title = childSnapshot.child("title").getValue(String::class.java) ?: ""
                    val description = childSnapshot.child("description").getValue(String::class.java) ?: ""
                    val isFavorite = childSnapshot.child("isFavorite").getValue(Boolean::class.java) ?: false

                    val fairyTale = FairyTale(image, title, description, isFavorite)
                    fairyTales.add(fairyTale)
                }
                fairyTalesFlow.value = fairyTales
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle errors here
            }
        })

        return fairyTalesFlow
    }
}