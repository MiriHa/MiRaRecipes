package de.miRa.mirarecipes.database

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.IgnoreExtraProperties
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.google.firebase.database.getValue

@IgnoreExtraProperties
data class Counter (
    var counter : Int? = null
)
class DatabaseCom{
    var counter : Counter? = null
    private var database: DatabaseReference = Firebase.database.reference

    init {
        retrieve_counter_from_DB()
        if(counter == null){
            create_counter_in_DB()
        }
    }
    fun create_counter_in_DB() {
        counter = Counter(0)
        database.child("counter").child("0").setValue(counter) { _, _ ->
            Log.d("FIREBASE", "Counter added")
        }
    }

    fun retrieve_counter_from_DB()
    {
        val counterListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot){
                this@DatabaseCom.counter = dataSnapshot.getValue<Counter>()
                Log.d("DataCom","retrieved updated value ${this@DatabaseCom.counter?.counter}")
            }
            override fun onCancelled(databaseError: DatabaseError){
                Log.w("DataCom","retrieve_counter_from_DB", databaseError.toException())
            }
        }
        database.child("counter").child("0").addValueEventListener(counterListener)
    }

    fun increment_counter_in_DB()
    {
        counter?.counter?.let {
            database.child("counter").child("0").child("counter")
                .setValue(it + 1)
        }
    }
}